
const eventEnum = Object.freeze({
			"STARTING": "STARTING",
			"DONE": "DONE",
			"STOPPED": "STOPPED",
    })
exports.eventEnum = eventEnum;
exports = class Status {
    constructor();
    

    /**
    *
    * @param { string } event
    */
    constructor(
        event
    ){
        this.event=event;
    }


    
    /**
    * Copy a js object into this.
    * @param {*} jsonObject the js object 
    */
    copyInto(jsonObject){
        if(jsonObject.event){
            this.event=jsonObject.event;
        }
    }
}
