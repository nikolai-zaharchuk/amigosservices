package com.amigos.shoppingcart.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    private final NotificationService notificationService;

    NotificationManager(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void sendNotification(String message) {
        System.out.println("Prepare send message");
        notificationService.send(message);
    }
}
