/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

//import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.messages.EnerginetProductionAndExchange;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedProducedSchema;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
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

//    public AllProcessedProducedSchema process(EnerginetProductionAndExchange newData) {
//        SparkSession spark = SparkSession
//                .builder()
//                .appName("datascience")
//                .master("local[2]")
//                .getOrCreate();
//
//        if (newData.getEnerginetProductionAndExchangeSchema().getMINUTES5_DK().endsWith("55:00+00:00")) {
//            Dataset<Row> tempDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/temporaryProductionAndExchange.json");
//
//            String timeStamp = tempDS.select("MINUTES5_DK").first().getString(0);
//            String hourAverageProuctionLT = tempDS.select(sum("PRODUCTION_LT_100").cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);
//            String hourAverageProuctionGE = tempDS.select(sum("PRODUCTION_GE_100").cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);
//            Dataset<Row> fullProcessedDataset = spark.emptyDataFrame();
//            fullProcessedDataset = fullProcessedDataset.withColumn("DAY_DATE_DK", functions.lit(timeStamp));
//            fullProcessedDataset = fullProcessedDataset.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetProductionAndExchangeSchema().getPRICE_AREA()));
//            fullProcessedDataset = fullProcessedDataset.withColumn("TOTAL_MWH_PRODUCED", functions.lit(hourAverageAreaDK));
//            fullProcessedDataset.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");
//
//            return getProcessedList(spark);
//        } else if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("00:00+00:00")) {
//            Dataset<Row> tempDS = spark.emptyDataFrame();
//
//            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
//            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
//            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));
//
//            tempDS.write().mode(SaveMode.Overwrite).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
//
//            return null;
//        } else {
//            Dataset<Row> tempDS = spark.emptyDataFrame();
//
//            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
//            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
//            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));
//
//            tempDS.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
//            return getProcessedList(spark);
//        }
//    }
//
//    public AllProcessedEmissionsSchema getProcessedList(SparkSession spark) {
//        Dataset<Row> fullDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");
//        AllProcessedEmissionsSchema procScheme = new AllProcessedEmissionsSchema();
//        List<AllProcessedEmissionsSchema.ProcessedEmissionsSchema> list = new ArrayList();
//
//        List<Row> rowList = fullDS.collectAsList();
//
//        AllProcessedEmissionsSchema.ProcessedEmissionsSchema result = new AllProcessedEmissionsSchema.ProcessedEmissionsSchema();
//
//        for (int i = 0; i < rowList.size(); i++) {
//            result.setHOUR_DK((String) rowList.get(0).getAs("HOUR_DK"));
//            result.setPRICE_AREA((String) rowList.get(0).getAs("PRICE_AREA"));
//            result.setACTUAL_EMISSIONS((String) rowList.get(0).getAs("ACTUAL_EMISSIONS"));
//
//            list.add(result);
//        }
//        procScheme.setAllProcessedEmissionsSchema(list);
//        return procScheme;
//    }

}
