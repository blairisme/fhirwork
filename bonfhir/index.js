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

const rest = require('restler')

const ehrPort = config.etherciseEHRPort;
const openEMPIPort = config.openEMPIPort;
const fhirPort = config.fhirPort
const serverAddress = config.serverAddress;

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

app.get('/patients', function (req, res) {
  var url = `http://fhirtest.uhn.ca/baseDstu2/Patient?_format=json`;
  console.log(url);
  rest.get(url).on('complete', function(result) {
    if (result instanceof Error) {
      console.log('Error:', result.message);
    }
    else {
      // console.log(JSON.parse(result));
      jsonResponse = JSON.parse(result);
      if(jsonResponse.hasOwnProperty('entry')) {
        var patients = JSON.parse(result)['entry'].map(function(item, index) {
          var resource = item['resource'];
          return resource;
        });

        patients = patients.map(function(item, index) {
          var patient = new Object();
          patient.id = item.id
          if(item.birthDate === undefined) {
            patient.birthDate = '';
          }
          else {
            patient.birthDate = item.birthDate;
          }

          if(item.name === undefined) {
            patient.given = '';
            patient.family = '';
          }
          else {
            if(item.name[0].given !== undefined) {
              patient.given = item.name[0].given[0]
            }
            if(item.name[0].family !== undefined) {
              patient.family = item.name[0].family[0];
            }
          }
          console.log(patient);
          return patient;
        });
    }
    else {
      var patients = [];
    };
    // res.send(patients)
    res.render('patients', { patients: patients})
    }
  });
})

app.get('observations', function(req, res){
	
})

//listen on port 3000
app.listen(3001);
