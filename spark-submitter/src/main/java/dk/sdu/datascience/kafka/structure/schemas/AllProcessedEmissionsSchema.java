
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

    public class ProcessedEmissionsSchema{
        
        
    private String HOUR_DK;

    /**
    * @return HOUR_DK
    */
    public String getHOUR_DK() {
        return HOUR_DK;
    }

    /**
    * @param HOUR_DK to set
    */
    public void setHOUR_DK(String HOUR_DK) {
        this.HOUR_DK = HOUR_DK;
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

        
    private String ACTUAL_EMISSIONS;

    /**
    * @return ACTUAL_EMISSIONS
    */
    public String getACTUAL_EMISSIONS() {
        return ACTUAL_EMISSIONS;
    }

    /**
    * @param ACTUAL_EMISSIONS to set
    */
    public void setACTUAL_EMISSIONS(String ACTUAL_EMISSIONS) {
        this.ACTUAL_EMISSIONS = ACTUAL_EMISSIONS;
    }


    }

}
