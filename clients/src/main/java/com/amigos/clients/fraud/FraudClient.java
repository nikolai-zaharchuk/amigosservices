package com.amigos.clients.fraud;

import com.amigos.clients.fraud.dto.response.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    value = "fraud", url = "${clients.fraud.url}"
)
public interface FraudClient {
    @GetMapping(path = "api/fraud-check/{customerId}")
    FraudCheckResponse isFraudster(
           @PathVariable("customerId") Long customerId
    );
}
