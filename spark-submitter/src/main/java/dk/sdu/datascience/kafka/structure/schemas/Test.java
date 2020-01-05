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
public class Test {
    private List<ProcessedEmissionsSchema> test;

    /**
    * @return test
    */
    public List<ProcessedEmissionsSchema> getTest() {
        return test;
    }

    /**
    * @param test to set
    */
    public void setTest(List<ProcessedEmissionsSchema> test) {
        this.test = test;
    }
}
