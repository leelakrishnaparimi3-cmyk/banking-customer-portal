package com.banking.portal;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final List<Customer> customers = new ArrayList<>();

    public CustomerService() {
        customers.add(
                new Customer(1L, "John Doe", "john@example.com")
        );
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Customer registerCustomer(Customer customer) {
        long newId = customers.size() + 1L;

        customer.setId(newId);
        customers.add(customer);

        return customer;
    }
}