const axios = require('axios');
const kafka = require('./kafka/index');

const energiProductionSchema = require("./kafka/schemas/EnerginetProductionAndExchangeSchema");
const energiSpotPricesSchema = require("./kafka/schemas/EnerginetElspotSchema");
const energiEmissionSchema = require("./kafka/schemas/EnerginetCO2EmissionSchema");

let url = 'https://api.energidataservice.dk/datastore_search'
let elementsStored = 0;

axios.get('https://api.energidataservice.dk/datastore_search', {
    params: {
        resource_id: 'electricityprodex5minrealtime',
        limit: 5
    }
})
.then((response) => {
    console.log(response.data.result.records);
    response.data.result.records.forEach(element => {
        const energiSchema = new energiProductionSchema();
        energiSchema.setData(element.Minutes5DK, element.PriceArea, element.ProductionLt100MW, element.ProductionGe100MW)
        kafka.publishEnergidataProductionAndExchange(JSON.stringify(energiSchema)).then(() => {
            console.log("Done: " + energiSchema);
        }).catch(e => {
            console.log(e);
        });
    });
})
.catch((e) =>{
    console.log(e);
});

axios.get('https://api.energidataservice.dk/datastore_search', {
    params: {
        resource_id: 'elspotprices',
        limit: 5
    }
})
.then((response) => {
    console.log(response.data.result.records);
    response.data.result.records.forEach(element => {
        const spotPriceSchema = new energiSpotPricesSchema();
        spotPriceSchema.setData(element.HourDK, element.PriceArea, element.SpotPriceEUR)
        kafka.publishEnergidataProductionAndExchange(JSON.stringify(spotPriceSchema)).then(() => {
            console.log("Done: " + spotPriceSchema);
        }).catch(e => {
            console.log(e);
        });
    });
})
.catch((e) =>{
    console.log(e);
});

axios.get('https://api.energidataservice.dk/datastore_search', {
    params: {
        resource_id: 'co2emis',
        limit: 5
    }
})
.then((response) => {
    console.log(response.data.result.records);
    response.data.result.records.forEach(element => {
        const emisSchema = new energiEmissionSchema();
        emisSchema.setData(element.Minutes5DK, element.PriceArea, element.CO2Emission)
        kafka.publishEnergidataProductionAndExchange(JSON.stringify(emisSchema)).then(() => {
            console.log("Done: " + emisSchema);
        }).catch(e => {
            console.log(e);
        });
    });
})
.catch((e) =>{
    console.log(e);
});
