const fs = require('fs');
const path = require('path');
var renameAllSync = function(dir) {
	files = fs.readdirSync(dir);
	files.forEach(function(file) {
		let filepath = path.resolve(dir, file);
		if (fs.statSync(filepath).isDirectory()) {
			renameAllSync(filepath);
		} else if (path.extname(filepath) === '.njk') {
			let baseName = path.basename(filepath, '.java.njk');
			let newName =
				baseName.charAt(0).toUpperCase() + baseName.slice(1) + '.java';
			let newPath = path.resolve(dir, newName);
			fs.renameSync(filepath, newPath);
		}
	});
};
module.exports = register => {
	register('generate:after', generator => {
		renameAllSync(path.resolve(generator.targetDir));
	});
};
