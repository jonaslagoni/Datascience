
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
public class AllEnerginetCO2EmissionSchema {
    
    private List<EnerginetCO2EmissionSchema> allEnerginetCO2EmissionSchema;

    /**
    * @return allEnerginetCO2EmissionSchema
    */
    public List<EnerginetCO2EmissionSchema> getAllEnerginetCO2EmissionSchema() {
        return allEnerginetCO2EmissionSchema;
    }

    /**
    * @param allEnerginetCO2EmissionSchema to set
    */
    public void setAllEnerginetCO2EmissionSchema(List<EnerginetCO2EmissionSchema> allEnerginetCO2EmissionSchema) {
        this.allEnerginetCO2EmissionSchema = allEnerginetCO2EmissionSchema;
    }

    public static class EnerginetCO2EmissionSchema{
        
        
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

}
