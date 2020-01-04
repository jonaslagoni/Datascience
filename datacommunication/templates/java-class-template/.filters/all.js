module.exports = ({ Nunjucks, _ }) => {
	Nunjucks.addFilter('camelCase', str => {
		return _.camelCase(str);
	});

	Nunjucks.addFilter('upperFirst', str => {
		return _.upperFirst(str);
	});

	Nunjucks.addFilter('toJavaType', property => {
		switch (property.type()) {
			case 'string':
				return 'String';
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
