package com.amigos.notification.service;

import com.amigos.clients.fraud.dto.request.NotificationRequest;
import com.amigos.clients.fraud.dto.response.NotificationResponse;
import com.amigos.notification.dao.NotificationDao;
import com.amigos.notification.entity.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationDao notificationDao;

    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        notificationDao.addNotification(
                Notification.builder()
                        .toCustomerId(notificationRequest.getToCustomerId())
                        .toCustomerEmail(notificationRequest.getToCustomerEmail())
                        .sender("Amigoscode")
                        .message(notificationRequest.getMessage())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())

                        .build()
        );


        return new NotificationResponse();
    }
}
