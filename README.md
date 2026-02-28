# ToolBox - Developer Tools Collection

A collection of useful developer tools built with Java + Vue.

## 🎯 Features

| Tool | Description |
|------|-------------|
| 🔢 UUID Generator | Generate UUIDs in batch |
| ⏰ Timestamp Converter | Unix ↔ Human readable time |
| 🔐 Password Generator | Random password with customizable options |
| 🖼️ Base64 Encoder/Decoder | Encode and decode Base64 |
| 📝 JSON Formatter | Format, compress, validate JSON |

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2 + Java 17
- **Frontend**: Vue 3 + Vite (coming soon)

## 🚀 Quick Start

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

Server runs at http://localhost:8080

## 📡 API Endpoints

### UUID
- `GET /api/uuid/generate?count=10`

### Timestamp
- `GET /api/timestamp/now`
- `GET /api/timestamp/convert?unix=1700000000`
- `GET /api/timestamp/convert?datetime=2024-01-01T00:00:00`

### Password
- `GET /api/password/generate?length=16&lowercase=true&uppercase=true&digits=true&special=false`

### Base64
- `GET /api/base64/encode?text=hello`
- `GET /api/base64/decode?text=aGVsbG8=`

### JSON
- `POST /api/json/format` → `{"data": {"key": "value"}}`
- `POST /api/json/compress` → `{"data": {"key": "value"}}`
- `POST /api/json/validate` → `{"data": {"key": "value"}}`

## 📄 License

MIT