
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
public class EnerginetProductionAndExchangeSchema {
    
        
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

        
    private Double PRODUCTION_LT_100;

    /**
    * @return PRODUCTION_LT_100
    */
    public Double getPRODUCTION_LT_100() {
        return PRODUCTION_LT_100;
    }

    /**
    * @param PRODUCTION_LT_100 to set
    */
    public void setPRODUCTION_LT_100(Double PRODUCTION_LT_100) {
        this.PRODUCTION_LT_100 = PRODUCTION_LT_100;
    }

        
    private Double PRODUCTION_GE_100;

    /**
    * @return PRODUCTION_GE_100
    */
    public Double getPRODUCTION_GE_100() {
        return PRODUCTION_GE_100;
    }

    /**
    * @param PRODUCTION_GE_100 to set
    */
    public void setPRODUCTION_GE_100(Double PRODUCTION_GE_100) {
        this.PRODUCTION_GE_100 = PRODUCTION_GE_100;
    }


}
