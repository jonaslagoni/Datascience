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
public class Status {
	public enum eventEnum {
		@SerializedName("STARTING")
			STARTING,
		@SerializedName("DONE")
			DONE,
		@SerializedName("STOPPED")
			STOPPED,
	}
	
    private eventEnum event;

    /**
     * @return eventEnum
     */
    public eventEnum getEvent() {
        return event;
    }

    /**
     * @param event to set
     */
    public void setEvent(eventEnum event) {
        this.event = event;
    }
}
