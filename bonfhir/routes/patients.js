/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 *
 * Author: Alperen Karaoglu
 * Author: Blair Butterworth
 */
 
const express = require('express');
const router = express.Router();
const rest = require('restler')
const config = require('../config.json');

router.get('/patients', function (req, res) {
  var url = `${config.fhir.address}/Patient?_format=json`;
  console.log(url);
  rest.get(url).on('complete', function(result) {
    if (result instanceof Error) {
      console.log('Error:', result.message);
    }
    else {
      console.log(JSON.parse(result));
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
      console.log(patients)
    	res.render('patients', { patients: patients})
    }
  });
});

module.exports = router;
