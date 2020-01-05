var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var kafka = require('./kafka/Index');
kafka
	.consumeDatascienceProcessedStatus(
		message => {
			console.log(message);
		},
		e => {
			console.log(e);
		}
	)
	.catch(() => {
		console.log('error');
	});
var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

module.exports = app;
