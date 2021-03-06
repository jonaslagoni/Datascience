const axios = require('axios');
const kafka = require('./kafka/Index');

const energiProductionSchema = require('./kafka/schemas/EnerginetProductionAndExchangeSchema');
const energiSpotPricesSchema = require('./kafka/schemas/EnerginetElspotSchema');
const energiEmissionSchema = require('./kafka/schemas/EnerginetCO2EmissionSchema');
const energiProduction = require('./kafka/messages/EnerginetProductionAndExchange');
const energiSpotPrices = require('./kafka/messages/EnerginetElspot');
const energiEmission = require('./kafka/messages/EnerginetCO2Emission');

let productionElementsStored = 0;
let emissionElementsStored = 0;
let elspotElementsStored = 0;
let electricityprodex5minrealtimeTimeout = 5000;
let elspotpricesTimeout = 5000;
let co2emisTimeout = 5000;
function waitForKafka() {
	if (kafka.isConnected) {
		setInterval(function() {
			axios
				.get(
					`https://api.energidataservice.dk/datastore_search_sql?sql=${`SELECT * from "electricityprodex5minrealtime" LIMIT 5 OFFSET ${productionElementsStored}`.replace(
						' ',
						'%20'
					)}`
				)
				.then(response => {
					response.data.result.records.forEach(element => {
						const payload = new energiProduction();
						const energiSchema = new energiProductionSchema();
						energiSchema.setData(
							element.Minutes5DK,
							element.PriceArea,
							element.ProductionLt100MW,
							element.ProductionGe100MW
						);
						payload.setData(energiSchema);
						kafka
							.publishEnergidataProductionAndExchange(JSON.stringify(payload))
							.then(() => {
								console.log('Done: ' + energiSchema);
							})
							.catch(e => {
								console.log(e);
							});
					});
					productionElementsStored += response.data.result.records.length;
				})
				.catch(e => {
					console.log(e);
				});
		}, electricityprodex5minrealtimeTimeout);

		setInterval(function() {
			axios
				.get(
					`https://api.energidataservice.dk/datastore_search_sql?sql=${`SELECT * from "elspotprices" LIMIT 5 OFFSET ${elspotElementsStored}`.replace(
						' ',
						'%20'
					)}`
				)
				.then(response => {
					response.data.result.records.forEach(element => {
						const payload = new energiSpotPrices();
						const spotPriceSchema = new energiSpotPricesSchema();
						spotPriceSchema.setData(
							element.HourDK,
							element.PriceArea,
							element.SpotPriceEUR
						);
						payload.setData(spotPriceSchema);
						kafka
							.publishEnergidataElspot(JSON.stringify(payload))
							.then(() => {
								console.log('Done: ' + spotPriceSchema);
							})
							.catch(e => {
								console.log(e);
							});
					});
					elspotElementsStored += response.data.result.records.length;
				})
				.catch(e => {
					console.log(e);
				});
		}, elspotpricesTimeout);
		setInterval(function() {
			axios
				.get(
					`https://api.energidataservice.dk/datastore_search_sql?sql=${`SELECT * from "co2emis" LIMIT 5 OFFSET ${emissionElementsStored}`.replace(
						' ',
						'%20'
					)}`
				)
				.then(response => {
					response.data.result.records.forEach(element => {
						const payload = new energiEmission();
						const emisSchema = new energiEmissionSchema();
						emisSchema.setData(
							element.Minutes5DK,
							element.PriceArea,
							element.CO2Emission
						);
						payload.setData(emisSchema);

						kafka
							.publishEnergidataCo2Emission(JSON.stringify(payload))
							.then(() => {
								console.log('Done: ' + emisSchema);
							})
							.catch(e => {
								console.log(e);
							});
					});
					emissionElementsStored += response.data.result.records.length;
				})
				.catch(e => {
					console.log(e);
				});
		}, co2emisTimeout);
	} else {
		setTimeout(waitForKafka, 5000);
	}
}

waitForKafka();
