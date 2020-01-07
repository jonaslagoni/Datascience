
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
public class EnerginetElspotSchema {
    
        
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

        
    private Double SPOT_PRICE_EUR;

    /**
    * @return SPOT_PRICE_EUR
    */
    public Double getSPOT_PRICE_EUR() {
        return SPOT_PRICE_EUR;
    }

    /**
    * @param SPOT_PRICE_EUR to set
    */
    public void setSPOT_PRICE_EUR(Double SPOT_PRICE_EUR) {
        this.SPOT_PRICE_EUR = SPOT_PRICE_EUR;
    }


}
