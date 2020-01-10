package dk.sdu.datascience;

import dk.sdu.datascience.kafka.KafkaClient;
import dk.sdu.datascience.kafka.structure.messages.EnerginetCO2Emission;
import dk.sdu.datascience.kafka.structure.messages.ProcessedEmissions;
import dk.sdu.datascience.kafka.structure.schemas.AllProcessedEmissionsSchema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ander
 */
public class KafkaConsumerThread extends Thread {
    private boolean newData = false;
    public KafkaConsumerThread(String str) {
        super(str);
    }

    public void run() {
        while(!newData){
            try {
                sleep((int)(Math.random() * 5000));
            } catch (InterruptedException e) {}
        }
        
    }

}
