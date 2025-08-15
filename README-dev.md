# Dev Quickstart (Backend + Frontend)

## Prereqs
- JDK 21
- Maven
- Node 18+ (for frontend)

---

## Backend (H2 in-memory, seeded from `data.sql`)
From the backend repo root:

```bash
mvn -Dspring-boot.run.profiles=dev spring-boot:run

```
### Test the backend
In another terminal, run:
```bash
curl -i http://localhost:8080/test