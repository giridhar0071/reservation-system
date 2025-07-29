# Reservation System

This is a simple backend application that lets **patients** book appointments at **healthcare organizations** (like hospitals and clinics) through a REST API. It also supports **representatives** (staff) who can book or cancel appointments on behalf of patients.

## Key Concepts

* **Patient**: A user who books an appointment (name, date of birth, contact info).
* **Representative**: Organization staff who can manage bookings and slots.
* **Organization**: A hospital or clinic, which can have multiple physical **locations**.
* **Slot**: A fixed time period on a specific date (Morning, Afternoon, Evening) at a given location.
* **Appointment**: A patient’s reservation for one slot, booked by either the patient or a representative.
* **Postal Code**: Used to convert a patient’s ZIP code into latitude/longitude to find the nearest location.

## Folder Structure
<img width="452" height="724" alt="Screenshot 2025-07-29 142336" src="https://github.com/user-attachments/assets/9dc45178-42a0-4b5c-b3b8-4e74af2dd6c9" />

```
reservation-system/
├── src/               # Java Spring Boot application code
│   ├── main/java/...  # Controllers, services, entities, repositories
│   └── main/resources # Configuration (application.yml)
├── db/                # Raw SQL scripts for database schema & sample data
│   ├── schema.sql     # CREATE TABLE statements
│   └── seeds.sql      # INSERT statements for initial data
├── output/            # Screenshots or generated files
├── pom.xml            # Maven build file
└── README.md          # This file


## Getting Started

### Prerequisites

* Java 24 (
* Maven
* PostgreSQL

### Database Setup

1. Create a new PostgreSQL database, e.g. `reservation_system`.
2. From the `db/` folder, run:

   ```bash
   psql -d reservation_system -f db/schema.sql
   psql -d reservation_system -f db/seeds.sql
   ```

   This creates all tables and loads sample locations and postal codes.

### Application Configuration

In `src/main/resources/application.yml`, set:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/reservation_system
    username: postgres
    password:Giridhar123
```

### Build & Run

```bash
# From project root
gradle build    # or: mvn package
java -jar build/libs/reservation-system.jar
# or, with Maven:
mvn spring-boot:run
```

The server starts on port **8080** by default.

## API Endpoints

All endpoints use **JSON**.

### 1. Find Nearby Locations

```
GET /api/locations/nearby?city={City}&zip={ZIP}
```

* Returns a list of organization locations within a 5-mile radius of the city/ZIP. If none found, returns the single nearest location.

### 2. Manage Slots

* **Create a slot**

  ```bash
  POST /api/slots
  Content-Type: application/json

  {
    "locationId": "<location-uuid>",
    "date": "2025-08-10",
    "slot": "MORNING",
    "capacity": 10
  }
  ```

* **List slots**

  ```bash
  GET /api/slots?locationId=<location-uuid>&date=2025-08-10
  ```

* **Delete a slot**

  ```bash
  DELETE /api/slots/{slotId}
  ```

### 3. Book & Cancel Appointments

* **Book**

  ```bash
  POST /api/appointments
  Content-Type: application/json

  {
    "patientId": "<patient-uuid>",
    "repId": "<rep-uuid>",    # or omit if patient books directly
    "city": "Bengaluru",
    "zip": "560001",
    "date": "2025-08-10",
    "slot": "MORNING"
  }
  ```

  * The system picks the nearest location, looks up the slot, checks availability and a 24-hour cutoff, then books.

* **Cancel**

  ```bash
  DELETE /api/appointments/{appointmentId}
  ```

  * Cannot cancel within 24 hours of the slot start.

## Next Steps

* Add **authentication** (e.g. JWT) so only logged-in patients/reps can call the API.
* Integrate **email notifications** for confirmations.
* Build a simple front-end or mobile client.

---

Now you have everything you need to run, test, and extend the Reservation System! Feel free to customize or reach out with questions.
