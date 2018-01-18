Feature: Observation API 
   Server has observation search api

Background: Setup environment
   Given fixture "observationBundle.json" has loaded as "observations"
   Given fixture "patient.json" has loaded as "patient"
   Given a patient was created using "patient" fixture, with family name "Evanthia", given name "Tingiri", gender "female", birthDate "1994-04-06", and NHS identifier "TEST-12345"
   Given an EHRid exists for the subjec id "TEST-12345"
   And the following compositions are created with the same ehrid:
   |         date            | height | weight | bmi  | head_circumference |
   | 1957-02-01T02:20:00Z    | 78.5   | 9.8    | 17.2 |          50        |
   | 1957-03-01T02:20:00Z    | 82.3   | 10.9   | nil  |          51        |
   | 1957-12-01T02:20:00Z    | 86.3   | 11.9   | 16   |          52        |
   | 1958-02-02T18:00:00Z    | 90     | 15     | 18.5 |          54        |

Scenario: Search Height, Weight, BMI and Head Circumference Observations 
   When I search Height, Weight, BMI, Head Circumference observation with the created patient id
   Then The server response has status code 200
   And the response is a bundle that contains the Observations created

Scenario: Delete all compositions for the patient
   Then all compositions created are deleted