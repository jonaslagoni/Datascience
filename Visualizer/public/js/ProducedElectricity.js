function triggerRedraw(ArrayOfProducedElectricity){

}


socket.on('ProducedElectricity', (ArrayOfProducedElectricity) => {
    console.log("Recieved ProducedElectricity: " + JSON.stringify(ArrayOfProducedElectricity));

    redrawProducedElectricity(ArrayOfProducedElectricity)



});


function redrawProducedElectricity(ArrayOfProducedElectricity){
    var splitArray = splitArray(ArrayOfProducedElectricity);

    var DK1 = splitArray[0];
    var DK2 = splitArray[1];

    CollectedArray = DK1.concat(DK2);

    var margin = {top: 20, right: 20, bottom: 40, left: 50},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

    var x = d3.scaleTime().range([0, width]);
    var y = d3.scaleTime().range([height, 0]);

    d3.select("#ProducedElectricity").selectAll("svg").remove();
    var svg = d3
    .select('#ProducedElectricity')
    .append('svg')
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform",
          "translate(" + margin.left + "," + margin.top + ")");



	var formattedDate = d3.utcParse("%Y-%m-%dT%H:%M:%S");


    DK1.forEach(function(d) {
        console.log(d.DAY_DATE_DK);
        d.DAY_DATE_DK = formattedDate(d.DAY_DATE_DK);
        d.TOTAL_MWH_PRODUCED = +d.TOTAL_MWH_PRODUCED;
    });

    DK2.forEach(function(d){
        console.log(d.DAY_DATE_DK);
        d.DAY_DATE_DK = formattedDate(d.DAY_DATE_DK);
        d.TOTAL_MWH_PRODUCED = +d.TOTAL_MWH_PRODUCED;
    });

    

    DK1.sort((a,b) => b.DAY_DATE_DK - a.DAY_DATE_DK);
    DK2.sort((a,b) => b.DAY_DATE_DK - a.DAY_DATE_DK);

 
    //Create Lines for the graph
    var dk1Line = d3.line()
    .x(function(d) {return x(d.DAY_DATE_DK); })
    .y(function(d) {return y(d.TOTAL_MWH_PRODUCED) })
    .curve(d3.curveMonotoneX);

    var dk2Line = d3.line()
    .x(function(d) {return x(d.DAY_DATE_DK); })
    .y(function(d) {return y(d.TOTAL_MWH_PRODUCED); })
    .curve(d3.curveMonotoneX);

    // Get max value for Y-Axis
    var maxY;
    var tempArray = [];
    CollectedArray.forEach(function(d){
        tempArray.push(+d.TOTAL_MWH_PRODUCED);
    });
    maxY = Math.max.apply(Math, tempArray);
    


    //Set range for X axis and Y axis.
    x.domain(d3.extent(CollectedArray, function(d) { return d.DAY_DATE_DK; }));
    y.domain([0,maxY]);

    svg.append("path")
      .attr("d", dk1Line(DK1))
      .attr("class", "line")
      .style("stroke", "blue")
      .style("fill", "none");
        

    svg.append("path")
      .attr("d", dk2Line(DK2))
      .attr("class", "line")
      .style("stroke", "red")
      .style("fill", "none");
      

      svg.append("g")
      .attr("transform", "translate(0," + height + ")")
      .call(d3.axisBottom(x));

      svg.append("text")             
      .attr("transform", "translate(" + (width/2) + " ," + 
                           (height + margin.top + 15) + ")")
      .style("text-anchor", "middle")
      .text("Date");
 
      svg.append("g")
      .call(d3.axisLeft(y));

      svg.append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 0 - margin.left)
      .attr("x",0 - (height / 2))
      .attr("dy", "1em")
      .style("text-anchor", "middle")
      .text("Energy Produced (MWh)");      

      svg.append("circle").attr("cx",200).attr("cy",130).attr("r", 6).style("fill", "blue")
      svg.append("circle").attr("cx",200).attr("cy",160).attr("r", 6).style("fill", "red")
      svg.append("text").attr("x", 220).attr("y", 130).text("Price Area: DK1").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("text").attr("x", 220).attr("y", 160).text("Price Area: DK2").style("font-size", "15px").attr("alignment-baseline","middle")



    function splitArray(array){
        DK1 = []
        DK2 = []
    
        array.forEach(function(entry){
            if(entry.PRICE_AREA == "DK1"){
                DK1.push(entry);
            } else{
                DK2.push(entry);
            }
        });

        return [DK1, DK2]
    }





}