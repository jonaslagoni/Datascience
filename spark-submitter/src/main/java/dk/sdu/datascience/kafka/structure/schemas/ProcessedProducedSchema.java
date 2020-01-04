/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience.kafka.structure.schemas;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Async api generated
 */
public class ProcessedProducedSchema {
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
