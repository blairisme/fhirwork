const express = require('express');
const router = express.Router();
const rest = require('restler')
const config = require('../config.json');

router.get(/observations/, function(req, res){
  console.log('req url', req.url);
  console.log('parse', req.url.slice(15));
  //console.log(req);
  //console.log(res);
  let patientID = req.url.slice(15);
	var url = `${config.fhir.address}/Observation?subject=Patient/${patientID}&_format=json`
	console.log(url)
	rest.get(url).on('complete', function(result) {
		if (result instanceof Error) {
			console.log('Error:', result.message);
		}
		else {
			//console.log(JSON.parse(result));
			jsonResponse = JSON.parse(result);
      if(jsonResponse.hasOwnProperty('entry')) {
        var observations = JSON.parse(result)['entry'].map(function(item, index){
          var resource = item['resource'];
          return resource;
        });

        //console.log(observations);
        observations = observations.map(function(item, index){
          var observation = new Object();

          observation.loinc = '';
          observation.type  = '';
          observation.value  = '';
          observation.unit  = '';
          //Retrieving a loinc code for an observation
          if(item.code.coding[0].code !== undefined){
            observation.loinc = item.code.coding[0].code;
          }
          //Retrieving the type of an observation
          if(item.code.coding[0].display !== undefined){
            observation.type = item.code.coding[0].display;
          }
          if(item.valueQuantity !== undefined){
            //Retrieving the value of an observation
            if(item.valueQuantity.value !== undefined){
              observation.value = item.valueQuantity.value;
            }
            //Retrieving the unit of an observation
            if(item.valueQuantity.unit !== undefined){
              observation.unit = item.valueQuantity.unit;
            }
          }

          console.log(observation.loinc);
          console.log(observation.type);
          console.log(observation.value);
          console.log(observation.unit);
          return observation;
        });
      }else{
        var observations = [];
      }


			res.render('observations', {observations: observations})
		}
	});
});

module.exports = router;
