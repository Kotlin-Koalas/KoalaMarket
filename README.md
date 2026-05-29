# 🐨 KoalaMarket

> A full-stack mobile e-commerce platform built with a Kotlin Android app and a Spring Boot REST API.

---

## Overview

KoalaMarket is a mobile marketplace application that lets users browse products, manage a cart, and place orders — all from an Android device. The backend exposes a RESTful API built with Spring Boot, backed by PostgreSQL, and served alongside an image hosting service. The entire backend stack is containerised with Docker Compose for easy local setup and deployment.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Mobile (Android) | Kotlin |
| Backend API | Spring Boot (Kotlin / Java) |
| Database | PostgreSQL |
| Image Storage | imgpush |
| Containerisation | Docker Compose |
| CI/CD | GitHub Actions |

---

## Project Structure

```
KoalaMarket/
├── frontend/        # Android application (Kotlin)
├── backend/         # Spring Boot REST API
├── .github/
│   └── workflows/   # CI/CD pipelines
├── compose.yaml     # Docker Compose — DB, image service, API
└── .gitignore
```

---

## Getting Started

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/) & Docker Compose
- [Android Studio](https://developer.android.com/studio) (for the mobile app)
- JDK 17+

### Backend (Docker Compose)

1. Copy the example env file and fill in your values:

   ```bash
   cp .env.example .env
   ```

2. Start all services (PostgreSQL, imgpush, Spring Boot API):

   ```bash
   docker compose up --build
   ```

   The API will be available at `http://localhost:<PUBLIC_API_PORT>` as configured in your `.env`.

### Android App

1. Open the `frontend/` directory in Android Studio.
2. Update the base URL in the app's config to point to your running API.
3. Run on an emulator or physical device.

---

## Environment Variables

| Variable | Description |
|---|---|
| `POSTGRES_DB` | Database name |
| `POSTGRES_USER` | Database user |
| `POSTGRES_PASSWORD` | Database password |
| `POSTGRES_VERSION` | PostgreSQL image tag |
| `API_PORT` | Internal port for the Spring Boot API |
| `PUBLIC_API_PORT` | Host-mapped port for the API |
| `IMAGE_PORT` | Port for the imgpush image service |

---

## CI/CD

GitHub Actions workflows are defined in `.github/workflows/`. They handle automated builds and tests on push and pull request events.

---

## License

This project is licensed under the [MIT License](LICENSE).
