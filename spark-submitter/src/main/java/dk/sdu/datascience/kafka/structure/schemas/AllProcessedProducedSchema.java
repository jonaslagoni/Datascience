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
public class AllProcessedProducedSchema {
    private List<ProcessedProducedSchema> allProcessedProducedSchema;

    /**
    * @return allProcessedProducedSchema
    */
    public List<ProcessedProducedSchema> getAllProcessedProducedSchema() {
        return allProcessedProducedSchema;
    }

    /**
    * @param allProcessedProducedSchema to set
    */
    public void setAllProcessedProducedSchema(List<ProcessedProducedSchema> allProcessedProducedSchema) {
        this.allProcessedProducedSchema = allProcessedProducedSchema;
    }
}
