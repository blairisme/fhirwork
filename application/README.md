# FHIR-Mapping

FHIR facade for openEMPI and Think!EHR/etherCIS.

## Requirements

### Vagrant

Vagrant 1.9.6+

VirtualBox 5.0+

### Local

Java 8

Gradle 4.0

Docker

docker-compose

## Build Instructions

### With Vagrant(Virtual Machine)
1. `vagrant up`
2. `vagrant ssh`
3. `cd /home/vagrant/openempi`
4. `docker-compose -d up`
5. `cd /vagrant/FHIR-MAPPING`
5. `cp config.properties.example config.properties`
6. `gradle appStart`

### Without Vagrant(Local Build)
1. clone **https://github.com/Yuan-W/docker-openempi.git**
2. Download openempi 3.3.0 war file from **https://www.dropbox.com/s/8ftbfrl8fku7ccd/openempi-entity-webapp-web-3.3.0c.war**, put it under `application/openempi-3.3.0/` directory within the openEMPI folder.
3. Start openEMPI with docker-compose
4. `cd FHIR-MAPPING`
5. `cp config.properties.example config.properties`
5. `gradle appStart`
