/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.KafkaClient;
import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.messages.ProcessedEmissions;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
import dk.sdu.datascience.kafka.structure.messages.EnerginetProductionAndExchange;
import dk.sdu.datascience.kafka.structure.messages.ProcessedProduced;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedProducedSchema;
import dk.sdu.datascience.kafka.structure.messages.EnerginetElspot;
import dk.sdu.datascience.kafka.structure.messages.ProcessedSpotPrices;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedSpotPricesSchema;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Lagoni
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //new KafkaConsumerThread("Fiji").start();

        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader) cl).getURLs();

        for (URL url : urls) {
            System.out.println(url.getFile());
        }
        EmissionProcessor emisProc = new EmissionProcessor();
        System.out.println("start Emission");

        KafkaClient.consumerEnerginetCO2Emission(new KafkaClient.energidataCo2EmissionCallback() {
            @Override
            public void messageConsumed(EnerginetCO2Emission payload) {
                System.out.println("messageConsumed Emission");
                AllProcessedEmissionsSchema newData = emisProc.process(payload);
                if (newData != null) {
                    ProcessedEmissions message = new ProcessedEmissions();
                    message.setAllProcessedEmissionsSchema(newData);
                    KafkaClient.produceprocessedEmissions(message);
                }
            }
        });
        
        ProducerProcessor prodProc = new ProducerProcessor();
        System.out.println("start Production");
        
        KafkaClient.consumerEnerginetProductionAndExchange(new KafkaClient.energidataProductionAndExchangeCallback() {
            @Override
            public void messageConsumed(EnerginetProductionAndExchange payload) {
                System.out.println("messageConsumed Production");
                AllProcessedProducedSchema newData = prodProc.process(payload);
                if(newData != null){
                    ProcessedProduced message = new ProcessedProduced();
                    message.setAllProcessedProducedSchema(newData);
                    KafkaClient.produceprocessedProduced(message);
                }
            }
        });
        
        SpotPriceProcessor spotProc = new SpotPriceProcessor();
        System.out.println("start SpotPrice");
        
        KafkaClient.consumerEnerginetElspot(new KafkaClient.energidataElspotCallback() {

            @Override
            public void messageConsumed(EnerginetElspot payload) {
                System.out.println("messageConsumed SpotPrice");
                AllProcessedSpotPricesSchema newData = spotProc.process(payload);
                if(newData != null){
                    ProcessedSpotPrices message = new ProcessedSpotPrices();
                    message.setAllProcessedSpotPricesSchema(newData);
                    KafkaClient.produceprocessedSpotPrices(message);
                }
            }
        });
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
