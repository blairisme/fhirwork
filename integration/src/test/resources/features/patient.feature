Feature: Patient API
    Tests features related to the patient API

Scenario: Search patients without parameters
   When I search patients
   Then The server response has status code 200
   And the response is a bundle that contains the patients created
