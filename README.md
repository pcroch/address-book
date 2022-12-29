# Address-book QR-code generator

## I. General description

This a simple api that provides me the opportunity to consolidate my learning and develop important others skills.

The api is an address book where the user can add professional or private address for a person. But hte user can generate either a nice formatted address  for a specific person or the user can generate a QR-Code to point on google map the address

## II. Technical description

### Current version:

      Version 1 is actually the one in Test.

### End Points:

    http://localhost:8080/person

    http://localhost:8080/address

### Initial Set-up

** Postgres **

Create a db in Postgres with the following command:

    psql -d address-book-db

** Build the app **

    mvn clean install

** Run the app **

    mvn clean spring-boot:run

** Changing the port **

In case of conflict with a port you can change the default port 8080 to the desire one in the respecting yml file

### How to fetch in short (example):

Fetch:

      curl -X POST                                                                                                                     \
            -H 'Content-Type      application/json'                                                                                       \
            http://localhost:8080/person/
            -d '{
            "firstname": "Test",
            "secondname": "nom",
            "lastname": "Fin",
            "address": [
            { "streetNumber": "783",
            "boxNumber": null,
            "streetName": "Albert Street",
            "zipcode": "7850",
            "locality": "Brussels",
            "country": "Belgium",
            "private": true } ] 
            }'

Render:

        {
        "personId": 353,
        "firstname": "Test",
        "secondname": "nom",
        "lastname": "Fin",
        "address": [
            {
                "addressId": 353,
                "streetNumber": "783",
                "boxNumber": null,
                "streetName": "Albert Street",
                "zipcode": "7850",
                "locality": "Brussels",
                "country": "Belgium",
                "person": null,
                "private": true
            }
        ]
    }

## III. HOW TO

    * CRUD actions on both controllers to be described *

## IV. Testing description

I tried here to have a 100% coverage for the testing

**A- HOW TO**

      Run: mvn clean test
      
      It will launch the Unit testing and Integration testing

**B- Integration Testing Description**

    We are testing the endpoints of each controllers

**C- Unit Testing Description**

    Basic Crud testing of the repository
