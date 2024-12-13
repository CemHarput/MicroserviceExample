package com.Ecommerce.CustomerService.dto;

import com.Ecommerce.CustomerService.model.Customer;

public record CustomerDto(Long id, String name, String email) {

    public static CustomerDto convertFromCustomer(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}
