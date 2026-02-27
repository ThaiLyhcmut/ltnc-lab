# Student Management System

Web application quản lý sinh viên: thêm, sửa, xóa, xem chi tiết, tìm kiếm, phân trang, xóa nhiều.

## Features

- CRUD sinh viên (tên, email, tuổi)
- Tìm kiếm theo tên hoặc email
- Sắp xếp theo cột (Name, Email, Age)
- Phân trang (20 sinh viên/trang)
- Chọn nhiều & xóa hàng loạt
- Validation: email hợp lệ, email không trùng, tên bắt buộc, tuổi 1-150
- REST API + Swagger UI
- Seed 30 sinh viên mẫu khi database trống

## Cách chạy

### 1. Docker Compose (khuyên dùng)

Chạy cả PostgreSQL + App trong Docker, không cần cài gì thêm.

```bash
cp .env.example .env   # sửa credentials
docker compose up -d   # start
docker compose down    # stop
```

### 2. Local + PostgreSQL

Cần Java 21 + PostgreSQL đang chạy sẵn.

```bash
cp .env.example .env   # sửa credentials

# Sửa .env thêm các dòng:
# DB_URL=jdbc:postgresql://localhost:5432/mydb
# DB_USERNAME=thaily
# DB_PASSWORD=yourpassword

set -a && source .env && set +a
./mvnw spring-boot:run
```

### 3. Local không có PostgreSQL (SQLite)

Chỉ cần Java 21, không cần database. Dữ liệu lưu file `students.db`.

```bash
./mvnw spring-boot:run
```

## File .env

```bash
# Cho Docker Compose
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_db
```

## Truy cập

| URL | Mô tả |
|-----|-------|
| http://localhost:8080/students | Danh sách sinh viên (tìm, sắp xếp, phân trang, xóa nhiều) |
| http://localhost:8080/students/new | Thêm sinh viên mới |
| http://localhost:8080/students/{id} | Xem chi tiết sinh viên |
| http://localhost:8080/students/{id}/edit | Sửa sinh viên |
| http://localhost:8080/swagger-ui.html | Swagger UI - test API trực tiếp trên trình duyệt |

## REST API

Chi tiết đầy đủ tất cả endpoints, parameters, request/response xem tại **Swagger UI**: http://localhost:8080/swagger-ui.html

## CI/CD

Push lên `main` -> GitHub Actions tự build & push image `thaily/ltnc-lab:latest` lên DockerHub.

Thêm 2 secrets trong GitHub repo (**Settings > Secrets > Actions**):
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Cloudflare Tunnel (tuỳ chọn)

Nếu muốn expose app ra internet qua Cloudflare Tunnel, thêm service sau vào `docker-compose.yml`:

```yaml
  tunnel:
    image: cloudflare/cloudflared:latest
    container_name: ltnc_tunnel
    restart: unless-stopped
    command: tunnel --no-autoupdate run --protocol http2 --token ${TUNNEL_TOKEN}
    env_file: .env
    networks:
      - app-network
```

Và thêm vào `.env`:

```bash
TUNNEL_TOKEN=your_tunnel_token
```
