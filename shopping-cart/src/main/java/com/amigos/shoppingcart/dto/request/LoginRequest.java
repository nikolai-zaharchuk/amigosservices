package com.amigos.shoppingcart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
