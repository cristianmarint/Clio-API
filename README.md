![Java CI](https://github.com/cristianmarint/CLIO-API/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

# CLIO API

Patron of history CLIO API now allows you to store, catalog and relate books to authors through out Http protocols.


## Summary 📋

  - [Getting Started](#getting-started)
  - [Runing the tests](#running-the-tests)
  - [Deployment](#deployment)
  - [Built With](#built-with)
  - [Versioning](#versioning)
  - [Authors](#authors)
  - [License](#license)
  - [Acknowledgments](#acknowledgments)

## Getting Started

These instructions will get you a copy of the project up and running on
your local machine for development and testing purposes. See deployment
for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

    Maven

### Installing

A step by step series of examples that tell you how to get a development
env running

Say what the step will be

    git clone https://github.com/cristianmarint/Clio-API
    open it with with you code IDE
    set mail credentials on application.properties
    and then press the run botton
    

Request some data
    
    // http://localhost:8080/api/authors
        [{
        
        "id":  1,
        
        "name":  "Jostein Gaarder",
        
        "numberOfBooks":  1,
        
        "dateOfBirth":  "1952-08-12T08:25:24Z",
        
        "dateOfDeath":  null
        
    	    },{
        
        "id":  2,
        
        "name":  "Charles Bukowski",
        
        "numberOfBooks":  1,
        
        "dateOfBirth":  "1920-08-16T08:25:24Z",
        
        "dateOfDeath":  "1994-03-08T08:25:24Z"
        
        }]

## Running the tests 

Explain how to run the automated tests for this system.

    mvn -B clean verify

### And coding style tests

* This API **[tries]** follows [Google API recommendations](https://cloud.google.com/apis/design/resources)
* Test naming convention [Source](https://dzone.com/articles/7-popular-unit-test-naming)

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

  - [Spring Boot a Java ☕ framework](https://spring.io/projects/spring-boot)

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions
available, see the [tags on this
repository](https://github.com/cristianmarint/Clio-API/tags).

## Authors

  - **Cristian Marín** - *original author* -
    [cristianmarint](https://github.com/cristianmarint)

See also the list of
[contributors](https://github.com/cristianmarint/Clio-API/contributors)
who participated in this project.

## License

This project is licensed under the [MIT License](LICENSE.md)
Creative Commons License - see the [LICENSE](LICENSE) file for
details

## Acknowledgments

  - Chill not everything's perfect anyways.

[license-url]: https://github.com/cristianmarint/CLIO-API/blob/master/LICENSE
