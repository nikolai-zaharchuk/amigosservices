package com.amigos.customer.dto.request;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
