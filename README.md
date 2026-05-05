# FraudShield 🛡️
Real-time fraud detection system built with Spring Boot and Apache Kafka Streams.

---

## Architecture

```
transaction-service ──→ fraudshield.transactions.created ──→ fraud-detection-service
                                                                        ↓
alert-service ←────────── fraudshield.fraud.detected ←─────── (fraud rules applied)
```

| Service | Port | Role |
|---|---|---|
| `fraudshield-transaction-service` | 8081 | REST API — accepts and publishes transactions |
| `fraudshield-fraud-detection-service` | 8082 | Kafka Streams — applies fraud rules |
| `fraudshield-alert-service` | 8083 | Consumer — processes fraud alerts |

---

## Tech Stack

| | |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Messaging | Apache Kafka + Kafka Streams |
| Database | PostgreSQL 16 |
| API Design | OpenAPI 3 (Contract First) |
| Infrastructure | Docker + Docker Compose |

---

## Getting Started

**Prerequisites:** Java 21, Docker, Maven

```bash
# 1. Start infrastructure
cd development && docker compose --env-file ../.environment/.env up -d

# 2. Run services (in order)
cd fraudshield-transaction-service      && mvn spring-boot:run
cd fraudshield-fraud-detection-service  && mvn spring-boot:run
cd fraudshield-alert-service            && mvn spring-boot:run
```

---

## Fraud Detection Rules

| Rule | Condition |
|---|---|
| High Amount | `amount > 10,000` |
| Suspicious Location | `location == OTHER` |

---

## Monitoring

| Tool | URL |
|---|---|
| Kafka UI | http://localhost:8080 |
| Swagger UI | http://localhost:8081/swagger-ui.html |

---

## Environment Variables

Create `.environment/.env` at project root:

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

## License
MIT
