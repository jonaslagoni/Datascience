/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience.spark;

import java.util.Properties;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Lagoni
 */
public class Controller {

    private final SparkSession spark;
    private final Properties connectionProperties;
    private static Controller instance = null;

    public Controller() {
        SparkConf config = new SparkConf();
        config.setAppName(ISparkConstants.SPARK_APPNAME);
        //config.setMaster("local[4]");
        spark = SparkSession
                .builder()
                .config(config)
                .getOrCreate();

        connectionProperties = new Properties();
        connectionProperties.setProperty("user", ISparkConstants.SPARK_POSTGRES_USER);
        connectionProperties.setProperty("password", ISparkConstants.SPARK_POSTGRES_PASS);
        connectionProperties.setProperty("driver", ISparkConstants.SPARK_POSTGRES_DRIVER);
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * @return the spark
     */
    public SparkSession getSpark() {
        return spark;
    }

    /**
     * @return the connectionProperties
     */
    public Properties getConnectionProperties() {
        return connectionProperties;
    }

}
