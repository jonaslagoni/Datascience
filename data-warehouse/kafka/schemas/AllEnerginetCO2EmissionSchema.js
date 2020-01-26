
module.exports = class AllEnerginetCO2EmissionSchema {
    constructor(){

    }
    /**
    * 
    * @param { EnerginetCO2EmissionSchema[] } energinetCO2EmissionSchema
    * @param { string } EnerginetCO2EmissionSchema[].MINUTES5_DK
    * @param { string } EnerginetCO2EmissionSchema[].PRICE_AREA
    * @param { number } EnerginetCO2EmissionSchema[].CO2_EMISSION
    */
    setData(energinetCO2EmissionSchema){ 
        this.energinetCO2EmissionSchema = energinetCO2EmissionSchema
    }

    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        this.energinetCO2EmissionSchema=jsonObject.energinetCO2EmissionSchema;
    }
}
