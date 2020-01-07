
module.exports = class AllProcessedEmissionsSchema {
    constructor(){

    }
    /**
    * 
    * @param { ProcessedEmissionsSchema[] } processedEmissionsSchema
    * @param { string } ProcessedEmissionsSchema[].HOUR_DK
    * @param { string } ProcessedEmissionsSchema[].PRICE_AREA
    * @param { string } ProcessedEmissionsSchema[].ACTUAL_EMISSIONS
    */
    setData(processedEmissionsSchema){ 
        this.processedEmissionsSchema = processedEmissionsSchema
    }

    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        this.processedEmissionsSchema=jsonObject.processedEmissionsSchema;
    }
}
