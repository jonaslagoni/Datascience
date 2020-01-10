
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
public class AllProcessedSpotPricesSchema {
    
    private List<ProcessedSpotPricesSchema> allProcessedSpotPricesSchema;

    /**
    * @return allProcessedSpotPricesSchema
    */
    public List<ProcessedSpotPricesSchema> getAllProcessedSpotPricesSchema() {
        return allProcessedSpotPricesSchema;
    }

    /**
    * @param allProcessedSpotPricesSchema to set
    */
    public void setAllProcessedSpotPricesSchema(List<ProcessedSpotPricesSchema> allProcessedSpotPricesSchema) {
        this.allProcessedSpotPricesSchema = allProcessedSpotPricesSchema;
    }

    public class ProcessedSpotPricesSchema{
        
        
    private String MONTH_DATE_DK;

    /**
    * @return MONTH_DATE_DK
    */
    public String getMONTH_DATE_DK() {
        return MONTH_DATE_DK;
    }

    /**
    * @param MONTH_DATE_DK to set
    */
    public void setMONTH_DATE_DK(String MONTH_DATE_DK) {
        this.MONTH_DATE_DK = MONTH_DATE_DK;
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

        
    private String AVERAGE_SPOT_PRICE_EUR;

    /**
    * @return AVERAGE_SPOT_PRICE_EUR
    */
    public String getAVERAGE_SPOT_PRICE_EUR() {
        return AVERAGE_SPOT_PRICE_EUR;
    }

    /**
    * @param AVERAGE_SPOT_PRICE_EUR to set
    */
    public void setAVERAGE_SPOT_PRICE_EUR(String AVERAGE_SPOT_PRICE_EUR) {
        this.AVERAGE_SPOT_PRICE_EUR = AVERAGE_SPOT_PRICE_EUR;
    }


    }

}
