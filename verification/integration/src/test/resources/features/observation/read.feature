Feature: Observation Reading
    This feature allows users to get data on observations made about patients.

Background:
  Given the system has the following patients:
    | id    | first     | last          | gender    |
    | 1     | harry     | potter        | male      |
    | 2     | hermione  | granger       | female    |

  And the system has the following health data:
    | subject   | namespace | date          | height | weight | bmi  | headCircumference  |
    | 1         | SSN       | 2010-04-10    | 78.5   | 9.8    | 17.2 |          50        |
    | 1         | SSN       | 2010-04-10    | 82.3   | 10.9   | 15   |          51        |
    | 1         | SSN       | 2010-04-10    | 86.3   | 11.9   | 16   |          52        |
    | 1         | SSN       | 2010-04-10    | 90     | 15     | 18.5 |          54        |

#Scenario: Search for observations
#   When the user searches for observations
#   Then the user should receive a list of 6 observations
