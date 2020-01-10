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
        System.out.println("start");

        KafkaClient.consumerEnerginetCO2Emission(new KafkaClient.energidataCo2EmissionCallback() {
            @Override
            public void messageConsumed(EnerginetCO2Emission payload) {
                System.out.println("messageConsumed");
                AllProcessedEmissionsSchema newData = emisProc.process(payload);
                if (newData != null) {
                    ProcessedEmissions message = new ProcessedEmissions();
                    message.setAllProcessedEmissionsSchema(newData);
                    KafkaClient.produceprocessedEmissions(message);
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
