/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema.ProcessedEmissionsSchema;
import dk.sdu.datascience.kafka.structure.schemas.EnerginetCO2EmissionSchema;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


/**
 *
 * @author Lagoni
 */
public class EmissionProcessor {

    private Logger logger = Logger.getLogger("EmissionProcessor");
    List<EnerginetCO2EmissionSchema> emissionsList = new ArrayList();
    List<ProcessedEmissionsSchema> totalList = new ArrayList();

    public AllProcessedEmissionsSchema process(EnerginetCO2Emission newData) {
        SparkSession spark = SparkSession
                .builder()
                .appName("datascience")
                .getOrCreate();
        if (emissionsList.size() == 12) {
            AllProcessedEmissionsSchema procScheme = new AllProcessedEmissionsSchema();

            AllProcessedEmissionsSchema.ProcessedEmissionsSchema result = new ProcessedEmissionsSchema();
            result.setHOUR_DK((String) emissionsList.get(0).getMINUTES5_DK());
            result.setPRICE_AREA((String) emissionsList.get(0).getPRICE_AREA());
            int sum = 0;
            for (EnerginetCO2EmissionSchema i : emissionsList) {
                sum += i.getCO2_EMISSION();
            }
            result.setACTUAL_EMISSIONS(Double.toString(sum));
            totalList.add(result);
            procScheme.setAllProcessedEmissionsSchema(totalList);
            emissionsList.clear();
            return procScheme;
        } else {
            emissionsList.add(newData.getEnerginetCO2EmissionSchema());
        }
        return null;
//        if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("55:00")) {
//            logger.log(Level.INFO, "Procces emission data");
//
//            return getProcessedList(spark);
//        } else if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("00:00")) {
//            logger.log(Level.INFO, "New/overwrite temp dataframe");
//            Dataset<Row> tempDS = spark.emptyDataFrame();
//
//            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
//            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
//            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));
//
//            tempDS.write().mode(SaveMode.Overwrite).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
//
//            Dataset<Row> test = spark.read().format("json").option("inferSchema", true).load("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
//            test.printSchema();
//            return null;
//        } else {
//            logger.log(Level.INFO, "Append to temp dataframe");
//            Dataset<Row> tempDS = spark.emptyDataFrame();
//
//            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
//            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
//            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));
//            tempDS.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
//            Dataset<Row> test = spark.read().format("json").option("inferSchema", true).load("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
//            test.printSchema();
//            return null;
//        }
    }

    public AllProcessedEmissionsSchema getProcessedList(SparkSession spark) {
        logger.log(Level.INFO, "New/overwrite temp dataframe");
        File tmpDir = new File("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");
        boolean exists = tmpDir.exists();
        if (exists) {
            Dataset<Row> fullDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");
            AllProcessedEmissionsSchema procScheme = new AllProcessedEmissionsSchema();
            List<AllProcessedEmissionsSchema.ProcessedEmissionsSchema> list = new ArrayList();

            List<Row> rowList = fullDS.collectAsList();

            AllProcessedEmissionsSchema.ProcessedEmissionsSchema result = new ProcessedEmissionsSchema();

            for (int i = 0; i < rowList.size(); i++) {
                result.setHOUR_DK((String) rowList.get(0).getAs("HOUR_DK"));
                result.setPRICE_AREA((String) rowList.get(0).getAs("PRICE_AREA"));
                result.setACTUAL_EMISSIONS((String) rowList.get(0).getAs("ACTUAL_EMISSIONS"));

                list.add(result);
            }
            procScheme.setAllProcessedEmissionsSchema(list);
            return procScheme;
        }

        return null;
    }

}
