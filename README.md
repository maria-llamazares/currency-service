# Currency Exchange API

## Overview

The **Currency Exchange API** is a Spring Boot-based microservice that enables users to convert currencies. The API provides functionalities for checking exchange rates, performing currency conversions, and retrieving the converted amount. It is a key tool for financial management, offering simple and secure operations for converting currencies and accessing the corresponding exchange rate.

## Features

- Retrieve exchange rates for currency pairs.
- Convert amounts between currencies.
- Provide total converted amount.

---

## Technology Stack

The application is built with the following key technologies:

- **Java 23**: Primary programming language.
- **Spring Boot 3.4.3**: Framework for building the REST API.
- **Spring MVC**: RESTful services and routing.
- **Swagger 3/OpenAPI**: API documentation and testing.
- **Lombok**: Reduces boilerplate code for entities and DTOs.
- **Jakarta Validation**: Request validation for input consistency.
- **HTTP Client (RestTemplate/WebClient)**: Simulates external system calls.
- **Maven**: Project build and dependency management.
- **Spring Boot Starter Test:** For testing purposes, includes libraries like JUnit and Mockito.
- **JavaMoney (Moneta):**: Provides support for currency and monetary operations.

---

## Prerequisites

Before starting the application, ensure you have the following installed:

- **Java Development Kit (JDK) 23**
- **Maven 3.9.9** for dependency management
- **Git** for cloning and managing the repository.

---

## Installation and Setup

1. **Clone the Repository**  
   Clone this repository to your local machine:
   ```bash
   git clone https://github.com/maria-llamazares/currency-service
   cd currency-service
   ```

2. **Build the Project**  
   Use Maven to build and package the application:
   ```bash
   mvn clean install
   ```

3. **Run the Application**  
   Start the application using the following Maven command or by running the `CurrencyServiceApplication` class:
   ```bash
   mvn spring-boot:run
   ```

   Alternatively:
   ```bash
   java -jar target/currency-service-0.0.1-SNAPSHOT.jar
   ```

4. **Access API Documentation via Swagger**  
   Once the application is running, you can access the Swagger/OpenAPI documentation at:  
   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Testing the API

You can test the API using the following methods:

### 1. Swagger UI (Web Interface)
- Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) in your browser.
- Use the interactive interface to explore and test endpoints.
- Available endpoints include:
    - **`GET /exchange-rate?baseCurrency={baseCurrency}&targetCurrency={targetCurrency}&amount={amount}`**: Retrieve the exchange rate and the converted amount between two specified currencies.

### 2. Postman (API Client)
- A Postman collection is included in the project directory under:  
  `postman/Currency Exchange API.postman_collection.json`
- Import this collection into Postman and directly test the predefined requests:
    1. Open Postman.
    2. Go to *File -> Import*.
    3. Select the `Currency Exchange API.postman_collection.json` file from the project directory.

You can also customize the requests as needed to test different use cases.

---

### Simulated External System Call

Certain operations, such as deposits or debits, trigger a simulated external system call using the [HTTPStat API](https://httpstat.us). This could represent logging or notification in a real-world scenario.
