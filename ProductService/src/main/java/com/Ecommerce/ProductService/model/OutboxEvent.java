package com.Ecommerce.ProductService.model;

import com.Ecommerce.ProductService.enums.EventStatus;
import com.Ecommerce.ProductService.enums.EventType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String payload;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    private LocalDateTime createdAt = LocalDateTime.now();


    public OutboxEvent(UUID id, EventType eventType, String payload, EventStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.createdAt = createdAt;
    }

    public OutboxEvent() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutboxEvent that)) return false;
        return Objects.equals(getId(), that.getId()) && getEventType() == that.getEventType() && Objects.equals(getPayload(), that.getPayload()) && getStatus() == that.getStatus() && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEventType(), getPayload(), getStatus(), getCreatedAt());
    }
}
