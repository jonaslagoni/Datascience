
module.exports = class AllProcessedEmissionsSchema {
    constructor(){

    }
    /**
    * 
    * @param { ProcessedEmissionsSchema[] } processedEmissionsSchema 
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
