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
				topics.push('energidataElspot');
				topics.push('energidataCo2Emission');
				topics.push('energidataProductionAndExchange');
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

			const producer = new Producer(await this.getClient());
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
				producer.close();
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
			producer.connect();
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

			const producer = new Producer(await this.getClient());
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
				producer.close();
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
			producer.connect();
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

			const producer = new Producer(await this.getClient());
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
				producer.close();
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
			producer.connect();
		});
	}
}
module.exports = new Kafka();


