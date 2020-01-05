const zookeeper = require('../zookeeper/index');
const options = require('../bin/options');
const kafka = require('kafka-node');
const Consumer = kafka.Consumer;
const Producer = kafka.Producer;
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
	/**
	 * subscribes to event for when new data is being processed
	 * @param {*} payload to send.
	 * @returns {Promise} Promise object resolves when the data is sent successfully.
	 */
	publishEnergidataElspot(payload) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}

			const producer = new Producer(client);
			const kafka_topic = 'energidataElspot';
			console.log(kafka_topic);
			let payloads = [
				{
				topic: kafka_topic,
				messages: payload
				}
			];

			producer.on('ready', async function() {
				let push_status = producer.send(payloads, (err, data) => {
				if (err) {
					console.log('[kafka-producer -> '+kafka_topic+']: broker update failed');
					reject('[kafka-producer -> '+kafka_topic+']: broker update failed');
				} else {
					console.log('[kafka-producer -> '+kafka_topic+']: broker update success');
					resolve();
				}
				});
			});

			producer.on('error', function(err) {
				console.log(err);
				console.log('[kafka-producer -> '+kafka_topic+']: connection errored');
				reject(err);
			});


			var consumer = new Consumer(
				this.client,
				[{ topic: 'energidataElspot', partition: 0 }],
				{
					autoCommit: true,
					fetchMaxWaitMs: 1000,
					fetchMaxBytes: 1024 * 1024,
					encoding: 'utf8',
      				fromOffset: false,
					groupId: options.kafkaGroup
				}
			);
			consumer.on('message', messageCallback);
			consumer.on('error', errorCallback);
			consumer.on('offsetOutOfRange', errorCallback);
			resolve(consumer);
		});
	}
	/**
	 * subscribes to event for when new data is being processed
	 * @param {*} payload to send.
	 * @returns {Promise} Promise object resolves when the data is sent successfully.
	 */
	publishEnergidataCo2Emission(payload) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}

			const producer = new Producer(client);
			const kafka_topic = 'energidataCo2Emission';
			console.log(kafka_topic);
			let payloads = [
				{
				topic: kafka_topic,
				messages: payload
				}
			];

			producer.on('ready', async function() {
				let push_status = producer.send(payloads, (err, data) => {
				if (err) {
					console.log('[kafka-producer -> '+kafka_topic+']: broker update failed');
					reject('[kafka-producer -> '+kafka_topic+']: broker update failed');
				} else {
					console.log('[kafka-producer -> '+kafka_topic+']: broker update success');
					resolve();
				}
				});
			});

			producer.on('error', function(err) {
				console.log(err);
				console.log('[kafka-producer -> '+kafka_topic+']: connection errored');
				reject(err);
			});


			var consumer = new Consumer(
				this.client,
				[{ topic: 'energidataCo2Emission', partition: 0 }],
				{
					autoCommit: true,
					fetchMaxWaitMs: 1000,
					fetchMaxBytes: 1024 * 1024,
					encoding: 'utf8',
      				fromOffset: false,
					groupId: options.kafkaGroup
				}
			);
			consumer.on('message', messageCallback);
			consumer.on('error', errorCallback);
			consumer.on('offsetOutOfRange', errorCallback);
			resolve(consumer);
		});
	}
	/**
	 * subscribes to event for when new data is being processed
	 * @param {*} payload to send.
	 * @returns {Promise} Promise object resolves when the data is sent successfully.
	 */
	publishEnergidataProductionAndExchange(payload) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}

			const producer = new Producer(client);
			const kafka_topic = 'energidataProductionAndExchange';
			console.log(kafka_topic);
			let payloads = [
				{
				topic: kafka_topic,
				messages: payload
				}
			];

			producer.on('ready', async function() {
				let push_status = producer.send(payloads, (err, data) => {
				if (err) {
					console.log('[kafka-producer -> '+kafka_topic+']: broker update failed');
					reject('[kafka-producer -> '+kafka_topic+']: broker update failed');
				} else {
					console.log('[kafka-producer -> '+kafka_topic+']: broker update success');
					resolve();
				}
				});
			});

			producer.on('error', function(err) {
				console.log(err);
				console.log('[kafka-producer -> '+kafka_topic+']: connection errored');
				reject(err);
			});


			var consumer = new Consumer(
				this.client,
				[{ topic: 'energidataProductionAndExchange', partition: 0 }],
				{
					autoCommit: true,
					fetchMaxWaitMs: 1000,
					fetchMaxBytes: 1024 * 1024,
					encoding: 'utf8',
      				fromOffset: false,
					groupId: options.kafkaGroup
				}
			);
			consumer.on('message', messageCallback);
			consumer.on('error', errorCallback);
			consumer.on('offsetOutOfRange', errorCallback);
			resolve(consumer);
		});
	}
}
module.exports = new Kafka();