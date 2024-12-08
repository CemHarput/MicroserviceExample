package com.Ecommerce.ProductService.repository;

import com.Ecommerce.ProductService.enums.EventStatus;
import com.Ecommerce.ProductService.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findAllByStatus(EventStatus eventStatus);
}
