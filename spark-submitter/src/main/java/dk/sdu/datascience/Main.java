/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.KafkaClient;
import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.messages.ProcessedEmissions;
import dk.sdu.datascience.kafka.structure.messages.StatusMessage;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;
import dk.sdu.datascience.kafka.structure.schemas.Status;

/**
 *
 * @author Lagoni
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EmissionProcessor emisProc = new EmissionProcessor();
        KafkaClient.consumerEnerginetCO2Emission(new KafkaClient.energidataCo2EmissionCallback() {
            @Override
            public void messageConsumed(EnerginetCO2Emission payload) {
                AllProcessedEmissionsSchema newData = emisProc.process(payload);
                ProcessedEmissions message = new ProcessedEmissions();
                message.setAllProcessedEmissionsSchema(newData);
                KafkaClient.produceprocessedEmissions(message);
            }
        });

        // TODO code application logic here
        StatusMessage statusMessage = new StatusMessage();
        Status status = new Status();
        status.setEvent(Status.eventEnum.DONE);
        statusMessage.setStatus(status);
        KafkaClient.producedatascienceProcessedStatus(statusMessage);
    }

}
