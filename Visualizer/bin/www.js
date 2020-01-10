#!/usr/bin/env node

/**
 * Module dependencies.
 */

var app = require('../app');
var debug = require('debug')('visualizer:server');
var http = require('http');

/**
 * Get port from environment and store in Express.
 */

var port = normalizePort(process.env.PORT || '9090');
app.set('port', port);

/**
 * Create HTTP server.
 */

var server = http.createServer(app);
var io = require('socket.io')(server);
var kafka = require('../kafka/Index');
io.on('connection', function(socket){
	console.log('a user connected');
	let tempProducedElectricity = [
		{
			DAY_DATE_DK: "2020-01-17",
			PRICE_AREA: "DK1",
			TOTAL_MWH_PRODUCED: "123"
		},
		{
			DAY_DATE_DK: "2020-01-16",
			PRICE_AREA: "DK1",
			TOTAL_MWH_PRODUCED: "312"
		},
		
		{
			DAY_DATE_DK: "2020-01-15",
			PRICE_AREA: "DK1",
			TOTAL_MWH_PRODUCED: "765"
		},
		
		{
			DAY_DATE_DK: "2020-01-14",
			PRICE_AREA: "DK1",
			TOTAL_MWH_PRODUCED: "952"
		},
		
		{
			DAY_DATE_DK: "2020-01-13",
			PRICE_AREA: "DK1",
			TOTAL_MWH_PRODUCED: "543"
		},
		
		{
			DAY_DATE_DK: "2020-01-17",
			PRICE_AREA: "DK2",
			TOTAL_MWH_PRODUCED: "345"
		},
		
		{
			DAY_DATE_DK: "2020-01-16",
			PRICE_AREA: "DK2",
			TOTAL_MWH_PRODUCED: "678"
		},

		{
			DAY_DATE_DK: "2020-01-15",
			PRICE_AREA: "DK2",
			TOTAL_MWH_PRODUCED: "765"
		},
		{
			DAY_DATE_DK: "2020-01-14",
			PRICE_AREA: "DK2",
			TOTAL_MWH_PRODUCED: "1209"
		}
	]
	socket.emit('ProducedElectricity', tempProducedElectricity);

	
	let tempSpotPrices = [
		{
			HOUR_DK: "2020-01",
			PRICE_AREA: "DK2",
			SPOT_PRICE_EUR: "24"
		},
		{
			HOUR_DK: "2020-01",
			PRICE_AREA: "DK1",
			SPOT_PRICE_EUR: "23"
		},
		{
			HOUR_DK: "2020-02",
			PRICE_AREA: "DK2",
			SPOT_PRICE_EUR: "30"
		},
		{
			HOUR_DK: "2020-02",
			PRICE_AREA: "DK1",
			SPOT_PRICE_EUR: "35"
		},
		{
			HOUR_DK: "2020-03",
			PRICE_AREA: "DK2",
			SPOT_PRICE_EUR: "32"
		},
		{
			HOUR_DK: "2020-03",
			PRICE_AREA: "DK1",
			SPOT_PRICE_EUR: "24"
		},
		{
			HOUR_DK: "2020-04",
			PRICE_AREA: "DK2",
			SPOT_PRICE_EUR: "18"
		},
		{
			HOUR_DK: "2020-04",
			PRICE_AREA: "DK1",
			SPOT_PRICE_EUR: "28"
		},
		{
			HOUR_DK: "2020-05",
			PRICE_AREA: "DK2",
			SPOT_PRICE_EUR: "28"
		},
		{
			HOUR_DK: "2020-05",
			PRICE_AREA: "DK1",
			SPOT_PRICE_EUR: "28"
		},
		{
			HOUR_DK: "2020-06",
			PRICE_AREA: "DK2",
			SPOT_PRICE_EUR: "28"
		},
		{
			HOUR_DK: "2020-06",
			PRICE_AREA: "DK1",
			SPOT_PRICE_EUR: "28"
		}
	
	]
	socket.emit('SpotPrices', tempSpotPrices);
	
	let tempEmissions = [
	
			{
				HOUR_DK: "2020-01-07 15",
				PRICE_AREA: "DK1",
				ACTUAL_EMISSIONS: "221"
			},
			
			 {
				HOUR_DK: "2020-01-07 14",
				PRICE_AREA: "DK1",
				ACTUAL_EMISSIONS: "224"
			},
			
			{
				HOUR_DK: "2020-01-06 22",
				PRICE_AREA: "DK1",
				ACTUAL_EMISSIONS: "467"
			},
			
			{
				HOUR_DK: "2020-01-06 21",
				PRICE_AREA: "DK1",
				ACTUAL_EMISSIONS: "354"
			},
			
			{
				HOUR_DK: "2020-01-06 15",
				PRICE_AREA: "DK2",
				ACTUAL_EMISSIONS: "543"
			},
			
			{
				HOUR_DK: "2020-01-06 14",
				PRICE_AREA: "DK2",
				ACTUAL_EMISSIONS: "123"
			},

			{
				HOUR_DK: "2020-01-06 22",
				PRICE_AREA: "DK2",
				ACTUAL_EMISSIONS: "765"
			},
			
			{
				HOUR_DK: "2020-01-06 21",
				PRICE_AREA: "DK2",
				ACTUAL_EMISSIONS: "284"
			}
	]
	socket.emit('Emissions', tempEmissions);
});
kafka
	.consumeProcessedProduced(
		message => {
			console.log('Recieved consumeProcessedProduced: ' + JSON.stringify(message))
			io.emit('ProducedElectricity', message);
		},
		e => {
			console.log(e);
		}
	)
	.catch(() => {
		console.log('error');
	});
kafka
	.consumeProcessedSpotPrices(
		message => {
			console.log('Recieved consumeProcessedSpotPrices: ' + JSON.stringify(message))
			io.emit('SpotPrices', message);
		},
		e => {
			console.log(e);
		}
	)
	.catch(() => {
		console.log('error');
	});
kafka
	.consumeProcessedEmissions(
		message => {
			console.log('Recieved consumeProcessedEmissions: ' + JSON.stringify(message))
			io.emit('Emissions', message);
		},
		e => {
			console.log(e);
		}
	)
	.catch(() => {
		console.log('error');
	});
/**
 * Listen on provided port, on all network interfaces.
 */

server.listen(port);
server.on('error', onError);
server.on('listening', onListening);

/**
 * Normalize a port into a number, string, or false.
 */

function normalizePort(val) {
	var port = parseInt(val, 10);

	if (isNaN(port)) {
		// named pipe
		return val;
	}

	if (port >= 0) {
		// port number
		return port;
	}

	return false;
}

/**
 * Event listener for HTTP server "error" event.
 */

function onError(error) {
	if (error.syscall !== 'listen') {
		throw error;
	}

	var bind = typeof port === 'string' ? 'Pipe ' + port : 'Port ' + port;

	// handle specific listen errors with friendly messages
	switch (error.code) {
		case 'EACCES':
			console.error(bind + ' requires elevated privileges');
			process.exit(1);
			break;
		case 'EADDRINUSE':
			console.error(bind + ' is already in use');
			process.exit(1);
			break;
		default:
			throw error;
	}
}

/**
 * Event listener for HTTP server "listening" event.
 */

function onListening() {
	var addr = server.address();
	var bind = typeof addr === 'string' ? 'pipe ' + addr : 'port ' + addr.port;
	debug('Listening on ' + bind);
}
