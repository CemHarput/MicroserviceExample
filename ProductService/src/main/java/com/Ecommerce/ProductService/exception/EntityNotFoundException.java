package com.Ecommerce.ProductService.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String s){
        super(s);
    }
}
