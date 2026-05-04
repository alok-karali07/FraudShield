# FraudShield 🛡️
A real-time fraud detection system built with Spring Boot and Apache Kafka Streams.

---

## Architecture

```
POST /api/v1/transactions
        ↓
transaction-service  ──→  fraudshield.transactions.created  ──→  fraud-detection-service
                                                                          ↓
alert-service  ←──  fraudshield.fraud.detected  ←──────────────  (fraud rules applied)
```

### Microservices

| Service | Port | Responsibility |
|---|---|---|
| `fraudshield-transaction-service` | 8081 | Accepts transactions, saves to DB, publishes to Kafka |
| `fraudshield-fraud-detection-service` | 8082 | Kafka Streams — applies fraud rules, publishes alerts |
| `fraudshield-alert-service` | 8083 | Consumes fraud alerts and triggers notifications |

---

## Tech Stack

- **Java 17** + **Spring Boot 3**
- **Apache Kafka** + **Kafka Streams**
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **OpenAPI (Contract First)**
- **Lombok**

---

## Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- Maven

### 1. Start Infrastructure
```bash
cd development
docker-compose up -d
```

This starts:
- Kafka + Zookeeper
- Kafka UI → http://localhost:8080
- PostgreSQL

### 2. Create Kafka Topics
```bash
docker logs kafka-init
```
Topics created automatically on startup:
- `fraudshield.transactions.created` — 3 partitions
- `fraudshield.fraud.detected` — 3 partitions

### 3. Run Microservices

Start each service in order from IntelliJ or terminal:

```bash
# Terminal 1
cd fraudshield-transaction-service && mvn spring-boot:run

# Terminal 2
cd fraudshield-fraud-detection-service && mvn spring-boot:run

# Terminal 3
cd fraudshield-alert-service && mvn spring-boot:run
```

---

## API Usage

### Create Transaction
```http
POST http://localhost:8081/api/v1/transactions
Content-Type: application/json

{
  "transactionId": "txn_1001",
  "userId": 12345,
  "amount": 49999.99,
  "currency": "INR",
  "location": "IN",
  "timestamp": 1714550400000
}
```

### Response
```json
{
  "transactionId": "txn_1001",
  "status": "ACCEPTED",
  "message": "Transaction accepted for fraud processing"
}
```

---

## Fraud Detection Rules

| Rule | Condition |
|---|---|
| High Amount | `amount > 10,000` |
| Suspicious Location | `location == "OTHER"` |

---

## Project Structure

```
FraudShield/
├── development/
│   ├── docker-compose.yml
│   └── init-scripts/
│       └── create-topics.sh
├── fraudshield-api-contract/         ← OpenAPI spec (contract first)
├── fraudshield-transaction-service/  ← Producer
├── fraudshield-fraud-detection-service/ ← Kafka Streams processor
└── fraudshield-alert-service/        ← Consumer
```

---

## Monitoring

| Tool | URL |
|---|---|
| Kafka UI | http://localhost:8080 |
| Swagger UI | http://localhost:8081/swagger-ui.html |

---

## Environment Variables

| Variable | Default | Description |
|---|---|---|
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Kafka broker address |
| `POSTGRES_DB` | `fraudshield` | Database name |
| `POSTGRES_USER` | `user` | Database user |
| `POSTGRES_PASSWORD` | `root` | Database password |

---

## License
MIT
