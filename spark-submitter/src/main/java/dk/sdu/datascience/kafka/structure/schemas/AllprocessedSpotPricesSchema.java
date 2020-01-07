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
public class AllprocessedSpotPricesSchema {
    private List<ProcessedSpotPricesSchema> allprocessedSpotPricesSchema;

    /**
    * @return allprocessedSpotPricesSchema
    */
    public List<ProcessedSpotPricesSchema> getAllprocessedSpotPricesSchema() {
        return allprocessedSpotPricesSchema;
    }

    /**
    * @param allprocessedSpotPricesSchema to set
    */
    public void setAllprocessedSpotPricesSchema(List<ProcessedSpotPricesSchema> allprocessedSpotPricesSchema) {
        this.allprocessedSpotPricesSchema = allprocessedSpotPricesSchema;
    }
}
