package com.Ecommerce.ProductService.dto;

import com.Ecommerce.ProductService.model.Product;

import java.math.BigDecimal;

public record ProductDto(String name, String description, BigDecimal price, String currency, Integer quantityAvailable, Boolean isAvailable, String category) {

    public static ProductDto convertFromProduct(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getQuantityAvailable(),
                product.getAvailable(),
                product.getCategory()
        );
    }
}
