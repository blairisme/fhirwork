/*
 * FHIRWork (c)
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 *
 * Author: Alperen Karaoglu
 * Author: Blair Butterworth
 */

const express = require('express');
const router = express.Router();
const rest = require('restler')
const config = require('../config.json');
const http = require('http');
const request = require('request');
var url = `${config.fhir.address}/Patient?_format=json`;
router.get('/patients', function (req, res) {

  console.log(url);
  rest.get(url).on('complete', function(result) {
    if (result instanceof Error) {
      console.log('Error:', result.message);
    }
    else {
      //console.log(JSON.parse(result));
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
          //console.log(patient);
          return patient;
        });
    	}
    	else {
      	var patients = [];
    	};

    	// res.send(patients)

      console.log(patients)
    	res.render('patients', {patients: patients, chartAddress: config.growthchart.address, fhirAddress: config.fhir.address})
    }
  });
});

router.get('/patients/addpatient', function(req,res){
  res.render('addpatient');
});

router.post('/patients/addpatient', function(req,res){

  console.log(url);
  console.log(req.body);
  console.log(req.body.id);
  var data = {
  "resourceType": "Patient",
  "name": [
    {
      "use": "usual",
      "given": [
        req.body.given
      ],
      "family":[ req.body.family],
      "prefix": [
        "Miss"
      ]
    },
    {
      "use": " ",
      "family": [" "]
    }
  ],
  "identifier": [
    {
      "system": "uk.nhs.nhs_number",
      "value": req.body.nhs
    }
  ],
  "gender": req.body.gender,
  "address": [
    {
      "text": req.body.address,
      "line": [
        " ",
        " "
      ],
      "city": " ",
      "state": " ",
      "postalCode": " ",
      "country": " "
    }
  ],
};
const options = {
method: 'POST',
headers: {
  'Content-Type': 'application/json',
  'Content-Length': Buffer.byteLength(JSON.stringify(data))
}
};
console.log(data);
rest.postJson("http://localhost:8090/fhir/Patient", data, options).on('complete', function(result){

    console.log("?do we get here");
    console.log(result);
    res.redirect('/patients')
  })

});

module.exports = router;
