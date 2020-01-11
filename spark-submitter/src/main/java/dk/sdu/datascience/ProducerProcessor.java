/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

//import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.messages.EnerginetProductionAndExchange;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedProducedSchema;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.sum;

//import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
//import dk.sdu.datascience.kafka.structure.schemas.AllProcessedProducedSchema;
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SaveMode;
//import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.functions;
//import static org.apache.spark.sql.functions.sum;
/**
 *
 * @author Lagoni
 */
public class ProducerProcessor {

    public AllProcessedProducedSchema process(EnerginetProductionAndExchange newData) {
        SparkSession spark = SparkSession
                .builder()
                .appName("datascience")
                .getOrCreate();

        if (newData.getEnerginetProductionAndExchangeSchema().getMINUTES5_DK().endsWith("55:00")) {
            Dataset<Row> tempDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/temporaryProductionAndExchange.json");

            String timeStamp = tempDS.select(col("MINUTES5_DK")).first().getString(0);

            String hourAverageProuctionLT;
            String hourAverageProuctionGE;
            double totalAverage;
            if (newData.getEnerginetProductionAndExchangeSchema().getPRICE_AREA().equals("DK1")) {
                hourAverageProuctionLT = tempDS.select(sum(col("PRODUCTION_LT_100")).cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);
                hourAverageProuctionGE = tempDS.select(sum(col("PRODUCTION_GE_100")).cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);
            } else {
                hourAverageProuctionLT = tempDS.select(sum(col("PRODUCTION_LT_100")).cast("double")).where("PRICE_AREA = 'DK2'").first().getString(0);
                hourAverageProuctionGE = tempDS.select(sum(col("PRODUCTION_GE_100")).cast("double")).where("PRICE_AREA = 'DK2'").first().getString(0);
            }
            totalAverage = Double.parseDouble(hourAverageProuctionLT) + Double.parseDouble(hourAverageProuctionGE);

            Dataset<Row> fullProcessedDataset = spark.emptyDataFrame();
            fullProcessedDataset = fullProcessedDataset.withColumn("DAY_DATE_DK", functions.lit(timeStamp));
            fullProcessedDataset = fullProcessedDataset.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRICE_AREA()));
            fullProcessedDataset = fullProcessedDataset.withColumn("TOTAL_MWH_PRODUCED", functions.lit(totalAverage));
            fullProcessedDataset.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/processedProductionAndExchange.json");

            return getProcessedList(spark);
        } else if (newData.getEnerginetProductionAndExchangeSchema().getMINUTES5_DK().endsWith("00:00")) {
            Dataset<Row> tempDS = spark.emptyDataFrame();

            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getMINUTES5_DK()));
            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRICE_AREA()));
            tempDS = tempDS.withColumn("PRODUCTION_LT_100", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRODUCTION_LT_100()));
            tempDS = tempDS.withColumn("PRODUCTION_GE_100", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRODUCTION_GE_100()));
            tempDS.write().mode(SaveMode.Overwrite).json("spark-submitter/src/main/resources/datasets/temporaryProductionAndExchange.json");

            return null;
        } else {
            Dataset<Row> tempDS = spark.emptyDataFrame();

            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getMINUTES5_DK()));
            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRICE_AREA()));
            tempDS = tempDS.withColumn("PRODUCTION_LT_100", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRODUCTION_LT_100()));
            tempDS = tempDS.withColumn("PRODUCTION_GE_100", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRODUCTION_GE_100()));

            tempDS.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/temporaryProductionAndExchange.json");
            return null;
        }
    }

    public AllProcessedProducedSchema getProcessedList(SparkSession spark) {
        File tmpDir = new File("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");
        boolean exists = tmpDir.exists();
        if (exists) {
            Dataset<Row> fullDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/processedProductionAndExchange.json");
            AllProcessedProducedSchema procScheme = new AllProcessedProducedSchema();
            List<AllProcessedProducedSchema.ProcessedProducedSchema> list = new ArrayList();

            List<Row> rowList = fullDS.collectAsList();

            AllProcessedProducedSchema.ProcessedProducedSchema result = new AllProcessedProducedSchema.ProcessedProducedSchema();

            for (int i = 0; i < rowList.size(); i++) {
                result.setDAY_DATE_DK((String) rowList.get(0).getAs("HOUR_DK"));
                result.setPRICE_AREA((String) rowList.get(0).getAs("PRICE_AREA"));
                result.setTOTAL_MWH_PRODUCED((String) rowList.get(0).getAs("ACTUAL_EMISSIONS"));

                list.add(result);
            }
            procScheme.setAllProcessedProducedSchema(list);
            return procScheme;
        }
        return null;
    }

}
