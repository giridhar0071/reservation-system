# Reservation System

Hello! I’m thrilled to share my **Reservation System** backend with you. It lets **patients** book appointments at **healthcare organizations** (like hospitals or clinics) and allows **representatives** (staff) to manage slots and bookings for patients.

## What’s Inside

* **Patient**: Registers themselves and books appointments
* **Representative**: Staff who can create or cancel appointments for any patient
* **Organization**: A hospital or clinic, which can have multiple **locations**
* **Slot**: A daily time window (MORNING, AFTERNOON, EVENING) at a location
* **Appointment**: A patient’s reserved slot, created by a patient or rep
* **PostalCode Table**: Converts ZIP codes to lat/lon so we can find the closest location to you

## Project Layout
<img width="451" height="721" alt="Screenshot 2025-07-29 143250" src="https://github.com/user-attachments/assets/37b8a708-2a04-4cf0-8321-81ca6cd0bd99" />


```text
reservation-system/       ← root folder
│  pom.xml                ← Maven build file
│  README.md              ← you’re reading it!
│
├─ db/                    ← raw SQL scripts
│    ├─ schema.sql        ← table definitions (DDL)
│    └─ seeds.sql         ← starter data (INSERTs)
│
├─ src/                   ← all Java code
│   ├─ main/java/...      ← controllers, services, models, repos
│   └─ main/resources/... ← configs (application.yml)
│
└─ output/                ← generated assets (screenshots, exports)
```

## How I Get It Running

### Prerequisites

* Java 17 or newer
* Maven
* PostgreSQL database

### 1. Set Up the Database

1. Create a Postgres database named `reservation_system`.
2. Run these two commands from the project root:

   ```bash
   psql -d reservation_system -f db/schema.sql
   psql -d reservation_system -f db/seeds.sql
   ```

   * The first script creates all the tables I need.
   * The second script pre-loads locations and postal codes.

### 2. Configure the App

Open `src/main/resources/application.yml` and update your DB credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/reservation_system
    username: YOUR_DB_USER
    password: YOUR_DB_PASSWORD
```

### 3. Build & Launch

From your terminal:

```bash
# build the jar
mvn clean package

# run the app
java -jar target/reservation-0.0.1-SNAPSHOT.jar
```

The API will be available at **[http://localhost:8080](http://localhost:8080)**.

## Quick API Guide

### Find the Nearest Locations

```http
GET /api/locations/nearby?city=Bengaluru&zip=560001
```

Returns all locations within 5 miles, or the single closest one.

### Manage Slots (Reps Only)

* **Create a slot**

  ```http
  POST /api/slots
  Content-Type: application/json

  {
    "locationId":"<location-uuid>",
    "date":"2025-08-15",
    "slot":"AFTERNOON",
    "capacity":5
  }
  ```
* **List slots**

  ```http
  GET /api/slots?locationId=<location-uuid>&date=2025-08-15
  ```
* **Delete a slot**

  ```http
  DELETE /api/slots/{slotId}
  ```

### Book & Cancel Appointments

* **Book**

  ```http
  POST /api/appointments
  Content-Type: application/json

  {
    "patientId":"<patient-uuid>",
    "repId":"<rep-uuid>",       # optional if patient books
    "city":"Mumbai",
    "zip":"400001",
    "date":"2025-08-20",
    "slot":"MORNING"
  }
  ```
* **Cancel**

  ```http
  DELETE /api/appointments/{appointmentId}
  ```

*Note: cancellations must happen more than 24 hours before the slot start.*

## What’s Next?

* Add **authentication** so users must log in.
* Integrate **email/SMS** notifications for confirmations.
* Build a simple **web or mobile** front-end.

Enjoy exploring the code! Feel free to reach out if you have questions or suggestions.
