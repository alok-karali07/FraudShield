package com.example.service.messaging;

public interface EventPublisher<T> {

    void publish(T event);
}