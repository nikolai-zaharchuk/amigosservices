package com.amigos.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String gender;
}
