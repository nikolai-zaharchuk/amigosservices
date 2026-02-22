package com.amigos.customer.controller;

import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.entity.Customer;
import com.amigos.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public void registerCustomer(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest
    ) {
        log.info("New customer registration request {}", customerRegistrationRequest);

        customerService.register(customerRegistrationRequest);
    }
}
