/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.structure.messages.EnerginetElspot;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedSpotPricesSchema;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedSpotPricesSchema.ProcessedSpotPricesSchema;
import dk.sdu.datascience.kafka.structure.schemas.EnerginetCO2EmissionSchema;
import dk.sdu.datascience.kafka.structure.schemas.EnerginetElspotSchema;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.sum;

/**
 *
 * @author Ander
 */
public class SpotPriceProcessor {
    
    private Logger logger = Logger.getLogger("EmissionProcessor");
    List<EnerginetElspotSchema> spotPriceList = new ArrayList();
    List<ProcessedSpotPricesSchema> totalList = new ArrayList();
    
    public AllProcessedSpotPricesSchema process(EnerginetElspot newData) {
        SparkSession spark = SparkSession
                .builder()
                .appName("datascience")
                .getOrCreate();
        if (spotPriceList.size() == 12) {
            AllProcessedSpotPricesSchema procScheme = new AllProcessedSpotPricesSchema();
            
            AllProcessedSpotPricesSchema.ProcessedSpotPricesSchema result = new ProcessedSpotPricesSchema();
            result.setMONTH_DATE_DK((String) spotPriceList.get(0).getHOUR_DK());
            result.setPRICE_AREA((String) spotPriceList.get(0).getPRICE_AREA());
            int sum = 0;
            for (EnerginetElspotSchema i : spotPriceList) {
                sum += i.getSPOT_PRICE_EUR();
                sum = sum / 720;
            }
            result.setAVERAGE_SPOT_PRICE_EUR(Double.toString(sum));
            totalList.add(result);
            procScheme.setAllProcessedSpotPricesSchema(totalList);
            spotPriceList.clear();
            return procScheme;
        } else {
            spotPriceList.add(newData.getEnerginetElspotSchema());
        }
        return null;
        
//        if (newData.getHOUR_DK().endsWith("1 00:00")) {
//            Dataset<Row> tempDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/temporarySpotPrices.json");
//
//            String timeStamp = tempDS.select("HOUR_DK").first().getString(0);
//
//            String hourAverageSpotPriceEUR = "0";
//            if (newData.getPRICE_AREA().equals("DK1")) {
//                hourAverageSpotPriceEUR = tempDS.select(sum(col("SPOT_PRICE_EUR")).cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);
//            } else if (newData.getPRICE_AREA().equals("DK2")) {
//                hourAverageSpotPriceEUR = tempDS.select(sum(col("SPOT_PRICE_EUR")).cast("double")).where("PRICE_AREA = 'DK2'").first().getString(0);
//            }
//            
//            // TODO FOR HOLE MONTH NOT ONE DAY
//            
//            Dataset<Row> fullProcessedDataset = spark.emptyDataFrame();
//            fullProcessedDataset = fullProcessedDataset.withColumn("MONTH_DATE_DK", functions.lit(timeStamp));
//            fullProcessedDataset = fullProcessedDataset.withColumn("PRICE_AREA", functions.lit(newData.getPRICE_AREA()));
//            fullProcessedDataset = fullProcessedDataset.withColumn("AVERAGE_SPOT_PRICE_EUR", functions.lit(hourAverageSpotPriceEUR));
//            fullProcessedDataset.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/processedSpotPrices.json");
//
//            return getProcessedList(spark);
//        } else if (newData.getHOUR_DK().endsWith("1 01:00")) {
//            Dataset<Row> tempDS = spark.emptyDataFrame();
//
//            tempDS = tempDS.withColumn("HOUR_DK", functions.lit(newData.getHOUR_DK()));
//            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getPRICE_AREA()));
//            tempDS = tempDS.withColumn("SPOT_PRICE_EUR", functions.lit(newData.getSPOT_PRICE_EUR()));
//            tempDS.write().mode(SaveMode.Overwrite).json("spark-submitter/src/main/resources/datasets/temporarySpotPrices.json");
//
//            return null;
//        } else {
//            Dataset<Row> tempDS = spark.emptyDataFrame();
//
//            tempDS = tempDS.withColumn("HOUR_DK", functions.lit(newData.getHOUR_DK()));
//            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getPRICE_AREA()));
//            tempDS = tempDS.withColumn("SPOT_PRICE_EUR", functions.lit(newData.getSPOT_PRICE_EUR()));
//
//            tempDS.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/temporarySpotPrices.json");
//            return null;
//        }
    }

    public AllProcessedSpotPricesSchema getProcessedList(SparkSession spark) {
        File tmpDir = new File("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");
        boolean exists = tmpDir.exists();
        if (exists) {
            Dataset<Row> fullDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/processedSpotPrices.json");
            AllProcessedSpotPricesSchema procScheme = new AllProcessedSpotPricesSchema();
            List<AllProcessedSpotPricesSchema.ProcessedSpotPricesSchema> list = new ArrayList();

            List<Row> rowList = fullDS.collectAsList();

            AllProcessedSpotPricesSchema.ProcessedSpotPricesSchema result = new AllProcessedSpotPricesSchema.ProcessedSpotPricesSchema();

            for (int i = 0; i < rowList.size(); i++) {
                result.setMONTH_DATE_DK((String) rowList.get(0).getAs("MONTH_DATE_DK"));
                result.setPRICE_AREA((String) rowList.get(0).getAs("PRICE_AREA"));
                result.setAVERAGE_SPOT_PRICE_EUR((String) rowList.get(0).getAs("AVERAGE_SPOT_PRICE_EUR"));

                list.add(result);
            }
            procScheme.setAllProcessedSpotPricesSchema(list);
            return procScheme;
        }
        return null;
    }

}
