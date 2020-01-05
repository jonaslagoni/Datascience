
exports.Enum = Enum;
exports = class ProcessedEmissionsSchema {
    constructor();
    

    /**
    *
    * @param { string } HOUR_DK
    * @param { string } PRICE_AREA
    * @param { string } AVERAGE_EMISSIONS
    */
    constructor(
        HOUR_DK,PRICE_AREA,AVERAGE_EMISSIONS
    ){
        this.HOUR_DK=HOUR_DK;
        this.PRICE_AREA=PRICE_AREA;
        this.AVERAGE_EMISSIONS=AVERAGE_EMISSIONS;
    }


    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        if(jsonObject.HOUR_DK){
            this.HOUR_DK=jsonObject.HOUR_DK;
        }
        if(jsonObject.PRICE_AREA){
            this.PRICE_AREA=jsonObject.PRICE_AREA;
        }
        if(jsonObject.AVERAGE_EMISSIONS){
            this.AVERAGE_EMISSIONS=jsonObject.AVERAGE_EMISSIONS;
        }
    }
}
