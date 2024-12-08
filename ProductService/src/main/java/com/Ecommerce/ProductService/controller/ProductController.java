package com.Ecommerce.ProductService.controller;


import com.Ecommerce.ProductService.dto.AddProductRequestDto;
import com.Ecommerce.ProductService.dto.ProductDto;
import com.Ecommerce.ProductService.exception.ProductCreationException;
import com.Ecommerce.ProductService.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        logger.debug("getAllBooks is started");
        Page<ProductDto> employeeDtoPage = productService.getAllProducts(page, size);
        return ResponseEntity.ok(employeeDtoPage.getContent());
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody AddProductRequestDto addEmployeeRequestDTO) {
        try {
            logger.debug("createProduct is started");
            Long productId = productService.createProduct(addEmployeeRequestDTO);
            logger.info("Product created successfully with ID: {}", productId);
            return new ResponseEntity<>(productId.toString(), HttpStatus.CREATED);
        } catch (ProductCreationException e) {
            logger.error("createProduct failed due to: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error during employee creation: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
