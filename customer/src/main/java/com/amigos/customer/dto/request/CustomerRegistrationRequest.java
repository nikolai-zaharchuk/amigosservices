package com.amigos.customer.dto.request;

import com.amigos.customer.entity.CustomerGender;
import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private Integer age;
    private CustomerGender gender;
    private String email;
    private String password;
}
