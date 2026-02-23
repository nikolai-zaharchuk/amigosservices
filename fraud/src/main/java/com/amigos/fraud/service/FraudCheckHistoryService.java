package com.amigos.fraud.service;

import com.amigos.fraud.dao.FraudCheckHistoryDao;
import com.amigos.fraud.dto.response.FraudCheckResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckHistoryService {
    private final FraudCheckHistoryDao fraudCheckHistoryDao;

    public FraudCheckHistoryService(FraudCheckHistoryDao fraudCheckHistoryDao) {
        this.fraudCheckHistoryDao = fraudCheckHistoryDao;
    }

    public FraudCheckResponse isFraudsterCustomer(Long customerId) {
        fraudCheckHistoryDao.create(customerId);


        return new FraudCheckResponse(false);
    }
}
