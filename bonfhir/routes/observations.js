const express = require('express');
const router = express.Router();

const rest = require('restler')

router.get('/observations', function(req, res){
	var url = `http://fhirtest.uhn.ca/baseDstu2/Observation-lastn?patient=Patient/13664_format=json`
	console.log(url)
	rest.get(url).on('complete', function(result) {
		if (result instanceof Error) {
			console.log('Error:', result.message);
		}
		else {
			console.log(JSON.parse(result));
			jsonResponse = JSON.parse(result);
			res.render('observations')
		}
	});

});

module.exports = router;
