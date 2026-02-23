package com.amigos.fraud.service.access;

import com.amigos.fraud.dao.FraudCheckHistoryDao;
import com.amigos.fraud.enity.FraudCheckHistory;
import com.amigos.fraud.enity.FraudStatus;
import com.amigos.fraud.repository.FraudCheckHistoryRepository;
import com.amigos.fraud.service.FraudCheckHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("jpa")
@AllArgsConstructor
public class FraudCheckHistoryJPAServiceAccess implements FraudCheckHistoryDao {
    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    @Override
    public void create(Long customerId) {
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .status(FraudStatus.NEW)
                        .build()
        );
    }
}
