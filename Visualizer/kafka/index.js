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
			let kafkaZookeeperHosts = await zookeeper.listKafkaBrokers(() => {
				this.connect();
			});
			if (kafkaZookeeperHosts.length > 0) {
				this.kafkaHost = kafkaZookeeperHosts.reduce((acc, host) => {
					if (!acc || acc === '') {
						return `${host}`;
					} else {
						return `${acc},${host}`;
					}
				}, '');
				console.log('Using kafka hosts: ' + this.kafkaHost);
				let client = await this.getClient();
				let topics=[];
				topics.push('datascienceProcessedStatus');
				topics.push('processedProduced');
				topics.push('processedSpotPrices');
				topics.push('processedEmissions');
				client.loadMetadataForTopics(topics, (err, resp) => {
					console.log(`${JSON.stringify(resp)}`);
					this.isConnected = true;
				});
			}
			
		} catch (e) {
			console.log("Catched an exception while connecting to kafka");
			console.log(e.error);
			this.isConnected = false;
			setTimeout(this.connect.bind(this), 5000)
		}
	}
	getClient() {
		return new Promise((resolve, reject) => {
			let client = new kafka.KafkaClient({
				kafkaHost: this.kafkaHost
			});
			client.on('close', e => {
				console.error('kafka connection closed');
				console.error(e.error);
				reject(e);
			});
			client.on('ready', () => {
				console.error('Kafka connection ready');
				resolve(client);
			});
			client.on('error', e => {
				console.error('Error in kafka connection');
				console.error(e.error);
				reject(e);
			});
			client.on('socket_error', e => {
				console.error('socket_error in kafka connection');
				console.error(e.error);
				reject(e);
			});
		});
	}
	
	/**
	 * subscribes to event for when new data is being processed
	 * @param {*} messageCallback used when messages are consumed. 
	 * @param {*} errorCallback used when an error occured.
	 * @returns {Promise} Promise object resolves the kafka consumer created.
	 */
	consumeDatascienceProcessedStatus(messageCallback, errorCallback) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}
			var consumer = new Consumer(
				await this.getClient(),
				[{ topic: 'datascienceProcessedStatus', partition: 0 }],
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
	 * @param {*} messageCallback used when messages are consumed. 
	 * @param {*} errorCallback used when an error occured.
	 * @returns {Promise} Promise object resolves the kafka consumer created.
	 */
	consumeProcessedProduced(messageCallback, errorCallback) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}
			var consumer = new Consumer(
				await this.getClient(),
				[{ topic: 'processedProduced', partition: 0 }],
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
	 * @param {*} messageCallback used when messages are consumed. 
	 * @param {*} errorCallback used when an error occured.
	 * @returns {Promise} Promise object resolves the kafka consumer created.
	 */
	consumeProcessedSpotPrices(messageCallback, errorCallback) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}
			var consumer = new Consumer(
				await this.getClient(),
				[{ topic: 'processedSpotPrices', partition: 0 }],
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
	 * @param {*} messageCallback used when messages are consumed. 
	 * @param {*} errorCallback used when an error occured.
	 * @returns {Promise} Promise object resolves the kafka consumer created.
	 */
	consumeProcessedEmissions(messageCallback, errorCallback) {
		return new Promise(async (resolve, reject) => {
			try {
				await this.ensureConnected();
			} catch (e) {
				reject('Could not connect to kafka..');
				return;
			}
			var consumer = new Consumer(
				await this.getClient(),
				[{ topic: 'processedEmissions', partition: 0 }],
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


