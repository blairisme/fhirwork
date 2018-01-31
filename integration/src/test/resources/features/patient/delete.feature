Feature: Delete patient
    This feature allows users to delete patient biographical information

Background:
  Given the system has the following patients:
    | id    | first     | last          | gender    |
    | 1     | harry     | potter        | male      |
    | 2     | hermione  | granger       | female    |
    | 3     | ron       | weasley       | male      |

Scenario: Delete patient information
     When the user deletes the patient named "harry"
     Then the system should contain 2 patients
     And the system should contain a patient named "hermione"
     And the system should contain a patient named "ron"
