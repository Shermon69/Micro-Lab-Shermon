# SE4010 — Microservices Lab (Spring Boot) IT 22177964 - Harrish Shermon 

## Project Structure

```
microservices-lab/
├── docker-compose.yml
├── item-service/          → Port 8081
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/lab/itemservice/
│       ├── ItemServiceApplication.java
│       └── ItemController.java
├── order-service/         → Port 8082
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/lab/orderservice/
│       ├── OrderServiceApplication.java
│       └── OrderController.java
├── payment-service/       → Port 8083
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/lab/paymentservice/
│       ├── PaymentServiceApplication.java
│       └── PaymentController.java
└── api-gateway/           → Port 8080
    ├── Dockerfile
    ├── pom.xml
    └── src/main/
        ├── java/com/lab/apigateway/ApiGatewayApplication.java
        └── resources/application.yml
```

---

## Step-by-Step: Build & Run

### Prerequisites
- Java 21
- Maven
- Docker & Docker Compose

---

### Step 1 — Build each service JAR

Run these commands from the project root:

```bash
cd item-service    && mvn clean package
cd order-service   && mvn clean package
cd payment-service && mvn clean package
cd api-gateway     && mvn clean package
```

Each command produces a JAR in `<service>/target/*.jar` which Docker copies into the image.

---

### Step 2 — Build Docker images & start all containers

```bash
docker-compose build
docker-compose up
```
---

### Step 3 — Verify containers are running

```bash
docker ps
```

You should see 4 containers: `item-service`, `order-service`, `payment-service`, `api-gateway`.

---

### Step 4 — Test with Postman (all via Gateway on port 8080)

#### Items
| Method | URL | Body |
|--------|-----|------|
| GET | http://localhost:8080/items | — |
| GET | http://localhost:8080/items/1 | — |
| POST | http://localhost:8080/items | `{"name": "Headphones"}` |

#### Orders
| Method | URL | Body |
|--------|-----|------|
| GET | http://localhost:8080/orders | — |
| GET | http://localhost:8080/orders/1 | — |
| POST | http://localhost:8080/orders | `{"item": "Laptop", "quantity": 2, "customerId": "C001"}` |

#### Payments
| Method | URL | Body |
|--------|-----|------|
| GET | http://localhost:8080/payments | — |
| GET | http://localhost:8080/payments/1 | — |
| POST | http://localhost:8080/payments/process | `{"orderId": 1, "amount": 1299.99, "method": "CARD"}` |

> **All requests go through port 8080 (the API Gateway)**


---

## Architecture

```
Client (Postman)
      │
      ▼
API Gateway :8080
  /items/**   ──► item-service:8081
  /orders/**  ──► order-service:8082
  /payments/** ─► payment-service:8083
```

All services share the `microservices-net` Docker bridge network.
Services communicate using their **container name** as the hostname (not `localhost`).
