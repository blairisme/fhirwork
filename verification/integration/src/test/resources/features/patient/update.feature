Feature: Update patient
    This feature allows users to update patient biographical information

Background:
  Given the system has the following patients:
    | id    | first     | last          | gender    |
    | 1     | harry     | potter        | male      |
    | 2     | hermione  | granger       | female    |
    | 3     | ron       | weasley       | male      |

Scenario: Update patient information
    When the user updates a patient to the following data:
        | id    | first     | last          | gender    |
        | 1     | harry     | potter        | female    |
    And the user searches for patients with female gender and last name "potter"
    Then the user should receive a list of 1 patients