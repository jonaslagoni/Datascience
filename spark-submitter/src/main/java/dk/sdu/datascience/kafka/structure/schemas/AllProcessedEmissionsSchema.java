/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience.kafka.structure.schemas;

import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 *
 * @author Async api generated
 */
public class AllProcessedEmissionsSchema {
    private List<ProcessedEmissionsSchema> allProcessedEmissionsSchema;

    /**
    * @return allProcessedEmissionsSchema
    */
    public List<ProcessedEmissionsSchema> getAllProcessedEmissionsSchema() {
        return allProcessedEmissionsSchema;
    }

    /**
    * @param allProcessedEmissionsSchema to set
    */
    public void setAllProcessedEmissionsSchema(List<ProcessedEmissionsSchema> allProcessedEmissionsSchema) {
        this.allProcessedEmissionsSchema = allProcessedEmissionsSchema;
    }
}