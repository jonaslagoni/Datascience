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
public class EnerginetCO2EmissionSchema {
    private String MINUTES5_DK;

    /**
     * @return MINUTES5_DK
     */
    public String getMINUTES5_DK() {
        return MINUTES5_DK;
    }

    /**
     * @param MINUTES5_DK to set
     */
    public void setMINUTES5_DK(String MINUTES5_DK) {
        this.MINUTES5_DK = MINUTES5_DK;
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
    private Double CO2_EMISSION;

    /**
     * @return CO2_EMISSION
     */
    public Double getCO2_EMISSION() {
        return CO2_EMISSION;
    }

    /**
     * @param CO2_EMISSION to set
     */
    public void setCO2_EMISSION(Double CO2_EMISSION) {
        this.CO2_EMISSION = CO2_EMISSION;
    }
}
