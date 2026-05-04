#!/bin/bash
set -e

echo "Waiting for Kafka to be ready..."

# Changed port to 29092
until kafka-topics --bootstrap-server kafka:29092 --list >/dev/null 2>&1; do
  echo "Kafka not ready yet..."
  sleep 2
done

echo "Kafka is ready!"

create_topic() {
  local topic=$1
  echo "Creating topic: $topic"

  # Changed port to 29092
  until kafka-topics --create \
    --bootstrap-server kafka:29092 \
    --topic "$topic" \
    --partitions 3 \
    --replication-factor 1 \
    --if-not-exists >/dev/null 2>&1; do

    echo "Retrying topic creation: $topic..."
    sleep 2
  done
}

create_topic "fraudshield.transactions.created"
create_topic "fraudshield.fraud.detected"

echo "Topics created successfully!"

# Changed port to 29092
kafka-topics --list --bootstrap-server kafka:29092