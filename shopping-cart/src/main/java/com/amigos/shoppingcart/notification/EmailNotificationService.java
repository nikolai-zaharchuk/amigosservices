package com.amigos.shoppingcart.notification;

public class EmailNotificationService implements NotificationService{
    @Override
    public void send(String message) {
        System.out.println("Send by email");
    }
}
