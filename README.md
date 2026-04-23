# CSA Coursework — Java EE Maven Web Server (Tomcat / TomEE)

## Overview

This repository contains a **Java (Maven) web server project** built with **Java EE / Jakarta EE-style APIs** and intended to run on an application server such as **Apache Tomcat** (Servlet container) or **Apache TomEE** (Java EE/Jakarta EE web profile).

The project exposes a REST-style API (“myAPI”) intended for client applications (web/mobile/other services) to interact with server-side resources.

---
## Part 1: Service Architecture & Setup

​**Q1:**
​In the default JAX-RS lifecycle, a new instance of the resource class is instantiated for every incoming request. After the request is processed and the response is sent back, the object is sent to the garbage collection.
​Architectural Impact: Since in the runtime it doesn't  default to a singleton, you can’t store data in non-static instance variables of the resource class.
​Synchronization: To avoid data loss and race conditions in in-memory data structures (like HashMap or ArrayList), we must use thread-safe collections (e.g., ConcurrentHashMap) or wrap your data in a Singleton-scoped provider/service class to ensure all request threads are interacting with the same synchronized data set.
​**Q2:** 
​Hypermedia as the Engine of Application State (HATEOAS) allows a client to communicat and exchange data with the API entirely through the responses provided dynamically by the server.
​Benefit to Developers: Unlike static documentation, which can become outdated, HATEOAS provides "discoverability." A new developer only needs to know the entry point (the Discovery endpoint); from there, the API provides the URI links for related actions. This reduces client-side typing code and allows the server to change URI structures without breaking the client.

​## Part 2: Room Management
​**Q1:**
​Returning Only IDs: This is friendly on the network bandwidth, taking up low bandwidth and reduces the payload size, which is best for mobile clients or large datasets. However, it increases the "chattiness" of the API, as the client must make subsequent calls to fetch details for each ID.
​Returning Full Objects: This reduces the number of requests from the user but cmes with very big response sizse, which could slow down the initial load and increase client-side processing overhead to parse the bigger JSON arrays.
​**Q2:**
​Yes, DELETE is idempotent.
​Justification: Idempotent means that making the same request multiple times and making it for the first time (once) has the same effect on the server state. In this implementation, the first DELETE request removes the room. Following identical DELETE requests will find that the room no longer exists; while the response code might change (e.g., from 204 No Content to 404 Not Found), the state of the server remains the same.


​## Part 3: Sensor Operations & Linking
​**Q1:** 
​The method is explicitly annotated with @Consumes(MediaType.APPLICATION_JSON). JAX-RS enforces strict content-type filtering.
If a client sends anything other than JSON, like text/plain or application/xml, the JAX-RS runtime will automatically reject the request before it reaches method logic.
The server will give an HTTP 415 Unsupported Media Type error response as output to the client is how JAX-RS handles the mismatch.
​**Q2:** 
​Query Parameters: These are especially designed for searching, sorting, and filtering collections. They allow for optional, combinable filters without changing the resource's identity.
​Path Parameters: These are used to identify a specific resource (e.g., a unique ID). Using path parameters for filtering (like /type/CO2) offer the URL hierarchy rigidity and suggests that "CO2" is a unique resource rather than a subset of sensors, which might violates standard RESTful naming conventions.


​## Part 4: Deep Nesting with Sub-Resources
​**Q1:**
​The Sub-Resource Locator pattern allows for effective separation of a range of concerns from the main resource.
​Complexity Management: Instead of a single "God Class" (controller) containing dozens of methods for all rooms, sensors, and readings, we can break the logic into smaller, maintainable classes.
​Reusability: It lets you better grasp the logic for SensorReadings independently, making the code easier to test and more readable as the API gets bigger.
​

Part 5: Advanced Error Handling & Logging
​**Q1:** 
​404 Not Found: Suggests the actual URI/endpoint requested does not exist.
​422 Unprocessable Entity: Indicates that the server understands the content type and the syntax of the request is correct, but it cannot process the contained instructions. Here, the JSON is "valid," but the specific roomId inside it refers to an absent entity, making it a logical error rather than a routing error.
​**Q2:** 
​Exposing raw stack traces is an unhealthy practice as it exposes security vulnerabilities in the system which can later be maliciously taken advantage of.
​Information Gathered: An attacker can see the exact package names, class structures, third-party library versions, and even file paths.
​Exploitation: This information will let an attacker know the versions of libraries we are using or understand the internal logic flow and they can create more sophisticated injection or bypass attacks.
​***Q3:**
​Using ContainerRequestFilter and ContainerResponseFilter handles cross-cutting concerns centrally.
​Maintainability: You write the logging logic once, and it automatically applies to every endpoint. Otherwise we would be manually creating Logger instances and hardcode the logs.
​Clean Code: It keeps your business logic (resource methods) clean and focused on the required area rather than being cluttered with repetitive boilerplate logging code.

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

- **Base URL (local)**
  - `(http://localhost:8080/NewCoursework/api/v1/)`
- **API Base Path**
  - `/api/v1`

Example resource pattern:
- `GET    http://localhost:8080/NewCoursework/api/v1/` → list resources
- `GET    http://localhost:8080/NewCoursework/api/v1/rooms/102` → fetch room one by id
- `POST   http://localhost:8080/NewCoursework/api/v1/rooms` → create new room resource (JSON body)
- `DELETE http://localhost:8080/NewCoursework/api/v1/rooms/103` → delete room resource

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

### 4) Error format
A consistent error payload makes debugging and client handling easier :
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
- Install Tomcat 10
  
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

1. Create a Room (POST /rooms)
curl -X POST http://localhost:8080/NewCoursework/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d "{\"id\":\"101\",\"name\":\"Server Room\",\"capacity\":10,\"sensorIds\":[]}"

2. Get All Rooms (GET /rooms)
curl -X GET http://localhost:8080/NewCoursework/api/v1/rooms \
  -H "Accept: application/json"

3. Add a Sensor to a Room (POST /sensors)
curl -X POST http://localhost:8080/NewCoursework/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d "{\"id\":\"Sensor3a\",\"roomId\":\"101\",\"type\":\"TEMPERATURE\",\"currentValue\":22.5}"

4. Get Sensors filtered by type (GET /sensors?type=TEMPERATURE)
curl -X GET "http://localhost:8080/NewCoursework/api/v1/sensors?type=TEMPERATURE" \
  -H "Accept: application/json"

5. Add a Reading for a Sensor (POST /sensors/{id}/reading)
curl -X POST http://localhost:8080/NewCoursework/api/v1/sensors/Sensor3a/reading \
  -H "Content-Type: application/json" \
  -d "{\"sensorId\":\"Sensor3a\",\"timestamp\":\"2026-04-23T10:00:00\",\"value\":25.3}"
---

## Project Structure (Typical Maven Web App)

- `src/main/java` — Java source (controllers/resources/services)
- `src/main/resources` — configuration files
- `src/main/webapp` — web assets, `WEB-INF`, JSPs (if used)
- `pom.xml` — Maven build configuration

---

## Notes / Troubleshooting

### Tomcat 10 

### Port already in use
If port `8080` is busy, change the connector port in:
- Tomcat/TomEE: `conf/server.xml`
- 

---

## License
Add a license here (or remove this section if not applicable).
