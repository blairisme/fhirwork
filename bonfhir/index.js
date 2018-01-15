//Set up express, app and router
const express = require('express');
const app = express();
//const router = express.Router();

//Set up body parser to access data from post
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({extended: false}));

//Set up for static file
const path = require('path');
app.use(express.static(path.join(__dirname, "public")));

//Set up for session management
const session = require('express-session');

//Setting up the configuration file
const config = require('./config.json');

const ehrPort = config.etherciseEHRPort;
const openEMPIPort = config.openEMPIPort;
const fhirPort = config.fhirPort

//Setting up the options for session management
const sessionOptions = {
	secret: 'secret cookie thang',
	resave: true,
	saveUninitialized: true
};

//activating session management
app.use(session(sessionOptions));

//hbs setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

//Get for the main page, renders the template for the main page
app.get('/', function(req, res){
    res.render('mainTemplate');
});

//listen on port 3000
app.listen(3000);
