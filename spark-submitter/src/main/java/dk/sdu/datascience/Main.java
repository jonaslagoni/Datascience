/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience;

import dk.sdu.datascience.kafka.KafkaClient;
import dk.sdu.datascience.kafka.structure.messages.StatusMessage;
import dk.sdu.datascience.kafka.structure.schemas.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lagoni
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            StatusMessage statusMessage = new StatusMessage();
            Status status = new Status();
            status.setEvent(Status.eventEnum.DONE);
            statusMessage.setStatus(status);
            KafkaClient.producedatascienceProcessedStatus(statusMessage);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
