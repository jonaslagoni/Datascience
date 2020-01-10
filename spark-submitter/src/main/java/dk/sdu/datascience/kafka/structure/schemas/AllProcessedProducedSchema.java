
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

    public static class ProcessedProducedSchema{
        
        
    private String DAY_DATE_DK;

    /**
    * @return DAY_DATE_DK
    */
    public String getDAY_DATE_DK() {
        return DAY_DATE_DK;
    }

    /**
    * @param DAY_DATE_DK to set
    */
    public void setDAY_DATE_DK(String DAY_DATE_DK) {
        this.DAY_DATE_DK = DAY_DATE_DK;
    }

        
    private String PRICE_AREA;

    /**
    * @return PRICE_AREA
    */
    public String getPRICE_AREA() {
        return PRICE_AREA;
    }

    /**
    * @param PRICE_AREA to set
    */
    public void setPRICE_AREA(String PRICE_AREA) {
        this.PRICE_AREA = PRICE_AREA;
    }

        
    private String TOTAL_MWH_PRODUCED;

    /**
    * @return TOTAL_MWH_PRODUCED
    */
    public String getTOTAL_MWH_PRODUCED() {
        return TOTAL_MWH_PRODUCED;
    }

    /**
    * @param TOTAL_MWH_PRODUCED to set
    */
    public void setTOTAL_MWH_PRODUCED(String TOTAL_MWH_PRODUCED) {
        this.TOTAL_MWH_PRODUCED = TOTAL_MWH_PRODUCED;
    }


    }

}
