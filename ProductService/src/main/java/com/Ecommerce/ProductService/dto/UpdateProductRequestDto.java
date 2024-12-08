package com.Ecommerce.ProductService.dto;

import java.math.BigDecimal;

public record UpdateProductRequestDto(String name, String description, BigDecimal price, String currency, Integer quantityAvailable, Boolean isAvailable, String category) {
}
