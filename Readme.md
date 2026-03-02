# Bet99 Transaction Report System

## Overview

This is a Spring MVC web application that generates transaction reports
from MySQL within a selected date range.\
The system supports filtering, sorting, pagination, summary calculation,
and CSV export.

Architecture: Controller → Service → Repository → MySQL

------------------------------------------------------------------------

## Tech Stack

-   Java 8+
-   Spring MVC
-   Hibernate ORM
-   JSP (JSTL)
-   MySQL 8
-   Maven
-   Tomcat 9
-   JUnit 5 + Mockito

------------------------------------------------------------------------

## Features

### Search Report

Filters: - Start date/time (required) - End date/time (required) -
Account ID (optional) - Transaction Type (optional) - Game ID
(optional) - Platform Transaction ID (optional) - Game Transaction ID
(optional)

Validation: - Start date must be before End date - Date range is
required

------------------------------------------------------------------------

### Report Table

Columns: - id - account_id - datetime - tran_type - platform_tran_id -
game_tran_id - game_id - amount - balance

------------------------------------------------------------------------

### Sorting

Supported: - Date - Account - Transaction Type - Game ID - Ascending /
Descending

------------------------------------------------------------------------

### Filtering

Supported fields: - account_id - tran_type - game_id -
platform_tran_id - game_tran_id

------------------------------------------------------------------------

### Pagination

-   Page size: 25
-   Shows total records
-   Previous / Next navigation

------------------------------------------------------------------------

### Summary Section

Displays: - Total Bet - Total Win - Net = Win − Bet

------------------------------------------------------------------------

### CSV Export

Exports filtered results to CSV file.

------------------------------------------------------------------------

## Database Setup

### Create Database

``` sql
CREATE DATABASE bet99_report;
```

Run provided SQL script to create table and sample data.

------------------------------------------------------------------------

## Application Setup

### Configure Database

Edit: src/main/webapp/WEB-INF/spring/dispatcher-servlet.xml

Update credentials if needed.

------------------------------------------------------------------------

### Build

``` bash
mvn clean package
```

Output: target/bet99-report.war

------------------------------------------------------------------------

### Deploy

Copy WAR to: TOMCAT_HOME/webapps/

Access: http://localhost:8080/bet99-report/

------------------------------------------------------------------------

## Running Tests

``` bash
mvn test
```

Includes: - Service tests

Mockito configured for Java 21 compatibility.

------------------------------------------------------------------------

## Project Structure

com.bet99.report ├── controller ├── service ├── repository ├── entity
├── dto └── views

------------------------------------------------------------------------

## Design Notes

-   Layered architecture
-   Hibernate for persistence
-   Pagination via offset/limit
-   Aggregation using SQL functions
-   CSV streamed to response

------------------------------------------------------------------------

## Future Improvements

-   Docker compose setup
-   REST API version
-   Authentication layer

------------------------------------------------------------------------

## Author

Tarunjeet Singh
