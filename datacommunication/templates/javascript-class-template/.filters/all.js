module.exports = ({ Nunjucks, _ }) => {
	Nunjucks.addFilter('camelCase', str => {
		return _.camelCase(str);
	});

	Nunjucks.addFilter('upperFirst', str => {
		return _.upperFirst(str);
	});

	Nunjucks.addFilter('constructor', schema => {
		let returnString = '';
		console.log(schema);
		if (schema.allOf()) {
			console.log('all of');
			schema.allOf().forEach(element => {
				returnString += `${element.uid()},`;
			});
		}

		if (schema.oneOf()) {
			console.log('one of');
			schema.oneOf().forEach(element => {
				returnString += `${element.uid()},`;
			});
		}

		if (schema.anyOf()) {
			console.log('any of');
			schema.anyOf().forEach(element => {
				returnString += `${element.uid()},`;
			});
		}

		if (schema.uid()) {
			console.log('uid');
			returnString += `${schema.uid()},`;
		}
		if (returnString.length > 1) {
			returnString = returnString.slice(0, -1);
		}
		console.log(returnString);
		return returnString;
	});
	Nunjucks.addFilter('schemaConstructor', properties => {
		let returnString = '';
		for (const [key, value] of Object.entries(properties)) {
			returnString += `${key},`;
		}
		if (returnString.length > 1) {
			returnString = returnString.slice(0, -1);
		}
		return returnString;
	});
};
