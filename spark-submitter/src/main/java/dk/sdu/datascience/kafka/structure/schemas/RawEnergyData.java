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
public class RawEnergyData {
    private String month;

    /**
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }
    private int municipality;

    /**
     * @return municipality
     */
    public int getMunicipality() {
        return municipality;
    }

    /**
     * @param municipality to set
     */
    public void setMunicipality(int municipality) {
        this.municipality = municipality;
    }
    private Double onShoreWind;

    /**
     * @return onShoreWind
     */
    public Double getOnShoreWind() {
        return onShoreWind;
    }

    /**
     * @param onShoreWind to set
     */
    public void setOnShoreWind(Double onShoreWind) {
        this.onShoreWind = onShoreWind;
    }
    private Double offShoreWind;

    /**
     * @return offShoreWind
     */
    public Double getOffShoreWind() {
        return offShoreWind;
    }

    /**
     * @param offShoreWind to set
     */
    public void setOffShoreWind(Double offShoreWind) {
        this.offShoreWind = offShoreWind;
    }
    private Double solarPower;

    /**
     * @return solarPower
     */
    public Double getSolarPower() {
        return solarPower;
    }

    /**
     * @param solarPower to set
     */
    public void setSolarPower(Double solarPower) {
        this.solarPower = solarPower;
    }
    private Double centralPowerPlant;

    /**
     * @return centralPowerPlant
     */
    public Double getCentralPowerPlant() {
        return centralPowerPlant;
    }

    /**
     * @param centralPowerPlant to set
     */
    public void setCentralPowerPlant(Double centralPowerPlant) {
        this.centralPowerPlant = centralPowerPlant;
    }
    private Double decentralPowerPlant;

    /**
     * @return decentralPowerPlant
     */
    public Double getDecentralPowerPlant() {
        return decentralPowerPlant;
    }

    /**
     * @param decentralPowerPlant to set
     */
    public void setDecentralPowerPlant(Double decentralPowerPlant) {
        this.decentralPowerPlant = decentralPowerPlant;
    }
}
