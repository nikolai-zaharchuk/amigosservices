package com.amigos.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraudCheckResponse {
    private Boolean fraudster;

    public boolean isFraudster() {
        return fraudster;
    }
}
