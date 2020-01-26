const axios = require('axios');
const kafka = require('./kafka/Index');

const allEnergiProductionSchema = require('./kafka/schemas/AllEnerginetProductionAndExchangeSchema');
const allEnergiSpotPricesSchema = require('./kafka/schemas/AllEnerginetElspotSchema');
const allEnergiEmissionSchema = require('./kafka/schemas/AllEnerginetCO2EmissionSchema');
const energiProductionSchema = require('./kafka/schemas/EnerginetProductionAndExchangeSchema');
const energiSpotPricesSchema = require('./kafka/schemas/EnerginetElspotSchema');
const energiEmissionSchema = require('./kafka/schemas/EnerginetCO2EmissionSchema');
const energiProduction = require('./kafka/messages/EnerginetProductionAndExchange');
const energiSpotPrices = require('./kafka/messages/EnerginetElspot');
const energiEmission = require('./kafka/messages/EnerginetCO2Emission');

let productionElementsStored = 0;
let emissionElementsStored = 0;
let elspotElementsStored = 0;
let electricityprodex5minrealtimeTimeout = 2000;
let elspotpricesTimeout = 2000;
let co2emisTimeout = 2000;
function waitForKafka() {
	if (kafka.isConnected) {
		setInterval(function() {
			axios
				.get(
					`https://api.energidataservice.dk/datastore_search_sql?sql=${`SELECT * from "electricityprodex5minrealtime" LIMIT 100 OFFSET ${productionElementsStored}`.replace(
						' ',
						'%20'
					)}`
				)
				.then(response => {
					const payload = new energiProduction();
					const allschemas = new allEnergiProductionSchema();
					allschemas.setData([]);
					response.data.result.records.forEach(element => {
						const energiSchema = new energiProductionSchema();
						energiSchema.setData(
							element.Minutes5DK,
							element.PriceArea,
							element.ProductionLt100MW,
							element.ProductionGe100MW
						);
						allschemas.energinetProductionAndExchangeSchema.push(energiSchema);
					});
					payload.setData(allschemas);
					kafka
						.publishEnergidataProductionAndExchange(JSON.stringify(payload))
						.then(() => {
							console.log('Done: ');
						})
						.catch(e => {
							console.log(e);
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
					`https://api.energidataservice.dk/datastore_search_sql?sql=${`SELECT * from "elspotprices" LIMIT 100 OFFSET ${elspotElementsStored}`.replace(
						' ',
						'%20'
					)}`
				)
				.then(response => {
					const payload = new energiSpotPrices();
					const allSchemas = new allEnergiSpotPricesSchema();
					allSchemas.setData([]);
					response.data.result.records.forEach(element => {
						const spotPriceSchema = new energiSpotPricesSchema();
						spotPriceSchema.setData(
							element.HourDK,
							element.PriceArea,
							element.SpotPriceEUR
						);
						allSchemas.energinetElspotSchema.push(spotPriceSchema);
					});
					payload.setData(allSchemas);
					kafka
						.publishEnergidataElspot(JSON.stringify(payload))
						.then(() => {
							console.log('Done: ');
						})
						.catch(e => {
							console.log(e);
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
					`https://api.energidataservice.dk/datastore_search_sql?sql=${`SELECT * from "co2emis" LIMIT 100 OFFSET ${emissionElementsStored}`.replace(
						' ',
						'%20'
					)}`
				)
				.then(response => {
					const payload = new energiEmission();
					const allSchemas = new allEnergiEmissionSchema();
					allSchemas.setData([]);
					response.data.result.records.forEach(element => {
						const emisSchema = new energiEmissionSchema();
						emisSchema.setData(
							element.Minutes5DK,
							element.PriceArea,
							element.CO2Emission
						);
						allSchemas.energinetCO2EmissionSchema.push(emisSchema);
					});
					payload.setData(allSchemas);

					kafka
						.publishEnergidataCo2Emission(
							JSON.stringify({
								allEnerginetCO2EmissionSchema: payload
							})
						)
						.then(() => {
							console.log('Done: ');
						})
						.catch(e => {
							console.log(e);
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
