# Task Manager - Backend

This project was developed by Otavio Miguel as part of the selection process for the Senior Software Engineer position at Riachuelo.

---

## Technologies

* **Spring Boot:** Version 3.5.4
* **Java:** Version 21

---

## Getting Started

### Running the Tests

To execute the unit and integration tests for the project, run the following command in your terminal:

```sh
mvn test
```

### Running the Application with Docker

This project includes a **Dockerfile** and a **docker-compose.yml** file, making it easy to build and run the application using Docker.

1.  Navigate to the root directory of the project.
2.  Run the following command:


```sh
docker-compose up --build
```

This command will build the Docker image and start the application in a container.

-----

## API Documentation

This application provides API documentation using **Swagger/OpenAPI**.

Once the application is running, you can access the documentation at the following URL:

`http://localhost:8080/swagger-ui.html`


## Technical Decisions
* I chose to use the Clean Architecture pattern to organize the project.
* To simplify database communication, I used Spring JPA for defining tables, relationships, and queries.
* I used Docker to make building and running the entire project easier.
* I used Java 21 to enable virtual threads and improve the app’s performance.


## Use of AI

The following tasks involved the use of AI assistance:
* Clarifying specific details about implementing JWT authentication from scratch — something not commonly done on a daily basis.
* Assistance in developing some unit tests to speed up development.

```