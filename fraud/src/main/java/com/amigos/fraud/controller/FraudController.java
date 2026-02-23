package com.amigos.fraud.controller;

import com.amigos.fraud.dto.response.FraudCheckResponse;
import com.amigos.fraud.service.FraudCheckHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/fraud-check")
@AllArgsConstructor
public class FraudController {
    private final FraudCheckHistoryService fraudCheckHistoryService;

    @GetMapping(path = "/{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Long customerId
    ) {
        log.info("Check fraud request {}", customerId);

        return fraudCheckHistoryService.isFraudsterCustomer(customerId);
    }

}
