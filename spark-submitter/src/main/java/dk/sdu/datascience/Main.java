/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.structure.schemas.EnerginetCO2EmissionSchema;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author Lagoni
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader) cl).getURLs();

        for (URL url : urls) {
            System.out.println(url.getFile());
        }
        //new KafkaConsumerThread("Fiji").start();
        SparkConf config = new SparkConf();
        config.setAppName("datascience");
        //config.setMaster("local[4]");
        SparkSession spark = SparkSession
                .builder()
                .config(config)
                .getOrCreate();

        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", "postgres");
        connectionProperties.setProperty("password", "psql");
        connectionProperties.setProperty("driver", "org.postgresql.Driver");
        connectionProperties.setProperty("dbtable", "sparkdatabase");

        String url = "jdbc:postgresql://postgres:5432/sparkdatabase";
        String table = "TestTable";

        EnerginetCO2EmissionSchema testScheme = new EnerginetCO2EmissionSchema();
        testScheme.setCO2_EMISSION(25.1515);
        testScheme.setMINUTES5_DK("00-00-00");
        testScheme.setPRICE_AREA("DK1");
        List<EnerginetCO2EmissionSchema> list = new ArrayList();
        list.add(testScheme);

        Dataset<Row> squaresDF = spark.createDataFrame(list, EnerginetCO2EmissionSchema.class);

        squaresDF.write()
                .option("createTableColumnTypes", "MINUTES5_DK VARCHAR(64), PRICE_AREA VARCHAR(1024), CO2_EMISSION float").jdbc(url, table, connectionProperties);

        Dataset<Row> jdbcDF2 = spark.read()
                .jdbc(url, table, connectionProperties);
        jdbcDF2.show();
//
//        ClassLoader cl = ClassLoader.getSystemClassLoader();
//
//        URL[] urls = ((URLClassLoader) cl).getURLs();
//
//        for (URL url : urls) {
//            System.out.println(url.getFile());
//        }
//        EmissionProcessor emisProc = new EmissionProcessor();
//        System.out.println("start Emission");
//
//        KafkaClient.consumerEnerginetCO2Emission(new KafkaClient.energidataCo2EmissionCallback() {
//            @Override
//            public void messageConsumed(EnerginetCO2Emission payload) {
//                System.out.println("messageConsumed Emission");
//                AllProcessedEmissionsSchema newData = emisProc.process(payload);
//                if (newData != null) {
//                    ProcessedEmissions message = new ProcessedEmissions();
//                    message.setAllProcessedEmissionsSchema(newData);
//                    KafkaClient.produceprocessedEmissions(message);
//                }
//            }
//        });
//
//        ProducerProcessor prodProc = new ProducerProcessor();
//        System.out.println("start Production");
//
//        KafkaClient.consumerEnerginetProductionAndExchange(new KafkaClient.energidataProductionAndExchangeCallback() {
//            @Override
//            public void messageConsumed(EnerginetProductionAndExchange payload) {
//                System.out.println("messageConsumed Production");
//                AllProcessedProducedSchema newData = prodProc.process(payload);
//                if(newData != null){
//                    ProcessedProduced message = new ProcessedProduced();
//                    message.setAllProcessedProducedSchema(newData);
//                    KafkaClient.produceprocessedProduced(message);
//                }
//            }
//        });
//
//        SpotPriceProcessor spotProc = new SpotPriceProcessor();
//        System.out.println("start SpotPrice");
//
//        KafkaClient.consumerEnerginetElspot(new KafkaClient.energidataElspotCallback() {
//
//            @Override
//            public void messageConsumed(EnerginetElspot payload) {
//                System.out.println("messageConsumed SpotPrice");
//                AllProcessedSpotPricesSchema newData = spotProc.process(payload);
//                if(newData != null){
//                    ProcessedSpotPrices message = new ProcessedSpotPrices();
//                    message.setAllProcessedSpotPricesSchema(newData);
//                    KafkaClient.produceprocessedSpotPrices(message);
//                }
//            }
//        });
//        EnerginetCO2Emission test = new EnerginetCO2Emission();
//        EnerginetCO2EmissionSchema testScheme = new EnerginetCO2EmissionSchema();
//        testScheme.setCO2_EMISSION(25.1515);
//        testScheme.setMINUTES5_DK("00-00-00");
//        testScheme.setPRICE_AREA("DK1TEST");
//        test.setEnerginetCO2EmissionSchema(testScheme);
//        emisProc.process(test);
//        // TODO code application logic here
//        StatusMessage statusMessage = new StatusMessage();
//        Status status = new Status();
//        status.setEvent(Status.eventEnum.DONE);
//        statusMessage.setStatus(status);
//        KafkaClient.producedatascienceProcessedStatus(statusMessage);
    }

}
