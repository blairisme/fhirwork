# FHIRWork

[![Build Status](https://travis-ci.org/blairisme/fhirwork.svg?branch=develop)](https://travis-ci.org/blairisme/fhirwork)
[![Code Coverage](https://codecov.io/gh/blairisme/fhirwork/branch/develop/graph/badge.svg)](https://codecov.io/gh/blairisme/fhirwork)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a35ff5d290d44bacab2a561044e802f8)](https://www.codacy.com/app/kalperen/fhirwork?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=blairisme/fhirwork&amp;utm_campaign=Badge_Grade)

[FHIR](https://www.hl7.org/fhir/) compliant server backed by an [OpenEHR](https://www.openehr.org/) repository.

## Test Environment

The FHIRWork server requires a number of services in order to run. Chiefly an OpenEHR from which to obtain patient data, an an EMPI service containing patient identifiers.

1. Download, install and run [Docker](https://www.docker.com).
1. Open a terminal and change directory to the directory containing this file.
1. Run `gradlew envStart`.

This will download and provision the EHR and EMPI services, running them when complete. Future calls to this command will run the previously provisioned service instances. When running, the services can be used at the following addresses.

  * __EthercisEHR REST API__: http://localhost:8888
  * __OpenEMPI REST API__: http://localhost:8080
  * __OpenEMPI Admin__: http://localhost:8080/openempi-admin

When the services are no longer needed, run the following.

1. Open a terminal and change directory to the directory containing this file.
1. Run `gradlew stopEnvironment`

## Develop Environment

The FHIRWork project supports development from within the [Eclipse](https://www.eclipse.org/) and [IntelliJ](https://www.jetbrains.com/idea/) integrated development environments (IDE). The following steps can be used to generate project files for these applications.

1. Open a terminal and change directory to the directory containing this file.
1. Execute either of the following commands to generate the IDE project files.
  * __Eclipse__: `gradlew eclipse`
  * __IntelliJ__: `gradlew idea`
1. Open/Import the project files in the application directory.

## Troubleshooting

* Java 9 is currently __not__ supported. Please use Java 8 instead.
