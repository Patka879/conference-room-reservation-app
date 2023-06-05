
# BOOKEY - CONFERENCE ROOM RESERVATION SYSTEM
##### Project back-end repository: https://github.com/Patka879/finalProject
##### Project front-end repository: https://github.com/Patka879/finalProjectFrontend


## Table of contents
* [General info](#general-info)
* [Rules of the application](#rules-of-the-application)
* [API Reference examples](#api-reference-examples)
* [Technologies](#technologies)
* [Setup](#setup)
* [Author](#author)

## General info
System created for managing reservations for conference rooms within one building, for multiple organizations.
This repository contains back-end code of the project. In order to find front-end code, please refer to the front-end repository.

## Rules of the application
### Rooms:
* Room name (unique in the system, min 2 characters, max 20
  character, can't be blank)
* Identifier / room number (unique in the system, can't be blank or example: "1.33")
* Level (number 0-10)
* Availability (true/false)
### Organizations
* Organization name (unique in the system, min 2 characters, max 20
  character, can't be blank)
* Any number of rooms can be added to given organization
* One room can be added to only one organization at a time. 
* <b>Organization can't be deleted if there are reservations for given organization.</b>
### Reservations
* Identifier (unique in the system, min 2 characters, max 20 character, can't
  be blank)
* Start date can't be created for current date because rooms must be prepared. 
* A reservation can be made only for one full day. In order to create reservation for two days, please create Two 
separate reservations
* Reservation can't be made for past days.
* Reservation can be made only two weeks in advance. 
* Start Time must be before end time of the reservation.
* Start Time must be after 5:59AM and before 7:01PM
* End Time must be after 5:59AM and before 8:01pm
* Reservation for given room can be only made if this room is associated with given reservation. 


## API Reference examples

### --- ORGANIZATION ---

### GET all organizations:

```http
  GET /organization/all
```

#### Sample output:

```json
[
    {
        "id": 154,
        "name": "sda",
        "roomNames": []
    },
    {
        "id": 155,
        "name": "transporeon",
        "roomNames": []
    },
    {
        "id": 156,
        "name": "pepsico",
        "roomNames": []
    }
]
```

### GET a specific organization:

```http
  GET /organization/{id}
```

| Parameter        | Type     | Description                               |
|:-----------------|:---------|:------------------------------------------|
| `id`             | `long`   | **Required**. requested organization's id |

#### Sample output:
```json
{
    "id": 154,
    "name": "sda",
    "rooms": [],
    "reservations": []
}
```

#### Sample output (invalid parameters):
```json
{
  "message": "Organization not found for ID: 157"
}
```

### POST

```http
  POST /organization/new
```

| Parameter | Type   | Description                  |
|:----------|:-------|:-----------------------------|
| `body`    | `json` | **Required** organizationName|

#### Sample output:
```json

{
    "id": 202,
    "name": "capita",
    "roomNames": []
}
```

#### Sample output (invalid parameters):
```json

  Organization with the name 'Capita' already exists"

```

### DELETE

```http
  DELETE /organization/{id}
```

| Parameter        | Type     | Description                               |
|:-----------------|:---------|:------------------------------------------|
| `id`             | `long`   | **Required**. requested organization's id |


#### Sample output (invalid parameters):
```
"Organization not found for ID: 1"
```

### PATCH (replace organization data)
```http
  PATCH organization/replace/{id}
```

| Parameter | Type   | Description                                     |
|:----------|:-------|:------------------------------------------------|
| `id`      | `long` | **Required**. id of the organization            |
| `body`    | `long` | **Required**. {'organizationName' : 'new name'} |

#### Sample output (invalid parameters):

```
"Organization not found for id: 1"
```


### PATCH (add room to organization)
```
  PATCH organization/{organizationId}/add-room/{roomId}
```
| Parameter        | Type     | Description                          |
|:-----------------|:---------|:-------------------------------------|
| `organizationId` | `long`   | **Required**. id of the organization |
| `roomId`         | `long`   | **Required**. id of the room         |


#### Sample output (invalid parameters):
```
 Organization or Room not found
```

## Technologies
Technologies used in the project:

Java
* Spring Boot
* Hibernate
* MySQL
* RESTful API 

## Setup
Instructions on how to set up the project:

* Clone the back-end repository:
```
$ git clone https://github.com/Patka879/finalProject.
```
* Ensure that you have Java, Spring Boot, Hibernate, and MySQL installed on your machine.

* Set up the MySQL database:
- Create a new database in MySQL for the project.
- Update the database configuration in the application.properties file located in the project's resources folder. 
- Set the appropriate values for spring.datasource.url, spring.datasource.username, and spring.datasource.password to match your MySQL database configuration.

* Open the project in your preferred Java IDE.

* Build the project to resolve dependencies and compile the code.

* Run the project by executing the main class (FinalProjectApplication) or using the IDE's built-in running mechanism.

* Once the server is running, you can interact with the API endpoints using HTTP requests.

* If you also want to set up the front-end of the project, clone the front-end repository:
```

$ git clone from https://github.com/Patka879/finalProject
```
Frontend and follow the setup instructions provided in its README file.

That's it! You should now have the back-end of the Booker - Conference Room Reservation System project set up and running.

## Author
[@Patrycja Mysiak](https://github.com/Patka879)
