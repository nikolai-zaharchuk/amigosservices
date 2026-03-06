package com.amigos.customer.controller;

import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.dto.request.CustomerUpdateRequest;
import com.amigos.customer.dto.response.CustomerResponse;
import com.amigos.customer.entity.Customer;
import com.amigos.customer.service.CustomerService;
import com.amigos.customer.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest
    ) {
        log.info("New customer registration request {}", customerRegistrationRequest);
        Customer customer = customerService.register(customerRegistrationRequest);
        var token = jwtService.generateToken(customer.getId().toString(), "ROLE_USER");

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.getCustomers();
    }

    @DeleteMapping
    public void truncate() {
        customerService.truncate();
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(
            @PathVariable("customerId") Long customerId
    ) {
        log.info("Delete customer request {}", customerId);

        customerService.deleteCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest
    ) {
        log.info("Update customer request {} {}", customerId, customerUpdateRequest);

        customerService.updateCustomer(customerId, customerUpdateRequest);
    }
}
