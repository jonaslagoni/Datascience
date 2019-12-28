const zookeeper = require('../zookeeper/index');
const options = require('../bin/options');
const kafka = require('kafka-node');
const Consumer = kafka.Consumer;

class Kafka {
	constructor() {
		this.isConnected = false;
		this.ensureConnected = () => {
			return new Promise((resolve, reject) => {
				if (this.isConnected) {
					resolve();
				} else {
					console.log('Not connected to kafka, waiting');
					setTimeout(() => {
						this.ensureConnected()
							.catch(e => {
								reject(e);
							})
							.then(() => {
								resolve();
							});
					}, 3000);
				}
			});
		};
		this.connect();
	}

	async connect() {
		try {
			let kafkaZookeeperHosts = await zookeeper.listKafkaBrokers();

			this.kafkaHost = kafkaZookeeperHosts.reduce((acc, host) => {
				if (!acc || acc === '') {
					return `${host}`;
				} else {
					return `${acc},${host}`;
				}
			}, '');
			console.log('Using kafka hosts: ' + this.kafkaHost);
			this.client = new kafka.KafkaClient({
				kafkaHost: this.kafkaHost
			});
			this.client.on('close', e => {
				console.error('kafka connection closed');
				console.error(e);
				this.isConnected = false;
			});
			this.client.on('ready', () => {
				console.error('Kafka connection ready');
				this.isConnected = true;
			});
			this.client.on('error', e => {
				console.error('Error in kafka connection');
				console.error(e);
				this.isConnected = false;
			});
			this.client.on('socket_error', e => {
				console.error('socket_error in kafka connection');
				console.error(e);
				this.isConnected = false;
			});
		} catch (e) {
			console.log(e);
			this.isConnected = false;
			this.connect();
		}
	}
	listenForMessageA(messageCallback, errorCallback) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}
			var consumer = new Consumer(
				this.client,
				[{ topic: 'messageA', partition: 0 }],
				{
					autoCommit: false,
					groupId: options.kafkaGroup
				}
			);
			consumer.on('message', messageCallback);
			consumer.on('error', errorCallback);
			consumer.on('offsetOutOfRange', errorCallback);
			resolve();
		});
	}
}
module.exports = new Kafka();
