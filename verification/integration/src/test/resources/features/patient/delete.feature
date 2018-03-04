Feature: Delete patient
    This feature allows users to delete patient biographical information

Background:
  Given the system has the following patients:
    | id    | first     | last          | gender    |
    | 1     | harry     | potter        | male      |
    | 2     | hermione  | granger       | female    |
    | 3     | ron       | weasley       | male      |

Scenario: Delete patient by id
     When the user deletes the patient named "hermione" using their id
     Then the system should contain 2 patients
     And the system should contain a patient named "harry"
     And the system should contain a patient named "ron"

#Scenario: Delete patient by name
#     When the user deletes the patient named "hermione"
#     Then the system should contain 2 patients
#     And the system should contain a patient named "harry"
#     And the system should contain a patient named "ron"