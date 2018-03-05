Feature: Update patient
    This feature allows users to update patient biographical information

Background:
  Given the system has the following patients:
    | id        | domain    | first     | last      | gender    | birthday              |
    | 1         | SSN       | harry     | potter    | male      | 1980-01-01T00:00:00Z  |
    | 2         | SSN       | hermione  | granger   | female    | 1980-01-01T00:00:00Z  |
    | 3         | SSN       | ron       | weasley   | male      | 1980-01-01T00:00:00Z  |

#Scenario: Update patient information
#    When the user updates a patient to the following data:
#        | id    | first     | last          | gender    |
#        | 1     | harry     | potter        | female    |
#    And the user searches for patients with female gender and last name "potter"
#    Then the user should receive a list of 1 patients