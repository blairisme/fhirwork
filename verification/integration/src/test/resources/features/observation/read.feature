Feature: Observation Reading
    This feature allows users to get data on observations made about patients.

Background:
  Given the system has the following patients:
    | id        | domain            | first     | last      | gender    | birthday              |
    | 1         | uk.nhs.nhs_number | harry     | potter    | male      | 1980-01-01T00:00:00Z  |
    | 2         | uk.nhs.nhs_number | hermione  | granger   | female    | 1980-01-01T00:00:00Z  |
    | 3         | uk.nhs.nhs_number | ron       | weasley   | male      | 1980-01-01T00:00:00Z  |

  And the system has the following health data:
    | subject   | namespace         | date          | height | weight | bmi  | headCircumference  |
    | 1         | uk.nhs.nhs_number | 2010-04-10    | 78.5   | 9.8    | 17.2 |          50        |
    | 1         | uk.nhs.nhs_number | 2010-04-10    | 82.3   | 10.9   | 15   |          51        |
    | 1         | uk.nhs.nhs_number | 2010-04-10    | 86.3   | 11.9   | 16   |          52        |
    | 1         | uk.nhs.nhs_number | 2010-04-10    | 90     | 15     | 18.5 |          54        |

Scenario: Search for observations
   When the user searches for observations belonging to patient "harry"
   Then the user should receive a list of 4 observations
