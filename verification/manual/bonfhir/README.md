# BonFHIR

BonFHIR is a small web app used to test FHIRWork's implementation

## Dependencies
1. [Node Package Manager and Nodejs](https://www.npmjs.com/get-npm)

## Installation
Navigate to the _bonfhir_ directory
1. npm install
2. npm start

To run in the background as a daemon:
1. forever start -c nodemon app.js

## Usage

# Warning: This is prototype and the current fhir server queried is hardcoded

- In the config.json file set the ports for the EMPI, EHR and FHIR servers
- The port used for this application is 3000 and it runs on local host 127.0.0.1
- To view a list of the patients in the FHIR database http://localhost:3000/patients
