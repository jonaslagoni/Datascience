
module.exports = class AllProcessedSpotPricesSchema {
    constructor(){

    }
    /**
    * 
    * @param { ProcessedSpotPricesSchema[] } processedSpotPricesSchema
    * @param { string } ProcessedSpotPricesSchema[].MONTH_DATE_DK
    * @param { string } ProcessedSpotPricesSchema[].PRICE_AREA
    * @param { string } ProcessedSpotPricesSchema[].AVERAGE_SPOT_PRICE_EUR
    */
    setData(processedSpotPricesSchema){ 
        this.processedSpotPricesSchema = processedSpotPricesSchema
    }

    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        this.processedSpotPricesSchema=jsonObject.processedSpotPricesSchema;
    }
}
