function triggerRedraw(ArrayOfProducedElectricity){

}


socket.on('ProducedElectricity', (ArrayOfProducedElectricity) => {
    console.log("Recieved ProducedElectricity: " + JSON.stringify(ArrayOfProducedElectricity));

    triggerRedraw(ArrayOfProducedElectricity)

});
