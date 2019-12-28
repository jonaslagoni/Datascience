const commandLineArgs = require('command-line-args');

const optionDefinitions = [
	{
		name: 'zookeeper',
		type: String,
		defaultValue: '127.0.0.1:2181',
		description: 'Zookeeper host to use'
	},
	{
		name: 'kafkaGroup',
		type: String,
		defaultValue: 'visualizer',
		description: 'Which kafka group to be apart of'
	}
];

const options = commandLineArgs(optionDefinitions);
console.log(`Using options: ${JSON.stringify(options)}`);

module.exports = options;
