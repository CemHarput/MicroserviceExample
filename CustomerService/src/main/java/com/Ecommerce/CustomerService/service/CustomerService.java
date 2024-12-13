package com.Ecommerce.CustomerService.service;


import com.Ecommerce.CustomerService.dto.CustomerDto;
import com.Ecommerce.CustomerService.model.Customer;
import com.Ecommerce.CustomerService.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerDto> getAllCustomers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customersPage = customerRepository.findAll(pageable);

        List<CustomerDto> customerDtos = customersPage.getContent().stream()
                .map(CustomerDto::convertFromCustomer)
                .toList();

        return new PageImpl<>(customerDtos, pageable, customersPage.getTotalElements());
    }

    public

}
