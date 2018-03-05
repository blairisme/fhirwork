Feature: Create Patient
    This feature allows users to add patient data to the system.

Scenario: Add patient
    Given the system has no patients
    When the user adds a patient with the following data:
        | id        | domain    | first     | last      | gender    | birthday              |
        | 1         | SSN       | harry     | potter    | male      | 1980-01-01T00:00:00Z  |
    Then the system should contain a patient named "harry"