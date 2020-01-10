

socket.on('SpotPrices', (ArrayOfSpotPrices) => {
console.log("Recieved SpotPrices: " + JSON.stringify(ArrayOfSpotPrices));

redrawSpotPrices(ArrayOfSpotPrices);

});

function redrawSpotPrices(ArrayOfSpotPrices){
    
    DK1 = []
    DK2 = []

    totalArray = splitArray(ArrayOfSpotPrices);

    DK1 = totalArray[0];
    DK2 = totalArray[1];


    CollectedArray = DK1.concat(DK2)
 

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

    var formattedDate = d3.timeParse("%Y-%m");

    DK1.forEach(function(d) {
        d.HOUR_DK = formattedDate(d.HOUR_DK);
        d.SPOT_PRICE_EUR = +d.SPOT_PRICE_EUR;
    });

    DK2.forEach(function(d){
        d.HOUR_DK = formattedDate(d.HOUR_DK);
        d.SPOT_PRICE_EUR = +d.SPOT_PRICE_EUR;
    });


    DK1.sort((a,b) => b.HOUR_DK - a.HOUR_DK);
    DK2.sort((a,b) => b.HOUR_DK - a.HOUR_DK);


   //Create Lines for the graph
   var dk1Line = d3.line()
   .x(function(d) {return x(d.HOUR_DK); })
   .y(function(d) {return y(+d.SPOT_PRICE_EUR) })
   .curve(d3.curveMonotoneX);

   var dk2Line = d3.line()
   .x(function(d) {return x(d.HOUR_DK); })
   .y(function(d) {return y(+d.SPOT_PRICE_EUR); })
   .curve(d3.curveMonotoneX);


    // Get max value for Y-Axis
    var maxY;
    var tempArray = [];
    console.log(CollectedArray);
    CollectedArray.forEach(function(d){
        tempArray.push(+d.SPOT_PRICE_EUR);
    });
    maxY = Math.max.apply(Math, tempArray);
    console.log(tempArray);
    console.log("This is maxY: " + maxY);

    console.log(DK1);
    console.log(DK2);
    

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
    .text("Spot prices EUR");      

    svg.append("circle").attr("cx",200).attr("cy",130).attr("r", 6).style("fill", "blue")
    svg.append("circle").attr("cx",200).attr("cy",160).attr("r", 6).style("fill", "red")
    svg.append("text").attr("x", 220).attr("y", 130).text("Price Area: DK1").style("font-size", "15px").attr("alignment-baseline","middle")
    svg.append("text").attr("x", 220).attr("y", 160).text("Price Area: DK2").style("font-size", "15px").attr("alignment-baseline","middle")




    function splitArray(array){
       
        var DK1 = [];
        var DK2 = [];

        array.forEach(function(entry){
            if(entry.PRICE_AREA == "DK1"){
                DK1.push(entry);
            } else {
                DK2.push(entry);
            }
            
        });
        return [DK1, DK2];
       
    }

}
