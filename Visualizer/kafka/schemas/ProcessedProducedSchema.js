
exports.Enum = Enum;
exports = class ProcessedProducedSchema {
    constructor();
    

    /**
    *
    * @param { string } HOUR_DK
    * @param { string } PRICE_AREA
    * @param { string } TOTAL_MWH_PRODUCED
    */
    constructor(
        HOUR_DK,PRICE_AREA,TOTAL_MWH_PRODUCED
    ){
        this.HOUR_DK=HOUR_DK;
        this.PRICE_AREA=PRICE_AREA;
        this.TOTAL_MWH_PRODUCED=TOTAL_MWH_PRODUCED;
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
        if(jsonObject.TOTAL_MWH_PRODUCED){
            this.TOTAL_MWH_PRODUCED=jsonObject.TOTAL_MWH_PRODUCED;
        }
    }
}
