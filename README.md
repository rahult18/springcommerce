# SpringCommerce - Spring Boot Microservices Project

This repository contains code for a microservices-based e-commerce application developed using Spring Boot. This project demonstrates how to build, test, and deploy microservices using Spring Cloud and Docker.

## Architecture Overview

The application is composed of multiple microservices:

- **Product Service**: Manages product information (MongoDB)
- **Order Service**: Handles customer orders (MySQL)
- **Inventory Service**: Tracks product inventory (MySQL)

![Architecture Overview](https://raw.githubusercontent.com/rahult18/spring-boot-microservices/main/Project%20Architecutre%20Diagram.png)

## Tech Stack

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data MongoDB** (Product Service)
- **Spring Data JPA** (Order & Inventory Services)
- **MySQL** (Order & Inventory Services)
- **MongoDB** (Product Service)
- **Flyway** (Database migrations)
- **Lombok** (Reduce boilerplate code)
- **Docker & Docker Compose** (Containerization)
- **TestContainers** (Integration testing)

### Start the Databases

```bash
docker-compose up -d
```

This will start:
- MongoDB (for Product Service)
- MySQL (for Order Service and Inventory Service)

### Building the Services

Build all services using Maven:

```bash
mvn clean package
```

Or we can build each service individually:

```bash
cd product-service
mvn clean package
cd ../order-service
mvn clean package
cd ../inventory-service
mvn clean package
```

### Running the Services

Each service runs on a different port:

- Product Service: 8080
- Order Service: 8081
- Inventory Service: 8082

You we can run each service:

```bash
cd product-service
mvn spring-boot:run

cd order-service
mvn spring-boot:run

cd inventory-service
mvn spring-boot:run
```

## Database Setup

### MongoDB (Product Service)

The docker-compose file automatically sets up MongoDB with the following configuration:
- Port: 27017
- Username: root
- Password: password
- Database: product-service

### MySQL (Order Service & Inventory Service)

The docker-compose file sets up MySQL with:
- Port: 3306
- Username: root
- Password: mysql
- Databases: 
  - order_service
  - inventory_service

Database migrations are handled automatically by Flyway.

## API Documentation

### Product Service

- **Create Product**
  - Endpoint: POST /api/product
  - Request Body:
    ```json
    {
      "name": "Product Name",
      "description": "Product Description",
      "price": 100.00
    }
    ```
  - Response: 201 CREATED

- **Get All Products**
  - Endpoint: GET /api/product
  - Response: List of products

### Order Service

- **Place Order**
  - Endpoint: POST /api/order
  - Request Body:
    ```json
    {
      "skuCode": "iphone_15",
      "price": 1000,
      "quantity": 1
    }
    ```
  - Response: 201 CREATED

### Inventory Service

- **Check Inventory**
  - Endpoint: GET /api/inventory?skuCode=iphone_15&quantity=1
  - Response: Boolean (true if in stock, false otherwise)

## Testing

The project uses JUnit and TestContainers for integration testing. Run tests with:

```bash
mvn test
```
