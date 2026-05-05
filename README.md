# 🛡️ FraudShield
### _Real-Time Fraud Detection Platform_

<p align="left">
  <img src="https://img.shields.io/badge/Java-21-blue?style=flat-square" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3-green?style=flat-square" />
  <img src="https://img.shields.io/badge/Kafka-Streams-black?style=flat-square" />
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square" />
</p>

---

## 🧩 Architecture Overview

```
┌──────────────────────────────┐
│  Transaction Service (8081)  │
│  REST API (Producer)         │
└──────────────┬───────────────┘
               │
               ▼
     fraudshield.transactions.created (Kafka Topic)
               │
               ▼
┌──────────────────────────────┐
│ Fraud Detection Service (8082)│
│ Kafka Streams Processing      │
│ - Rule Engine                 │
└──────────────┬───────────────┘
               │
               ▼
     fraudshield.fraud.detected (Kafka Topic)
               │
               ▼
┌──────────────────────────────┐
│   Alert Service (8083)       │
│   Consumer / Notifier        │
└──────────────────────────────┘
```

---

## 🔧 Services

| Service | Port | Responsibility |
|---|---|---|
| `fraudshield-transaction-service` | 8081 | Accepts transactions and publishes events |
| `fraudshield-fraud-detection-service` | 8082 | Applies fraud detection rules via Kafka Streams |
| `fraudshield-alert-service` | 8083 | Consumes fraud events and triggers alerts |

---

## ⚙️ Tech Stack

| Category | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Streaming | Apache Kafka + Kafka Streams |
| Database | PostgreSQL 16 |
| API Design | OpenAPI 3 (Contract First) |
| DevOps | Docker & Docker Compose |

---

## 🚀 Getting Started

**Prerequisites:** Java 21, Docker, Maven

```bash
# Start infrastructure
cd development
docker compose --env-file ../.environment/.env up -d

# Run services (recommended order)
cd fraudshield-transaction-service      && mvn spring-boot:run
cd ../fraudshield-fraud-detection-service  && mvn spring-boot:run
cd ../fraudshield-alert-service            && mvn spring-boot:run
```

---

## 🧠 Fraud Detection Rules

| Rule | Condition |
|---|---|
| High Amount | `amount > 10,000` |
| Suspicious Location | `location == OTHER` |

---

## 📊 Monitoring & APIs

| Tool | Access |
|---|---|
| Kafka UI | http://localhost:8080 |
| Swagger UI | http://localhost:8081/swagger-ui.html |

---

## 🔐 Environment Configuration

Create `.environment/.env`:

```bash
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
POSTGRES_DB=fraudshield
POSTGRES_USER=user
POSTGRES_PASSWORD=root

FRAUD_MAX_AMOUNT=10000.0

TRANSACTION_SERVICE_PORT=8081
FRAUD_DETECTION_SERVICE_PORT=8082
ALERT_SERVICE_PORT=8083
```

---

## 📄 License
MIT
