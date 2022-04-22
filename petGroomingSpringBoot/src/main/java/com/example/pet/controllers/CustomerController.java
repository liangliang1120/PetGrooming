package com.example.pet.controllers;
// import java.util.HashMap;
import java.util.List;

import com.example.pet.dto.MessageDetails;
import com.example.pet.models.Customer;
import com.example.pet.repositories.CustomerRepository;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @PostMapping("/customers")
    public ResponseEntity<MessageDetails> addCustomer(@RequestBody Customer customer) {
        customerRepository.addCustomer(customer);
        MessageDetails msg = new MessageDetails("The customer was added successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping("/customers")
    public ResponseEntity<MessageDetails> updateCustomer(@RequestBody Customer customer) {
        customerRepository.updateCustomer(customer);
        MessageDetails msg = new MessageDetails("The customer was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping("/customers/{cusId}")
    public ResponseEntity<MessageDetails> deleteCustomer(@PathVariable  int cusId) {
        customerRepository.deleteCustomer(cusId);
        MessageDetails msg = new MessageDetails("The Customer was delete successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }



}
