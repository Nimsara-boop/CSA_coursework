# CSA Coursework — Java EE Maven Web Server (Tomcat / TomEE)

## Overview

This repository contains a **Java (Maven) web server project** built with **Java EE / Jakarta EE-style APIs** and intended to run on an application server such as **Apache Tomcat** (Servlet container) or **Apache TomEE** (Java EE/Jakarta EE web profile).

The project exposes a REST-style API (“myAPI”) intended for client applications (web/mobile/other services) to interact with server-side resources.

---

## myAPI Design (High-Level)

### 1) Design goals
- **Clear separation of concerns**
  - API layer handles HTTP, routing, and serialization (JSON).
  - Business/service layer handles core rules and workflows.
  - Data access layer handles persistence (if applicable).
- **Predictable, resource-oriented URLs**
  - Resources are identified by URLs.
  - Operations use standard HTTP methods (GET/POST/PUT/DELETE).
- **Consistent request/response format**
  - JSON request bodies for create/update.
  - JSON responses for success and errors.

### 2) API Structure (typical pattern)
> Update the base path and endpoints below to match your implementation.

- **Base URL (local)**
  - `http://localhost:8080/<your-war-context>/`
- **API Base Path**
  - `/<api-base>` (commonly `/api`)

Example resource pattern:
- `GET    /api/<resource>` → list resources
- `GET    /api/<resource>/{id}` → fetch one by id
- `POST   /api/<resource>` → create new resource (JSON body)
- `PUT    /api/<resource>/{id}` → update existing resource (JSON body)
- `DELETE /api/<resource>/{id}` → delete resource

### 3) Request/Response conventions
- **Content-Type**
  - Clients should send: `Content-Type: application/json`
  - Server returns: `application/json` for API responses
- **HTTP status codes**
  - `200 OK` for successful reads/updates
  - `201 Created` for successful creates
  - `400 Bad Request` for invalid input
  - `404 Not Found` when a resource does not exist
  - `500 Internal Server Error` for unexpected server failures

### 4) Error format (recommended)
A consistent error payload makes debugging and client handling easier. Recommended shape:
```json
{
  "error": "Bad Request",
  "message": "Validation failed",
  "details": [
    "fieldX must not be empty"
  ]
}
```

---

## Requirements

You’ll need:

- **Java JDK**: 8+ (11+ recommended)
- **Maven**: 3.8+
- **Application server** (choose one):
  - **Apache Tomcat** (Servlet/JSP)
  - **Apache TomEE** (adds Java EE/Jakarta EE APIs out of the box)
- (Optional) An IDE like IntelliJ IDEA / Eclipse

Verify:
```bash
java -version
mvn -version
```

---

## Build the Project (Step-by-step)

1. **Clone the repository**
```bash
git clone https://github.com/Nimsara-boop/CSA_coursework.git
cd CSA_coursework
```

2. **Clean and build with Maven**
```bash
mvn clean package
```

3. **Locate the built WAR**
After a successful build, you should get a WAR file at:
- `target/<artifactId>.war`

If you’re not sure of the name, list the folder:
```bash
ls target
```

---

## Launch the Server (Tomcat) — Step-by-step

### Option A: Deploy by copying the WAR

1. **Download and unzip Tomcat**
- Install Tomcat 9 or 10 (match your code: Tomcat 9 is common for `javax.*`, Tomcat 10 is for `jakarta.*`).

2. **Copy WAR into Tomcat**
Copy:
- `target/<artifactId>.war`
to:
- `<TOMCAT_HOME>/webapps/`

3. **Start Tomcat**
- macOS/Linux:
```bash
<TOMCAT_HOME>/bin/startup.sh
```
- Windows:
```bat
<TOMCAT_HOME>\bin\startup.bat
```

4. **Open in browser**
- `http://localhost:8080/`
- Your app context is typically the WAR name:
  - `http://localhost:8080/<artifactId>/`

5. **Stop Tomcat**
- macOS/Linux:
```bash
<TOMCAT_HOME>/bin/shutdown.sh
```
- Windows:
```bat
<TOMCAT_HOME>\bin\shutdown.bat
```

---

## Launch the Server (TomEE) — Step-by-step

TomEE is often easier for Java EE features (JAX-RS, CDI, etc.).

1. **Download and unzip TomEE**
Choose a distribution like “WebProfile” if you’re using JAX-RS/CDI.

2. **Deploy WAR**
Copy:
- `target/<artifactId>.war`
to:
- `<TOMEE_HOME>/webapps/`

3. **Start TomEE**
- macOS/Linux:
```bash
<TOMEE_HOME>/bin/startup.sh
```
- Windows:
```bat
<TOMEE_HOME>\bin\startup.bat
```

4. **Verify**
- `http://localhost:8080/<artifactId>/`

---

## Testing the API

Once deployed, test with a tool like `curl` or Postman.

Example (adjust to your real endpoint):
```bash
curl -i http://localhost:8080/<artifactId>/api/health
```

---

## Project Structure (Typical Maven Web App)

Common layout you may see:
- `src/main/java` — Java source (controllers/resources/services)
- `src/main/resources` — configuration files
- `src/main/webapp` — web assets, `WEB-INF`, JSPs (if used)
- `pom.xml` — Maven build configuration

---

## Notes / Troubleshooting

### Tomcat 9 vs Tomcat 10 (important)
- If your imports use **`javax.*`**, you typically want **Tomcat 9** / Java EE era libraries.
- If your imports use **`jakarta.*`**, you typically want **Tomcat 10+** / Jakarta EE.

### Port already in use
If port `8080` is busy, change the connector port in:
- Tomcat/TomEE: `conf/server.xml`

---

## License
Add a license here (or remove this section if not applicable).
