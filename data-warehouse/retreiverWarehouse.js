const axios = require('axios');
const kafka = require('./kafka/index');

const energiProductionSchema = require("./kafka/schemas/EnerginetProductionAndExchangeSchema");
const energiSpotPricesSchema = require("./kafka/schemas/EnerginetElspotSchema");
const energiEmissionSchema = require("./kafka/schemas/EnerginetCO2EmissionSchema");

let productionElementsStored = 0;
let emissionElementsStored = 0;
let elspotElementsStored = 0;

setInterval(function(){ axios.get('https://api.energidataservice.dk/datastore_search', {
    params: {
        resource_id: 'electricityprodex5minrealtime',
        limit: 5
    }
})
.then((response) => {
    let counter;
    console.log(response.data.result.records);
    if(productionElementsStored != 0){
        counter = response.data.result.records.length - productionElementsStored;
    }else{
        counter = productionElementsStored;
    }
    response.data.result.records.forEach(element => {
        if(counter != 0){
            const energiSchema = new energiProductionSchema();
            energiSchema.setData(element.Minutes5DK, element.PriceArea, element.ProductionLt100MW, element.ProductionGe100MW)
            kafka.publishEnergidataProductionAndExchange(JSON.stringify(energiSchema)).then(() => {
                console.log("Done: " + energiSchema);
            }).catch(e => {
                console.log(e);
            });
            counter--;
        }
    });
    elspotElementsStored = response.data.result.records.length;
})
.catch((e) =>{
    console.log(e);
})}, 300000);

setInterval(function(){ axios.get('https://api.energidataservice.dk/datastore_search', {
    params: {
        resource_id: 'elspotprices',
        limit: 5
    }
})
.then((response) => {
    let counter;
    if(elspotElementsStored != 0){
        counter = response.data.result.records.length - elspotElementsStored;
    }else{
        counter = elspotElementsStored;
    }
    console.log(response.data.result.records);
    response.data.result.records.forEach(element => {
        if(counter != 0){
            const spotPriceSchema = new energiSpotPricesSchema();
            spotPriceSchema.setData(element.HourDK, element.PriceArea, element.SpotPriceEUR)
            kafka.publishEnergidataElspot(JSON.stringify(spotPriceSchema)).then(() => {
                console.log("Done: " + spotPriceSchema);
            }).catch(e => {
                console.log(e);
            });
            counter--;
        }
    });
    elspotElementsStored = response.data.result.records.length;
})
.catch((e) =>{
    console.log(e);
})}, 300000);

setInterval(function(){ axios.get('https://api.energidataservice.dk/datastore_search', {
    params: {
        resource_id: 'co2emis',
        limit: 5
    }
})
.then((response) => {
    let counter;
    if(emissionElementsStored != 0){
        counter = response.data.result.records.length - emissionElementsStored;
    }else{
        counter = emissionElementsStored;
    }
    console.log(response.data.result.records);
    response.data.result.records.forEach(element => {
        if(counter != 0){
            const emisSchema = new energiEmissionSchema();
            emisSchema.setData(element.Minutes5DK, element.PriceArea, element.CO2Emission)
            kafka.publishEnergidataCo2Emission(JSON.stringify(emisSchema)).then(() => {
                console.log("Done: " + emisSchema);
            }).catch(e => {
                console.log(e);
            });
            counter--;
        }
    });
    emissionElementsStored = response.data.result.records.length;
})
.catch((e) =>{
    console.log(e);
})}, 300000);
