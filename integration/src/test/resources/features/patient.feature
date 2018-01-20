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
   When the user searches for patients
   Then the user should receive a list of 4 patients
   And the user should receive a patient named samwise
