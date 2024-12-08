package com.Ecommerce.ProductService.repository;

import com.Ecommerce.ProductService.enums.EventStatus;
import com.Ecommerce.ProductService.model.OutboxEvent;
import com.Ecommerce.ProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<OutboxEvent> findAllByStatus(EventStatus status);
}
