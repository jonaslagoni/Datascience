/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
import java.util.ArrayList;
import java.util.List;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.sum;

/**
 *
 * @author Lagoni
 */
public class EmissionProcessor {

    public AllProcessedEmissionsSchema process(EnerginetCO2Emission newData) {
        SparkSession spark = SparkSession
                .builder()
                .appName("datascience")
                .master("local[2]")
                .getOrCreate();

        if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("55:00+00:00")) {
            Dataset<Row> tempDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
            AllProcessedEmissionsSchema procScheme = new AllProcessedEmissionsSchema();
            List<AllProcessedEmissionsSchema.ProcessedEmissionsSchema> list = new ArrayList();

            if (newData.getEnerginetCO2EmissionSchema().getPRICE_AREA().equalsIgnoreCase("DK1")) {
                String timeStamp = tempDS.select("MINUTES5_DK").where("max").first().getString(0); // TODO get hours and the right hour
                String hourAverageAreaDK1 = tempDS.select(sum("CO2_EMISSION").cast("double")).where("PRICE_AREA = 'DK1'").first().getString(0);

                Dataset<Row> addToFullProcessedDataset = spark.emptyDataFrame();
                addToFullProcessedDataset = addToFullProcessedDataset.withColumn("HOUR_DK", functions.lit(timeStamp));
                addToFullProcessedDataset = addToFullProcessedDataset.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
                addToFullProcessedDataset = addToFullProcessedDataset.withColumn("ACTUAL_EMISSIONS", functions.lit(hourAverageAreaDK1));
                addToFullProcessedDataset.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");

                Dataset<Row> fullDS = spark.read().format("json").load("spark-submitter/src/main/resources/datasets/processedEmissionDataset.json");

                List<Row> rowList = fullDS.collectAsList();

                AllProcessedEmissionsSchema.ProcessedEmissionsSchema result = null; // TODO not sure dis is allright

                result.setHOUR_DK(timeStamp);
                result.setPRICE_AREA(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA());
                result.setACTUAL_EMISSIONS(hourAverageAreaDK1);

                list.add(result);

                procScheme.setAllProcessedEmissionsSchema(list);
            }
            // TODO might just do it in same if() ^^^
            /*if (newData.getEnerginetCO2EmissionSchema().getPRICE_AREA().equalsIgnoreCase("DK2")) {
                double hourAverageAreaDK2 = tempDS.select(sum("CO2_EMISSION").cast("double")).where("PRICE_AREA = 'DK2'").first().getDouble(0);
            }*/
             return procScheme;
        } else if (newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK().endsWith("00")) {
            Dataset<Row> tempDS = spark.emptyDataFrame();

            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));

            tempDS.write().mode(SaveMode.Overwrite).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");

            return null;
        } else {
            Dataset<Row> tempDS = spark.emptyDataFrame();

            tempDS = tempDS.withColumn("MINUTES5_DK", functions.lit(newData.getEnerginetCO2EmissionSchema().getMINUTES5_DK()));
            tempDS = tempDS.withColumn("PRICE_AREA", functions.lit(newData.getEnerginetCO2EmissionSchema().getPRICE_AREA()));
            tempDS = tempDS.withColumn("CO2_EMISSION", functions.lit(newData.getEnerginetCO2EmissionSchema().getCO2_EMISSION()));

            tempDS.write().mode(SaveMode.Append).json("spark-submitter/src/main/resources/datasets/temporaryEmissionDataset.json");
            return null;
        }
    }
}
