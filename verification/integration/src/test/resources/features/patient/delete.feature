Feature: Delete patient
    This feature allows users to delete patient biographical information

Background:
  Given the system has the following patients:
    | id        | domain    | first     | last      | gender    | birthday              |
    | 1         | SSN       | harry     | potter    | male      | 1980-01-01T00:00:00Z  |
    | 2         | SSN       | hermione  | granger   | female    | 1980-01-01T00:00:00Z  |
    | 3         | SSN       | ron       | weasley   | male      | 1980-01-01T00:00:00Z  |

#Scenario: Delete patient by id
#     When the user deletes the patient named "hermione" using their id
#     Then the system should contain 2 patients
#     And the system should contain a patient named "harry"
#     And the system should contain a patient named "ron"

#Scenario: Delete patient by name
#     When the user deletes the patient named "hermione"
#     Then the system should contain 2 patients
#     And the system should contain a patient named "harry"
#     And the system should contain a patient named "ron"