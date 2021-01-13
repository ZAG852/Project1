# ErikTerreriProject0
Backend server for a Dinosaur cataloging service.
# Project Description
A cataloging service for dinosaurs.

# Technologies Used
- Apache Tomcat Server - version 9.0.46
- PostgreSQL - version 12
- Git
- Amazon Web Services
- jenkins
- Java 8
- Maven - 3.6.3

# Getting Started
- Pull the project from github
- Make sure you have the proper tools installed like Maven and Apache Tomcat
- Apache Tomcat Installation Instructions
	- Go to this address and download the Tomcat Server ```https://tomcat.apache.org/download-90.cgi```
	- Setup the CatalinaHome environment variable appropriately for your operating system environment.
- Maven Installation Instructions
	- Windows install maven through Chocolatey using the ```choco install maven``` command
	- Otherwise follow the instructions at ```https://maven.apache.org/```
	- Setup the M2 environment variable appropriately for your operating system environment.
- Setup the postgres database with the data below
- Set the following within the environment variables of your IDE or the environment variables of your OS:
	- DB_URL to the correct database URL
	- DB_USERNAME to the username of the database
	- DB_PASSWORD to the password of the database



# API Usage
This is meant to be used in such a way for a forum of users to 
put a bunch together a bunch of different dinosaurs and then manage those dinosaurs. You can see which user posts what dinosaur. As an admin
you will eventually be able to manage the users within the application as well. The following are the URI calls that can be used with this API:

## GET /dino
Gets a list of all Dinosaurs.

## GET /dino/{id}
Gets the dinosaur with the given id

## GET /dino/{name}
Gets the dinosaur with the given name

## GET /dino/period/{period}
Gets a list of dinosaurs from the given period

## GET /dino/user/{id}
Gets a list of dinosaurs added by the given user id

## GET /dino/user/{username}
Gets a list of dinosaurs added by the given username

## POST /dino
Creates a dinosaur given a name and period given in html form data

## PUT /dino/{id}
Updates the dinosaur with the given id with a new name give in html form data

## DELETE /dino/{id}
Deletes the dinosaur with the given id if admin or original creator

## GET /user
Gets current logged in user's username

## GET /user/list
Gets a list of all users

## GET /user/logout
Logs current user out

## POST /login
Creates a user entry if given a name, username, and password in html form data
Logs a user in if given just a username and password in html form data

## PUT /login
Updates currently logged in user to a given name in html form data

## DELETE /login
Deletes currently logged in user

# Setup
```sql
drop table if exists Users CASCADE;
create table Users(
	User_ID SERIAL primary key,
	Username VARCHAR(255) unique not null,
	Password VARCHAR(255) not null,
	Salt BYTEA not null,
	User_name VARCHAR(255),
	Permissions BOOLEAN
);

drop table if exists Dinosaurs;
create table Dinosaurs(
	Dinosaur_ID SERIAL primary key,
	Dinosaur_Name VARCHAR(255) unique not null,
	Period VARCHAR(255) not null,
	USER_ID INT not null,
	DateAdded DATE,
	foreign key(USER_ID) references Users(User_ID)
);
```

# Contributors
- The API was written by Erik Terreri.
- The Server-Side Rendered frontend was written by Zachary Garner