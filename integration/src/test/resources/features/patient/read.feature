Feature: Read patient
    This feature allows users to obtain patient biographical information

Background:
  Given the system has the following patients:
    | id    | first     | last          | gender    |
    | 1     | harry     | potter        | male      |
    | 2     | hermione  | granger       | female    |
    | 3     | ron       | weasley       | male      |

Scenario: Read patient information by identifier (positive)
     When the user searches for patients by id for patient "hermione"
     Then the user should receive a list of 1 patients
     And the user should receive a patient named hermione

 Scenario: Read patient information by identifier (negative)
     When the user searches for patients by id for patient "donald duck"
     Then the user should receive a list of 0 patients
