
exports.Enum = Enum;
exports = class RawEnergyData {
    constructor();
    
    constructor(
        month,municipality,onShoreWind,offShoreWind,solarPower,centralPowerPlant,decentralPowerPlant
    ){
        this.month=month;
        this.municipality=municipality;
        this.onShoreWind=onShoreWind;
        this.offShoreWind=offShoreWind;
        this.solarPower=solarPower;
        this.centralPowerPlant=centralPowerPlant;
        this.decentralPowerPlant=decentralPowerPlant;
    }


    
}
