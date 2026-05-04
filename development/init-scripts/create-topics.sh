#!/bin/bash

set -e

echo "Waiting for Kafka to be ready..."

while ! kafka-topics --bootstrap-server kafka:9092 --list > /dev/null 2>&1; do
  echo "Kafka not ready yet..."
  sleep 2
done

echo "Kafka is ready!"

# Topic 1: Transaction events
kafka-topics --create \
  --bootstrap-server kafka:9092 \
  --topic fraudshield.transactions.created \
  --partitions 3 \
  --replication-factor 1 \
  --if-not-exists

# Topic 2: Fraud detection events
kafka-topics --create \
  --bootstrap-server kafka:9092 \
  --topic fraudshield.fraud.detected \
  --partitions 3 \
  --replication-factor 1 \
  --if-not-exists

echo "Topics created successfully!"

kafka-topics --list --bootstrap-server kafka:9092