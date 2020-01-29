module.exports = class AllEnerginetProductionAndExchangeSchema {
	constructor() {}
	/**
	 *
	 * @param { EnerginetProductionAndExchangeSchema[] } allEnerginetProductionAndExchangeSchema
	 * @param { string } allEnerginetProductionAndExchangeSchema[].MINUTES5_DK
	 * @param { string } allEnerginetProductionAndExchangeSchema[].PRICE_AREA
	 * @param { number } allEnerginetProductionAndExchangeSchema[].PRODUCTION_LT_100
	 * @param { number } allEnerginetProductionAndExchangeSchema[].PRODUCTION_GE_100
	 */
	setData(allEnerginetProductionAndExchangeSchema) {
		this.allEnerginetProductionAndExchangeSchema = allEnerginetProductionAndExchangeSchema;
	}

	/**
	 * Copy a js object into this.
	 * @param {*} jsonObject the js object
	 */
	copyInto(jsonObject) {
		this.allEnerginetProductionAndExchangeSchema =
			jsonObject.allEnerginetProductionAndExchangeSchema;
	}
};
