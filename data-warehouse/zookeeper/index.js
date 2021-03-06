var zookeeper = require('node-zookeeper-client');
var options = require('../bin/options');
class Zookeeper {
	constructor() {
		this.isConnected = false;
		this.brokerPath = '/brokers/ids';
		this.connect = () => {
			var that = this;
			that.client = zookeeper.createClient(options.zookeeper);
			that.client.once('connected', function() {
				console.log('Zookeeper connected to ZooKeeper.');
				that.isConnected = true;
			});
			that.client.once('authenticationFailed', function() {
				console.log('Zookeeper failed authentication');
				process.exit(1);
			});
			that.client.once('disconnected', function() {
				console.log('Zookeeper disconnected');
				that.isConnected = false;
				that.connect();
			});
			that.client.connect();
		};
		this.connect();
		this.ensureConnected = () => {
			return new Promise((resolve, reject) => {
				if (this.isConnected) {
					resolve();
				} else {
					console.log('Zookeeper not connected, waiting');
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
	}
	async listKafkaBrokers(watcher) {
		let that = this;
		if (!watcher)
			watcher = event => {
				console.log('Zookeeper got watcher event: %s', event);
				//Dynamically add and remove brokers here
			};
		return new Promise(async (resolve, reject) => {
			try {
				await that.ensureConnected();
			} catch (e) {
				reject('Could not connect to zookeeper..');
				return;
			}
			console.log('Zookeeper getting kafka brokers');
			that.client.getChildren(that.brokerPath, watcher, function(
				error,
				children,
				stat
			) {
				if (error) {
					console.log(`error: ${error}`);
					reject(
						`Failed to list children of ${that.brokerPath} due to: ${error}.`
					);
					return;
				}

				console.log(
					'Zookeeper Children of %s are: %j.',
					that.brokerPath,
					children
				);
				let hosts = [];
				children.forEach(element => {
					that.client.getData(
						`${that.brokerPath}/${element}`,
						watcher,
						function(error, data, stat) {
							if (error || !data) {
								console.log(`error: ${error}`);
								return;
							}
							data = JSON.parse(data.toString('utf8'));
							console.log(`recieved data: ${JSON.stringify(data)}`);
							let host = data.host;
							let port = data.port;
							hosts.push(`${host}:${port}`);
							if (hosts.length == children.length) {
								console.log(hosts);
								resolve(hosts);
							}
						}
					);
				});
			});
		});
	}
}
module.exports = new Zookeeper();
