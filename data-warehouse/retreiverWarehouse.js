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
.then(function(response) {
    console.log(response.data.result.records);
    response.data.result.records.forEach(element => {
        const energiSchema = new energiProductionSchema(element.Minutes5DK, element.PriceAream, element.ProductionLt100MW, element.ProductionGe100MW)
        kafka.publishEnergidataProductionAndExchange(energiSchema);
        console.log(energiSchema);
    });
});
