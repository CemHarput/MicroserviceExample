package com.Ecommerce.ProductService.service;


import com.Ecommerce.ProductService.dto.AddProductRequestDto;
import com.Ecommerce.ProductService.dto.ProductDto;
import com.Ecommerce.ProductService.dto.UpdateProductRequestDto;
import com.Ecommerce.ProductService.enums.EventStatus;
import com.Ecommerce.ProductService.enums.EventType;
import com.Ecommerce.ProductService.exception.EntityNotFoundException;
import com.Ecommerce.ProductService.model.OutboxEvent;
import com.Ecommerce.ProductService.model.Product;
import com.Ecommerce.ProductService.repository.OutboxEventRepository;
import com.Ecommerce.ProductService.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final OutboxEventRepository outboxEventRepository;

    public ProductService(ProductRepository productRepository, OutboxEventRepository outboxEventRepository) {
        this.productRepository = productRepository;
        this.outboxEventRepository = outboxEventRepository;
    }


    public Page<ProductDto> getAllProducts(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productsPage = productRepository.findAll(pageable);

        List<ProductDto> productDtos = productsPage.getContent().stream()
                .map(ProductDto::convertFromProduct)
                .toList();

        return new PageImpl<>(productDtos, pageable, productsPage.getTotalElements());
    }

    public Long createProduct(AddProductRequestDto addProductRequestDTO) {
        Product product = new Product();
        mapDtoToProduct(addProductRequestDTO,product);
        productRepository.save(product);

        OutboxEvent event = new OutboxEvent();
        event.setEventType(EventType.PRODUCT_CREATED);
        event.setPayload(product.getId().toString());
        event.setStatus(EventStatus.NEW);
        outboxEventRepository.save(event);

        return product.getId();
    }
    private void mapDtoToProduct(AddProductRequestDto dto, Product product) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCurrency(dto.currency());
        product.setQuantityAvailable(dto.quantityAvailable());
        product.setAvailable(dto.isAvailable());
        product.setCategory(dto.category());
    }
    private void mapUpdateDtoToProduct(UpdateProductRequestDto dto, Product product) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setCurrency(dto.currency());
        product.setQuantityAvailable(dto.quantityAvailable());
        product.setAvailable(dto.isAvailable());
        product.setCategory(dto.category());
    }
    private Product findEmployeeById(Long productId)  {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + productId));
    }

    public Long updateEmployee(Long employeeId, UpdateProductRequestDto updateEmployeeDTO) {
        Product existingEmployee = findEmployeeById(employeeId);
        mapUpdateDtoToProduct(updateEmployeeDTO, existingEmployee);
        productRepository.save(existingEmployee);
        return existingEmployee.getId();
    }

}
