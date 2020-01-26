/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.structure.messages.EnerginetElspot;
import dk.sdu.datascience.kafka.structure.schemas.AllEnerginetElspotSchema;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedSpotPricesSchema;
import dk.sdu.datascience.spark.Controller;
import dk.sdu.datascience.spark.ISparkConstants;
import java.util.logging.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Ander
 */
public class SpotPriceProcessor {

    private Logger logger = Logger.getLogger("SpotPriceProcessor");

    public AllProcessedSpotPricesSchema process(EnerginetElspot newData) {

        SparkSession spark = Controller.getInstance().getSpark();
        Dataset<Row> squaresDF = spark.createDataFrame(newData.getAllEnerginetElspotSchema().getAllEnerginetElspotSchema(), AllEnerginetElspotSchema.EnerginetElspotSchema.class);

        squaresDF.write().mode(SaveMode.Append).jdbc(ISparkConstants.SPARK_POSTGRES_URL, "SpotPrice", Controller.getInstance().getConnectionProperties());
        return null;
    }
}
