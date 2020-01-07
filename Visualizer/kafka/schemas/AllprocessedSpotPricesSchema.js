
module.exports = class AllprocessedSpotPricesSchema {
    constructor(){

    }
    /**
    * 
    * @param { ProcessedSpotPricesSchema[] } processedSpotPricesSchema 
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
