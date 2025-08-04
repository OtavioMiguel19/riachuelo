# Task Manager

This project was developed by Otavio Miguel as part of the selection process for the Senior Software Engineer position at Riachuelo.

---

## Technologies

### Backend
* **Spring Boot:** Version 3.5.4
* **Java:** Version 21

### Frontend

* **Angular:** Version 20.1.4
* **Node:** Version 22.18.0
* **Npm:** Version 10.9.3

---

## Getting Started

### Running the Application with Docker
This project includes two **Dockerfiles** (one in the backend and one in the frontend) as well as a **docker-compose.yml** file, making it easy to build and run the entire application using Docker.  
By running the `docker-compose.yml` at the root level, the backend, frontend, and their dependencies will be started in Docker containers.

1.  Navigate to the root directory of the project.
2.  Run the following command:

```sh
docker-compose up --build
```

This command will build the Docker image and start the application in a container.

### Running the Tests in the Backend

To execute the unit and integration tests for the project, run the following command in your terminal:

```sh
cd task-manager-backend
```
```sh
mvn test
```

### Accessing the Frontend

Once the frontend is up and running, it will be accessible at:
`http://localhost:4200`.

The frontend runs on port **4200** and communicates with the backend server running on port **8080**.  
You may register a new account to use the application.

-----


## Technical Decisions
* I used Docker to make building and running the entire project easier.

### Backend
* I chose to use the Clean Architecture pattern to organize the project.
* To simplify database communication, I used Spring JPA for defining tables, relationships, and queries.
* I used Java 21 to enable virtual threads and improve the app’s performance.

### Frontend
* I chose to use Bootstrap to ensure a pleasant visual experience and responsiveness.

## Use of AI
The following tasks involved the use of AI assistance:

### Backend
* Clarifying specific details about implementing JWT authentication from scratch — something not commonly done on a daily basis.
* Assistance in developing some unit tests to speed up development.

### Frontend
* Since I don't work with Angular on a daily basis, I had several questions. Due to time constraints, I used AI to assist with these development challenges.
* Assistance with debugging issues.
* Help improving my HTML during moments when I ran out of design ideas.

```