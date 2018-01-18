Feature: Patient API 
   Server has patient create api
   Server has patient search api
   Server has patient read api
   Server has patient update api
   Server has patient patch api
   Server has patient delete api

Background:
   Given fixture "patient.json" has loaded as "patient"
   Given a patient was created using "patient" fixture, with family name "Foo", given name "Bar", gender "female", birthDate "1987-12-01", and NHS identifier "0123456789"
   Given a patient was created using "patient" fixture, with family name "Foo", given name "Yuan", gender "male", birthDate "2010-06-30", and NHS identifier "9876543210"

Scenario: Create a new patient
   When I create a patient using "patient" fixture, with family name "Sinha" and given name "Evanthia"
   Then The server response has status code 201
   And The server response has the patient id in the location header
   And The server has a patient stored with this id, family name "Sinha", and given name "Evanthia" 

Scenario: Read a patient
   When I read the first patient created
   Then The server response has status code 200
   And The server response has a body with the first patient created

Scenario: Search patients without parameters
   When I search patients
   Then The server response has status code 200
   And the response is a bundle that contains the patients created

Scenario: Search patients by gender
   When I search patients with gender "female"
   Then The server response has status code 200
   And the response is a bundle with patients that have gender "female"
   And the response is a bundle that contains the first patient created

Scenario: Search patients by birthDate
   When I search patients with birthdate "1987-12-01"
   Then The server response has status code 200
   And the response is a bundle with patients that have birthDate "1987-12-01"
   And the response is a bundle that contains the first patient created

Scenario: Search patients by NHS identifier
   When I search patients with NHS identifier "0123456789"
   Then The server response has status code 200
   And the response is a bundle with patients that have NHS identifier "0123456789" 
   And the response is a bundle that contains the first patient created

Scenario: Search patients by family name and given name
   When I search patients with family name "Foo" and given name "Bar"
   Then The server response has status code 200
   And the response is a bundle with patients that have family name "Foo" and give name "Bar"
   And the response is a bundle that contains the first patient created

Scenario: Update a patient
   When I update the first patient using "patient" fixture, with family name "Lettier" and given name "Jens"
   Then The server response has status code 200
   And The first patient stored in the server has been modified

Scenario: Patch a patient
   When I patch the first patient stored to delete marital status, replace given name for "Saheed" and add country "UK" to address
   Then The server response has status code 200
   And The first patient stored in the server has been modified

Scenario: Delete a patient
   When I delete the first patient created
   Then The server response has status code 204
   And The server has not stored the first patient created

