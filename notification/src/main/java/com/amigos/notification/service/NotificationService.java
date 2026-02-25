package com.amigos.notification.service;

import com.amigos.clients.fraud.dto.request.NotificationRequest;
import com.amigos.clients.fraud.dto.response.NotificationResponse;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        return new NotificationResponse();
    }
}
