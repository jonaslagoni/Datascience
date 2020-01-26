
module.exports = class AllEnerginetProductionAndExchangeSchema {
    constructor(){

    }
    /**
    * 
    * @param { EnerginetProductionAndExchangeSchema[] } energinetProductionAndExchangeSchema
    * @param { string } EnerginetProductionAndExchangeSchema[].MINUTES5_DK
    * @param { string } EnerginetProductionAndExchangeSchema[].PRICE_AREA
    * @param { number } EnerginetProductionAndExchangeSchema[].PRODUCTION_LT_100
    * @param { number } EnerginetProductionAndExchangeSchema[].PRODUCTION_GE_100
    */
    setData(energinetProductionAndExchangeSchema){ 
        this.energinetProductionAndExchangeSchema = energinetProductionAndExchangeSchema
    }

    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        this.energinetProductionAndExchangeSchema=jsonObject.energinetProductionAndExchangeSchema;
    }
}
