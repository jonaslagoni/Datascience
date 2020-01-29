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
import org.apache.spark.sql.Column;
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

    private Logger logger = Logger.getLogger("ProducerProcessor");

    public AllProcessedProducedSchema process() {
        SparkSession spark = Controller.getInstance().getSparkSession();
        Dataset<Row> schemaDF = spark.read().jdbc(ISparkConstants.SPARK_POSTGRES_URL, "Producers", Controller.getInstance().getConnectionProperties());
        Column dateTimeCol = schemaDF.col("MINUTES5_DK");
        //2018-05-25T02:50:00
        Column dateCol = functions.split(dateTimeCol, "T").getItem(0);
        //2018-05-25
        schemaDF = schemaDF.withColumn("date", dateCol);
        schemaDF = schemaDF.groupBy("date", "PRICE_AREA")
                .agg(sum("PRODUCTION_LT_100").as("PRODUCTION_LT_100"), sum("PRODUCTION_GE_100").as("PRODUCTION_GE_100")).sort(functions.asc("date"));
        schemaDF = schemaDF.withColumn("total", schemaDF.col("PRODUCTION_LT_100").plus(schemaDF.col("PRODUCTION_GE_100")));
        schemaDF.show();
        System.exit(0);
        return null;
    }

    public void save(EnerginetProductionAndExchange newData) {
        SparkSession spark = Controller.getInstance().getSparkSession();
        Dataset<Row> schemaDF = spark.createDataFrame(newData.getAllEnerginetProductionAndExchangeSchema().getAllEnerginetProductionAndExchangeSchema(), AllEnerginetProductionAndExchangeSchema.EnerginetProductionAndExchangeSchema.class);
        schemaDF.write().mode(SaveMode.Append).jdbc(ISparkConstants.SPARK_POSTGRES_URL, "Producers", Controller.getInstance().getConnectionProperties());
    }

}
