const zookeeper = require('../zookeeper/index');
const options = require('../bin/options');
const kafka = require('kafka-node');
const Consumer = kafka.Consumer;

class Kafka {
	constructor() {}

	async connect() {
		this.kafkaHost = await zookeeper.listKafkaBrokers();
		this.client = new kafka.KafkaClient({
			kafkaHost: this.kafkaHost
		});
	}
	listenForMessageA(messageCallback, errorCallback) {
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
	}
}
module.exports = new Kafka();
