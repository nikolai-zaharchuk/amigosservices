package com.amigos.fraud.controller;

import com.amigos.fraud.dto.response.FraudCheckResponse;
import com.amigos.fraud.service.FraudCheckHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fraud-check")
@AllArgsConstructor
public class FraudController {
    private final FraudCheckHistoryService fraudCheckHistoryService;

    @PostMapping(path = "/{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Long customerId
    ) {
        fraudCheckHistoryService.isFraudsterCustomer(customerId);

        return new FraudCheckResponse();
    }

}
