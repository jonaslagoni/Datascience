/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import com.google.gson.Gson;
import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema.ProcessedEmissionsSchema;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
 * @author Lagoni
 */
public class EmissionProcessor {
    private Logger logger = Logger.getLogger("EmissionProcessor");
    public AllProcessedEmissionsSchema process(EnerginetCO2Emission newData) {
        SparkSession spark = SparkSession
                .builder()
                .appName("datascience")
                .getOrCreate();

        if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("55:00")) {
            logger.log(Level.INFO, "Procces emission data");

            Dataset<Row> tempDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
            String timeStamp = tempDS.select(col("MINUTES5_DK")).first().getString(0);
            String hourAverageAreaDK;
            if(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA().equals("DK1")){
                hourAverageAreaDK = tempDS.select(sum("CO2_EMISSION").cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);
            } else {
                hourAverageAreaDK = tempDS.select(sum("CO2_EMISSION").cast("double")).where("PRICE_AREA = 'DK2'").first().getString(0);
            }
            Dataset<Row> fullProcessedDataset = spark.emptyDataFrame();
            
            fullProcessedDataset = fullProcessedDataset.withColumn("HOUR_DK", functions.lit(timeStamp));
            fullProcessedDataset = fullProcessedDataset.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
            fullProcessedDataset = fullProcessedDataset.withColumn("ACTUAL_EMISSIONS", functions.lit(hourAverageAreaDK));
            fullProcessedDataset.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");

            return getProcessedList(spark);
        } else if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("00:00")) {
            logger.log(Level.INFO, "New/overwrite temp dataframe");
            Dataset<Row> tempDS = spark.emptyDataFrame();

            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));

            tempDS.write().mode(SaveMode.Overwrite).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");

            return getProcessedList(spark);
        } else {
            logger.log(Level.INFO, "Append to temp dataframe");
            Dataset<Row> tempDS = spark.emptyDataFrame();

            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));

            tempDS.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
            return getProcessedList(spark);
        }
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
