
module.exports = class AllProcessedProducedSchema {
    constructor(){

    }
    /**
    * 
    * @param { ProcessedProducedSchema[] } processedProducedSchema
    * @param { string } ProcessedProducedSchema[].DAY_DATE_DK
    * @param { string } ProcessedProducedSchema[].PRICE_AREA
    * @param { string } ProcessedProducedSchema[].TOTAL_MWH_PRODUCED
    */
    setData(processedProducedSchema){ 
        this.processedProducedSchema = processedProducedSchema
    }

    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        this.processedProducedSchema=jsonObject.processedProducedSchema;
    }
}
