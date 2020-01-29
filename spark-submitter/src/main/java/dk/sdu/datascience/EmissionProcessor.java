/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.schemas.AllEnerginetCO2EmissionSchema;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
import dk.sdu.datascience.spark.Controller;
import dk.sdu.datascience.spark.ISparkConstants;
import java.util.logging.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Lagoni
 */
public class EmissionProcessor {

    private Logger logger = Logger.getLogger("EmissionProcessor");

    public AllProcessedEmissionsSchema process(EnerginetCO2Emission newData) {
        SparkSession spark = Controller.getInstance().getSparkSession();
        Dataset<Row> squaresDF = spark.createDataFrame(newData.getAllEnerginetCO2EmissionSchema().getAllEnerginetCO2EmissionSchema(), AllEnerginetCO2EmissionSchema.EnerginetCO2EmissionSchema.class);

        squaresDF.write().mode(SaveMode.Append).jdbc(ISparkConstants.SPARK_POSTGRES_URL, "Emissions", Controller.getInstance().getConnectionProperties());

        return null;
    }
}
