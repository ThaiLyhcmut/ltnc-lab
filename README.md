# Student Management System

Web application quản lý sinh viên: thêm, sửa, xóa, xem chi tiết, tìm kiếm, phân trang, xóa nhiều.

## Thông tin nhóm

| MSSV | Họ tên |
|------|--------|
| 2213104 | Lý Vĩnh Thái |

## Public URL

> https://ltnc.thaily.id.vn

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

## Câu hỏi lý thuyết (Lab 1)

### Bài 2: Ràng buộc Khóa Chính – "Tại sao Database lại chặn thao tác này?"

Khi insert một student với `id` đã tồn tại, database trả lỗi `UNIQUE constraint failed` vì cột `id` là **Primary Key**. Primary Key đảm bảo mỗi dòng trong bảng có giá trị duy nhất — nếu cho phép trùng thì không thể phân biệt được các bản ghi, dẫn đến sai lệch khi truy vấn, cập nhật, xóa.

### Bài 3: Toàn vẹn dữ liệu – "Database có báo lỗi không? Sự thiếu chặt chẽ này ảnh hưởng gì khi code Java đọc dữ liệu lên?"

SQLite **không báo lỗi** khi insert giá trị NULL vào cột `name` vì mặc định SQLite không ép NOT NULL constraint. Khi Java đọc dữ liệu lên, các field String sẽ nhận giá trị `null`, dễ gây **NullPointerException** nếu code không kiểm tra. Đây là lý do cần thêm annotation `@NotBlank` / `@NotNull` ở tầng Entity và validation ở tầng Controller để đảm bảo toàn vẹn dữ liệu từ đầu vào.

### Bài 4: Cấu hình Hibernate – "Tại sao mỗi lần tắt ứng dụng và chạy lại, dữ liệu cũ trong Database lại bị mất hết?"

Vì cấu hình `spring.jpa.hibernate.ddl-auto=create`. Giá trị `create` khiến Hibernate **xóa toàn bộ bảng và tạo lại** mỗi lần khởi động app, nên dữ liệu cũ bị mất. Để giữ dữ liệu, dùng `ddl-auto=update` (chỉ thêm cột/bảng mới, không xóa dữ liệu) hoặc `ddl-auto=validate` (chỉ kiểm tra schema, không thay đổi gì).

## Screenshots (Lab 4)

### Danh sách sinh viên (search, sort, pagination, bulk delete)
![Student List](screenshots/student-list.png)

### Thêm sinh viên mới
![Add Student](screenshots/student-add.png)

### Chi tiết sinh viên
![Student Detail](screenshots/student-detail.png)

### Sửa sinh viên
![Edit Student](screenshots/student-edit.png)

### Swagger UI
![Swagger UI](screenshots/swagger-ui.png)
