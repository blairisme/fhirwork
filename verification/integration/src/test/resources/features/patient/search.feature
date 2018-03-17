Feature: Patient Search
    This feature allows users to search for patients by patient attributes.

Background:
  Given the system has the following patients:
    | id        | domain    | first     | last      | gender    | birthday              |
    | 1         | SSN       | harry     | potter    | male      | 1980-01-01T00:00:00Z  |
    | 2         | SSN       | hermione  | granger   | female    | 1980-01-01T00:00:00Z  |
    | 3         | SSN       | ron       | weasley   | male      | 1980-01-01T00:00:00Z  |
    | 4         | SSN       | james     | potter    | male      | 1980-01-01T00:00:00Z  |
    | 5         | SSN       | lily      | potter    | female    | 1980-01-01T00:00:00Z  |

Scenario: Search for patients without parameters
   When the user searches for patients
   Then the user should receive a list of 5 patients
   And the user should receive a patient named james

#Scenario: Search for patients by identifier (positive)
#    When the user searches for patients with identifier "2" and namespace "SSN"
#    Then the user should receive a list of 1 patients
#    And the user should receive a patient named hermione

Scenario: Search for patients by identifier (negative)
    When the user searches for patients with identifier "999" and namespace "SSN"
    Then the user should receive a list of 0 patients

Scenario: Search for patients by single attribute (positive)
    When the user searches for patients with male gender
    Then the user should receive a list of 3 patients
    And the user should receive a patient named ron

Scenario: Search for patients by single attribute (negative)
    When the user searches for patients with last name "maelstrom"
    Then the user should receive a list of 0 patients

Scenario: Search for patients by multiple attributes (positive)
    When the user searches for patients with male gender and last name "potter"
    Then the user should receive a list of 2 patients
    And the user should receive a patient named harry

Scenario: Search for patients by multiple attributes (negative)
    When the user searches for patients with male gender and last name "granger"
    Then the user should receive a list of 0 patients
