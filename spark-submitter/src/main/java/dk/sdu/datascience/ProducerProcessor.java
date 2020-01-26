/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

//import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.messages.EnerginetProductionAndExchange;
import dk.sdu.datascience.kafka.structure.schemas.AllEnerginetProductionAndExchangeSchema;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedProducedSchema;
import dk.sdu.datascience.spark.Controller;
import dk.sdu.datascience.spark.ISparkConstants;
import java.util.logging.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

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

    private Logger logger = Logger.getLogger("ProducerProcessor");

    public AllProcessedProducedSchema process(EnerginetProductionAndExchange newData) {

        SparkSession spark = Controller.getInstance().getSpark();
        Dataset<Row> squaresDF = spark.createDataFrame(newData.getAllEnerginetProductionAndExchangeSchema().getAllEnerginetProductionAndExchangeSchema(), AllEnerginetProductionAndExchangeSchema.EnerginetProductionAndExchangeSchema.class);

        squaresDF.write().mode(SaveMode.Append).jdbc(ISparkConstants.SPARK_POSTGRES_URL, "Producers", Controller.getInstance().getConnectionProperties());
        return null;
    }

}
