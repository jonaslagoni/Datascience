
const eventEnum = Object.freeze({
			"STARTING": "STARTING",
			"DONE": "DONE",
			"STOPPED": "STOPPED",
    })
module.exports.eventEnum = eventEnum;
module.exports = class Status {
    constructor(){

    }
    
    /**
    *
    * @param { string } event
    */
    setData(
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
