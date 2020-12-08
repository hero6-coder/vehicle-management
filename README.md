# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Spring Boot application to manage users, vehicles are belong to user, and maintenance of each vehicle
* Context path: http://localhost:8888/vm
* APIs (following the API design best practices - Resource Oriented):
    * Create Users              : POST /v1/users
    * Get Active Users          : GET  /v1/users
    * Create or Update Vehicles : POST /v1/vehicles
    * Get all vehicles          : GET  /v1/vehicles
    * Get vehicles by user      : GET  /v1/vehicles?userId=
    * Create/Update Maintenance : POST /v1/maintenances
    * Delete Maintenance        : DELETE /v1/maintenances/{maintenanceId}

### How do I get set up? ###

* How to run:
    * mvn clean compile
    * mvn spring-boot:run
* Dependencies: Spring Boot, H2 database, JPA, Spring Security Crypto (encode/decode password of user)
* Database: After running app, 
    * Access link in the browser: http://localhost:8888/vm/h2-console
    * JDBC URL: jdbc:h2:mem:vehicle_management
* How to run tests:
    * Import postman collection (all test requests are into this collection): https://www.getpostman.com/collections/17c1ce76ee94cb39fe10
    * Create Users:
        * Run 2 requests 1, 2 to create 2 different users.
            * Check the USERS table to view data 
            * Check password field data (value is hashed)
        * Test user's email unique constraint: Rerun each of 2 above request => return 400 error code (Bad Request)
    * Get Users:
        * Run request "3.Get All Users" and see the list of 2 users
    * Create Vehicles:
        * Create Vehicle successfully: request 4
        * Create Vehicle without UserId: request 4.1 => Bad Request (400)
        * Create Vehicle that userId does not exist: request 4.2 => Bad Request (400)
    * Update Vehicles: Request 5 
        * Same api with creating, only different that request body contains "id" field
    * Create/Update Maintenance: 
        * Create Maintenance successfully: Request 6
        * Create Maintenance without VehicleId: request 6.1 => Bad Request (400)
        * Create Maintenance that VehicleId does not exist: request 6.2 => Bad Request (400)
    * Delete Maintenance:
        * Delete Maintenance successfully: Request 7
    * Get All Vehicles: 5.1
        * Get all vehicles by UserId: 5.2
        * Get vehicles: UserId does not exist: 5.3 => Bad Request (400)
    * Get All Maintenances: 8.1
        * Get all maintenances by VehicleId: 8.2
        * Get maitenances: VehicleId does not exist: 8.3 => Bad Request (400)