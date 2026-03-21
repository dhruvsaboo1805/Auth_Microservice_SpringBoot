# Auth Service

A plug-and-play authentication & authorization microservice built with Spring Boot.
Supports JWT-based auth and Google Authenticator 2FA.

## Features
- Register / Login
- JWT Authentication
- 2FA with Google Authenticator
- Role-based Authorization

## Quick Start

### Prerequisites
- Docker & Docker Compose installed

### Steps

1. Clone the repo
```bash
   git clone https://github.com/yourusername/auth-service.git
   cd auth-service
```

2. Create your `.env` from the example
```bash
   cp .env.example .env
```
   Then fill in your values in `.env`

3. Run with Docker Compose
```bash
   docker-compose up
```

4. Service is live at `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /auth/register | Register new user |
| POST | /auth/login | Login, get JWT |
| POST | /auth/verify-2fa | Verify 2FA OTP |

## Using in Your Project

Pull the image directly:
```bash
docker pull yourdockerhubusername/auth-service:1.0
```

Or add to your own `docker-compose.yml`:
```yaml
auth-service:
  image: yourdockerhubusername/auth-service:1.0
  ports:
    - "8080:8080"
  env_file:
    - .env
```
