package com.amigos.customer.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString

public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
