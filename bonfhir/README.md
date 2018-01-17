# BonFHIR

BonFHIR is a small web app used to test FHIRWork's implementation

## Dependencies
1. [Node Package Manager and Nodejs](https://www.npmjs.com/get-npm)

## Installation
Navigate to the _bonfhir_ directory
1. npm install
2. npm start

To run in the background as a daemon:
forever start -c nodemon index.js

## Usage
- The port used for the application is 3000
- To view a list of the patients in the FHIR database http://localhost:3000/patients
