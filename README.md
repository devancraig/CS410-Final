# Project 3: Identity Server

* Authors: Devan Craig, Tanner Halcumb
* Class: CS410 Section 001
* Semester: Spring 2020
* Project: Database Final Project

# Overview:
This project implements a java front end application to manage a mysql gradebook database. This command shell utilizes various methods to implement changes to the gradebook.

# Details
When we realized that our application would run and execute in its entirety each time a command was passed, and we needed a way to store the current class externally. Our conclusion was to write to a text file. We would provide the simple course_number for the current activated class, as well as its class_id. This allowed us to have simplified access to the current class despite our program executing each time and losing our stored global variables. We can view the currently activated class at any state by reading our currentClass.txt file.

# Database Connection: Heroku
In order to accomodate our remote working collaborations, we decided to utilize a Heroku database connection. This allowed us to mutually work within the same table structure, and provide the same connection string within our github repository. 

# Compiling and Using:

$ javac commands.java

#### Add a class
$ java -classpath ".:lib/mysql-connector.jar" commands new-class CS455 Sp20 1 "Distrubuted Systems"

#### List Classes
$ java -classpath ".:lib/mysql-connector.jar" commands list-classes

#### Select Classes With 1 Param
$ java -classpath ".:lib/mysql-connector.jar" commands select-class CS410

#### Select Classes With 2 Params
$ java -classpath ".:lib/mysql-connector.jar" commands select-class CS410 Sp20

#### Select Classes With 3 Params
$ java -classpath ".:lib/mysql-connector.jar" commands select-class CS410 Sp20 1

#### Add a student
$ java -classpath ".:lib/mysql-connector.jar" commands add-student devancraig 1440125142 craig devan

#### Show students
$ java -classpath ".:lib/mysql-connector.jar" commands show-students

#### Show students string
$ java -classpath ".:lib/mysql-connector.jar" commands show-students tanner

Where "tanner" is a string to query within the usernames.

#### Grade assignmentname username grade
$ java -classpath ".:lib/mysql-connector.jar" commands grade assignment_name username grade

#### Student grades username 
$ java -classpath ".:lib/mysql-connector.jar" commands student-grades username

#### Gradebook 
$ java -classpath ".:lib/mysql-connector.jar" commands gradebook