Feature: Create Patient
    This feature allows users to add patient data to the system.

Scenario: Add patient
    Given the system has no patients
    When the user adds a patient with the following data:
        | id    | first     | last          | gender    |
        | 1     | harry     | potter        | male      |
    Then the system should contain a patient named "harry"