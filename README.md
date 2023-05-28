# ISA Year 2022-2023 FTN E2 PRNII
## Centralized Information System for Blood Transfusion Center
Project for the subject Internet Software Architectures
Faculty of Technical Sciences
Computer Science and Automation
Applied Computer Science and Informatics

* Student 1: RA 145/2019 - Jovan Srdanov
* Student 2: RA 152/2019 - Strahinja Eraković
* Student 3: RA 143/2019 - Stefan Apostolović
* Student 4: RA 149/2019 - Aleksandar Stojanović

### Technologies:
* Front-end: Angular (npm)
* Back-end: Java Spring Boot (Maven)
* Database: PostgreSQL
* Message Queue: RabbitMQ

### Tools used during application development:
* Visual Studio Code - frontend application development
* IntelliJ IDEA - backend application development
* DBeaver - working with the database
* pgAdmin - working with the database

### Running the application:
* [Prerequisite for Angular](https://angular.io/guide/setup-local#prerequisites)
* Running the Angular application: Navigate to the ISA_2022_2023/FrontEnd/ISA/ folder through the command line and run the command: `npm install --force`, then `ng serve`

* [Installation and running RabbitMQ](https://www.youtube.com/watch?v=V9DWKbalbWQ)

* PostgreSQL needs to be installed, and two databases need to be created: bloodbank and gps
postgresql://localhost:5432/bloodbank
postgresql://localhost:5432/gps
For both:
username=postgres
password=password
Both databases work on the create-drop principle

* Running the Java Spring Boot application (IntelliJ IDEA):
Projects -> Open -> (3 projects need to be run: ExternalHospital, HelicopterGPS, BackEnd/ISA_2022)
Install all the dependencies from the pom.xml file
Run -> Run "Project Name"

* Running the Spring application (Eclipse):
Import the project into the workspace: Import -> Maven -> Existing Maven Project
Install all the dependencies from the pom.xml file
Right-click on the project -> Run as -> Java Application / Spring Boot app (if the STS plugin is installed from the Eclipse Marketplace)

### Logging into the application:
* The password is the same for everyone and it is "password" (the actual value is hashed in the database)
The data.sql file contains user emails and their roles, which are listed in the Account table. The roles are enumerated as follows:
  - BLOOD_DONOR
  - STAFF
  - SYSTEM_ADMIN
