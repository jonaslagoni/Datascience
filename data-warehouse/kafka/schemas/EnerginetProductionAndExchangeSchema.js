
exports.Enum = Enum;
exports = class EnerginetProductionAndExchangeSchema {
    constructor();
    

    /**
    *
    * @param { string } MINUTES5_DK
    * @param { string } PRICE_AREA
    * @param { number } PRODUCTION_LT_100
    * @param { number } PRODUCTION_GE_100
    */
    constructor(
        MINUTES5_DK,PRICE_AREA,PRODUCTION_LT_100,PRODUCTION_GE_100
    ){
        this.MINUTES5_DK=MINUTES5_DK;
        this.PRICE_AREA=PRICE_AREA;
        this.PRODUCTION_LT_100=PRODUCTION_LT_100;
        this.PRODUCTION_GE_100=PRODUCTION_GE_100;
    }


    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        if(jsonObject.MINUTES5_DK){
            this.MINUTES5_DK=jsonObject.MINUTES5_DK;
        }
        if(jsonObject.PRICE_AREA){
            this.PRICE_AREA=jsonObject.PRICE_AREA;
        }
        if(jsonObject.PRODUCTION_LT_100){
            this.PRODUCTION_LT_100=jsonObject.PRODUCTION_LT_100;
        }
        if(jsonObject.PRODUCTION_GE_100){
            this.PRODUCTION_GE_100=jsonObject.PRODUCTION_GE_100;
        }
    }
}
