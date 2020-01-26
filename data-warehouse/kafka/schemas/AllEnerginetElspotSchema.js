
module.exports = class AllEnerginetElspotSchema {
    constructor(){

    }
    /**
    * 
    * @param { EnerginetElspotSchema[] } energinetElspotSchema
    * @param { string } EnerginetElspotSchema[].HOUR_DK
    * @param { string } EnerginetElspotSchema[].PRICE_AREA
    * @param { number } EnerginetElspotSchema[].SPOT_PRICE_EUR
    */
    setData(energinetElspotSchema){ 
        this.energinetElspotSchema = energinetElspotSchema
    }

    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        this.energinetElspotSchema=jsonObject.energinetElspotSchema;
    }
}
