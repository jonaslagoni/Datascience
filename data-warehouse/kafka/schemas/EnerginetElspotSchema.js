
module.exports = class EnerginetElspotSchema {
    constructor(){

    }
    
    /**
    *
    * @param { string } HOUR_DK
    * @param { string } PRICE_AREA
    * @param { number } SPOT_PRICE_EUR
    */
    setData(
        HOUR_DK,PRICE_AREA,SPOT_PRICE_EUR
    ){
        this.HOUR_DK=HOUR_DK;
        this.PRICE_AREA=PRICE_AREA;
        this.SPOT_PRICE_EUR=SPOT_PRICE_EUR;
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
        if(jsonObject.SPOT_PRICE_EUR){
            this.SPOT_PRICE_EUR=jsonObject.SPOT_PRICE_EUR;
        }
    }
}
