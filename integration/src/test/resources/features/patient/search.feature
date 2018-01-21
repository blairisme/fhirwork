Feature: Patient Search
    This feature allows users to search for patients by patient attributes.

Background:
  Given the system has the following patients:
    | id    | first     | last          | gender    |
    | 1     | harry     | potter        | male      |
    | 2     | hermione  | granger       | female    |
    | 3     | ron       | weasley       | male      |
    | 4     | james     | potter        | male      |
    | 5     | lily      | potter        | female    |

Scenario: Search for patients without parameters
   When the user searches for patients
   Then the user should receive a list of 5 patients
   And the user should receive a patient named james

Scenario: Search for patients by identifier (positive)
    When the user searches for patients with id "SSN|2"
    Then the user should receive a list of 1 patients
    And the user should receive a patient named hermione

Scenario: Search for patients by identifier (negative)
    When the user searches for patients with id "SSN|999"
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
