const commandLineArgs = require('command-line-args');

const optionDefinitions = [
	{
		name: 'zookeeper',
		type: String,
		defaultValue: '192.168.99.101:2181',
		description: 'Zookeeper host to use'
	},
	{
		name: 'kafkaGroup',
		type: String,
		defaultValue: 'datawarehouse',
		description: 'Which kafka group to be apart of'
	}
];

const options = commandLineArgs(optionDefinitions);
console.log(`Using options: ${JSON.stringify(options)}`);
module.exports = options;
