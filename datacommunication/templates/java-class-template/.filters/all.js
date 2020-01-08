module.exports = ({ Nunjucks, _ }) => {
	Nunjucks.addFilter('camelCase', str => {
		return _.camelCase(str);
	});

	Nunjucks.addFilter('print', str => {
		console.log(str);
	});
	Nunjucks.addFilter('upperFirst', str => {
		return _.upperFirst(str);
	});

	Nunjucks.addFilter('toJavaType', property => {
		switch (property.type()) {
			case 'string':
				return 'String';
			case 'array':
				let type =
					property
						.items()
						.uid()
						.charAt(0)
						.toUpperCase() +
					property
						.items()
						.uid()
						.slice(1);
				return `List<${type}>`;
			case 'number':
				if (property.required()) {
					return 'double';
				} else {
					return 'Double';
				}
			case 'integer':
				if (property.required()) {
					return 'int';
				} else {
					return 'Integer';
				}
			default:
				return property.type();
		}
	});
};
