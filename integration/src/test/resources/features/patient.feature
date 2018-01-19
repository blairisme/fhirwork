Feature: Patient API
    Tests features related to the patient API

Background:
  Given the system has the following patients:
    | id    | first     | last          |
    | 1     | frodo     | baggins       |
    | 2     | samwise   | gamgee        |
    | 3     | meriadoc  | brandybuck    |
    | 4     | peregrin  | took          |

Scenario: Search patients without parameters
   When I search patients
   Then The server response has status code 200
   And the response is a bundle that contains the patients created
