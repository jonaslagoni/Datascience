
exports = class AllProcessedProducedSchema {
    constructor();
    /**
    * 
    * @param { ProcessedProducedSchema[] } processedProducedSchema 
    */
    constructor(processedProducedSchema){ 
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
