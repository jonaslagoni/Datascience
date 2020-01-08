


   
socket.on('Emissions', (ArrayOfEmissions) => {
//console.log("Recieved Emissions: " + JSON.stringify(ArrayOfEmissions));

redrawEmissions(ArrayOfEmissions);

});

function redrawEmissions(array){
    
   var DK1 = []
   var DK2 = []
   var TotalArray = []
   var CollectedArray = []
    
    TotalArray = splitArray(array);
    DK1 = TotalArray[0];
    DK2 = TotalArray[1];
    CollectedArray = DK1.concat(DK2);

    setMinutesOnTimeStamps(DK1);
    setMinutesOnTimeStamps(DK2);

    var margin = {top: 20, right: 20, bottom: 40, left: 50},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;


    var x = d3.scaleTime().range([0, width]);
    var y = d3.scaleLinear().range([height, 0]);

   

    var svg = d3.select("body").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform",
              "translate(" + margin.left + "," + margin.top + ")");


  var formattedDate = d3.timeParse("%Y-%m-%d %H:00");

    
    DK1.forEach(function(d) {
        d.HOUR_DK = formattedDate(d.HOUR_DK);
        d.ACTUAL_EMISSIONS = +d.ACTUAL_EMISSIONS;
    });

    DK2.forEach(function(d){
        d.HOUR_DK = formattedDate(d.HOUR_DK);
        d.ACTUAL_EMISSIONS = +d.ACTUAL_EMISSIONS;
    });

    DK1.sort((a,b) => b.HOUR_DK - a.HOUR_DK);
    DK2.sort((a,b) => b.HOUR_DK - a.HOUR_DK);

    //Create Lines for the graph
    var dk1Line = d3.line()
    .x(function(d) {return x(d.HOUR_DK); })
    .y(function(d) {return y(d.ACTUAL_EMISSIONS) })
    .curve(d3.curveMonotoneX);

    var dk2Line = d3.line()
    .x(function(d) {return x(d.HOUR_DK); })
    .y(function(d) {return y(d.ACTUAL_EMISSIONS); })
    .curve(d3.curveMonotoneX);
    console.log(dk1Line);



    // Get max value for Y-Axis
    var maxY;
    var tempArray = [];
    CollectedArray.forEach(function(d){
        tempArray.push(+d.ACTUAL_EMISSIONS);
    });
    maxY = Math.max.apply(Math, tempArray);

    


    x.domain(d3.extent(CollectedArray, function(d) { return d.HOUR_DK; }));
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
      .text("C02 Emission(t)");      

      svg.append("circle").attr("cx",200).attr("cy",130).attr("r", 6).style("fill", "blue")
      svg.append("circle").attr("cx",200).attr("cy",160).attr("r", 6).style("fill", "red")
      svg.append("text").attr("x", 220).attr("y", 130).text("Price Area: DK1").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("text").attr("x", 220).attr("y", 160).text("Price Area: DK2").style("font-size", "15px").attr("alignment-baseline","middle")



   //   console.log(DK1);
   //   console.log(DK2);
}

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

function setMinutesOnTimeStamps(array){
    array.forEach(function(entry){
        entry.HOUR_DK = entry.HOUR_DK + ":00";
    })
}


    






