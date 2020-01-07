
const emmisionSchema = require("./kafka/schemas/ProcessedEmissionsSchema");
const producedSchema = require("./kafka/schemas/ProcessedProducedSchema");

var lineArray = []
var lineDK1 = [];
var lineDK2 = [];

var emissionsArray = []
emissionsArray.push(new emmisionSchema("Hour_DK", "PriceArea", "AverageEmissions"));

var producedArray = []
producedArray.push(new producedSchema("Hour_DK", "PriceArea", "Total_MWh_Produced"));

/**
 *Call methods for parsing data, and creating graph from said data
 */
parseData(emissionsArray, producedArray);
createGraphForEmissions(lineArray);


/**
 * parsedata, retrive data for first time, and set the lineDK1, lineDK2 array and put them in the lineArray.
 * @param {*} emissionsArray 
 * @param {*} producedArray 
 */

function parseData(emissionsArray, producedArray) {

    var emissionsArray = emissionsArray;
    var producedArray = producedArray;
    

    emissionsArray.forEach(function (emissionsSchema, index) {
        var hour_DK_Emission = emissionsSchema.HOUR_DK;
        var price_area_Emission = emissionSchema.PRICE_AREA;
        var averageEmission = emissionsSchema.AVERAGE_EMISSIONS;

        var hour_DK_produced;
        var price_area_produced;
        var total_MWh_Produced;

        producedArray.forEach(function (producedSchema, index) {
            hour_DK_produced = producedSchema.HOUR_DK;
            price_area_produced = producedSchema.PRICE_AREA;
            total_MWh_Produced = producedSchema.TOTAL_MWH_PRODUCED;
        })

        if (hour_DK_produced == hour_DK_Emission) {

            if (price_area_produced && price_area_Emission == "DK1") {
                var calculatedEmission = calculateEmission(total_MWh_Produced, averageEmission)

                var pointDK1 = {
                    Hour_DK: hour_DK_produced,
                    PriceArea: price_area_produced,
                    calculatedEmission: calculatedEmission
                };

                lineDK1.push(pointDK1);

            } else if (price_area_produced && price_area_Emission == "DK2") {
                var calculatedEmission = calculateEmission(total_MWh_Produced, averageEmission)

                var pointDK2 = {
                    Hour_DK: hour_DK_produced,
                    PriceArea: price_area_produced,
                    calculatedEmission: calculatedEmission
                };

                lineDK2.push(pointDK2);
            }
        }

    });

    lineArray.push(lineDK1, lineDK2);
    if(Array.isArray(lineArray) && lineArray.length){
        return lineArray;
    }
   
}



function calculateEmission(total_MWh_Produced, averageEmission) {
    /**
      * Calculate amount of C02 gasses in tons for the given hour.
      * The method starts by converting the MWh produced into kWh and then mulitplying the number with the average C02 emission per kWh for that hour
      * it then converts the number of C02 emission from grams to tons
         */
    var calculatedEmission = ((total_MWh_Produced * 1000) * averageEmission) / 1000000
    return calculatedEmission;
}

function getLineArray(){
    return lineArray;
}



/**
 * Create a chart using D3
 * @param {*} jsonDataSet 
 */
function createGraphForEmissions(lineArray) {

    var jsonDataSet = jsonDataSet

    var svgWidth = 600, svgHeight = 400;
    var margin = { top: 20, right: 20, bottom: 30, left: 50 }
    var width = svgWidth - margin.left - margin.right;
    var height = svgHeight - margin.top - margin.top;


    var svg = d3.select('svg')
        .attr("width", svgWidth)
        .attr("height", svgHeight);

    var g = svg.append("data")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")")



}
