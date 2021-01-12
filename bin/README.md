# ErikTerreriProject0
Backend server for a Dinosaur cataloging service.

# API Use

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