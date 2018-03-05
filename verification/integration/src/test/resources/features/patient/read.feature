Feature: Read patient
    This feature allows users to obtain patient biographical information

Background:
  Given the system has the following patients:
    | id        | domain    | first     | last      | gender    | birthday              |
    | 1         | SSN       | harry     | potter    | male      | 1980-01-01T00:00:00Z  |
    | 2         | SSN       | hermione  | granger   | female    | 1980-01-01T00:00:00Z  |
    | 3         | SSN       | ron       | weasley   | male      | 1980-01-01T00:00:00Z  |

Scenario: Read patient by internal identifier (positive)
     When the user searches for patients by id for patient "hermione"
     Then the user should receive a list of 1 patients
     And the user should receive a patient named hermione

Scenario: Read patient by internal identifier (negative)
     When the user searches for patients by id "123"
     Then the user should receive a list of 0 patients
