const axios = require('axios');
const kafka = require('./kafka/index');

const energiProductionSchema = require("./kafka/schemas/EnerginetProductionAndExchangeSchema");
const energiSpotPricesSchema = require("./kafka/schemas/EnerginetElspotSchema");
const energiEmissionSchema = require("./kafka/schemas/EnerginetCO2EmissionSchema");

let productionElementsStored = 0;
let emissionElementsStored = 0;
let elspotElementsStored = 0;
let electricityprodex5minrealtimeTimeout = 5000;
let elspotpricesTimeout = 5000;
let co2emisTimeout = 5000;
function waitForKafka() {
    if (kafka.isConnected) {
        setInterval(function () {
            axios.get('https://api.energidataservice.dk/datastore_search', {
                params: {
                    resource_id: 'electricityprodex5minrealtime',
                    limit: 5,
                    offset: productionElementsStored
                }
            })
            .then((response) => {
                let counter;
                console.log(response.data.result.records);
                if (productionElementsStored != 0) {
                    counter = response.data.result.records.length - productionElementsStored;
                } else {
                    counter = productionElementsStored;
                }
                response.data.result.records.forEach(element => {
                    kafka.publishEnergidataProductionAndExchange(JSON.stringify(element)).then(() => {
                        console.log("Done: " + energiSchema);
                    }).catch(e => {
                        console.log(e);
                    });
                });
                productionElementsStored = response.data.result.records.length;
            })
            .catch((e) => {
                console.log(e);
            })
        }, electricityprodex5minrealtimeTimeout);

        setInterval(function () {
            axios.get('https://api.energidataservice.dk/datastore_search', {
                params: {
                    resource_id: 'elspotprices',
                    limit: 5,
                    offset: elspotElementsStored
                }
            })
            .then((response) => {
                let counter;
                if (elspotElementsStored != 0) {
                    counter = response.data.result.records.length - elspotElementsStored;
                } else {
                    counter = elspotElementsStored;
                }
                console.log(response.data.result.records);
                response.data.result.records.forEach(element => {
                    const spotPriceSchema = new energiSpotPricesSchema();
                    spotPriceSchema.setData(element.HourDK, element.PriceArea, element.SpotPriceEUR)
                    kafka.publishEnergidataElspot(JSON.stringify(spotPriceSchema)).then(() => {
                        console.log("Done: " + spotPriceSchema);
                    }).catch(e => {
                        console.log(e);
                    });
                });
                elspotElementsStored = response.data.result.records.length;
            })
            .catch((e) => {
                console.log(e);
            })
        }, elspotpricesTimeout);
        setInterval(function () {
            axios.get('https://api.energidataservice.dk/datastore_search', {
                params: {
                    resource_id: 'co2emis',
                    limit: 5,
                    offset: emissionElementsStored
                }
            })
            .then((response) => {
                let counter;
                if (emissionElementsStored != 0) {
                    counter = response.data.result.records.length - emissionElementsStored;
                } else {
                    counter = emissionElementsStored;
                }
                console.log(response.data.result.records);
                response.data.result.records.forEach(element => {
                    const emisSchema = new energiEmissionSchema();
                    emisSchema.setData(element.Minutes5DK, element.PriceArea, element.CO2Emission)
                    kafka.publishEnergidataCo2Emission(JSON.stringify(emisSchema)).then(() => {
                        console.log("Done: " + emisSchema);
                    }).catch(e => {
                        console.log(e);
                    });
                });
                emissionElementsStored = response.data.result.records.length;
            })
            .catch((e) => {
                console.log(e);
            })
        }, co2emisTimeout);

    } else {
        setTimeout(waitForKafka, 5000);
    }
}

waitForKafka();