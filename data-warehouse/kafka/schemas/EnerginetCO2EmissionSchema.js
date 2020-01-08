
module.exports = class EnerginetCO2EmissionSchema {
    constructor(){

    }
    
    /**
    *
    * @param { string } MINUTES5_DK
    * @param { string } PRICE_AREA
    * @param { number } CO2_EMISSION
    */
    setData(
        MINUTES5_DK,PRICE_AREA,CO2_EMISSION
    ){
        this.MINUTES5_DK=MINUTES5_DK;
        this.PRICE_AREA=PRICE_AREA;
        this.CO2_EMISSION=CO2_EMISSION;
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
        if(jsonObject.CO2_EMISSION){
            this.CO2_EMISSION=jsonObject.CO2_EMISSION;
        }
    }
}
