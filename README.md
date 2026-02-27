# Student Management System

Spring Boot web application for managing students with full CRUD, search, pagination, and REST API.

## Tech Stack

- Java 21, Spring Boot 3.5
- Spring Data JPA, Thymeleaf, PostgreSQL
- Swagger UI (SpringDoc OpenAPI)
- Docker, Docker Compose, GitHub Actions CI/CD

## How to Run

```bash
# 1. Copy env file and fill in your PostgreSQL credentials
cp .env.example .env

# 2. Start (PostgreSQL + App)
docker compose up -d

# 3. Stop
docker compose down
```

App starts after PostgreSQL is healthy (~10s).

## URLs

| URL | Description |
|-----|-------------|
| http://localhost:8080/students | Web UI - Student list (CRUD, search, sort, pagination) |
| http://localhost:8080/students/new | Web UI - Add new student |
| http://localhost:8080/swagger-ui.html | Swagger UI - API documentation & testing |
| http://localhost:8080/api/students | REST API - JSON endpoints |

## REST API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | List all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/api/students/search?keyword=abc` | Search by name or email |

### Example

```bash
# Create
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Nguyen Van A","email":"nva@hcmut.edu.vn","age":20}'

# List
curl http://localhost:8080/api/students

# Search
curl "http://localhost:8080/api/students/search?keyword=gmail"
```

## Environment Variables (.env)

```
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_db
```

## CI/CD

GitHub Actions automatically builds and pushes Docker image on push to `main`.

**Image:** `thaily/ltnc-lab:latest`

### Setup

Add these secrets in GitHub repo settings (**Settings > Secrets and variables > Actions**):

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Project Structure

```
src/main/java/vn/edu/hcmut/cse/adse/lab/
├── StudentManagementApplication.java
├── DataSeeder.java
├── entity/Student.java
├── repository/StudentRepository.java
├── service/StudentService.java
└── controller/
    ├── StudentController.java      (REST API)
    └── StudentWebController.java   (Web UI)

src/main/resources/
├── application.properties
└── templates/
    ├── students.html          (list + search + pagination)
    ├── student-detail.html    (detail view)
    └── student-form.html      (add/edit form)
```
