package com.amigos.notification.controller;


import com.amigos.clients.notification.dto.request.NotificationRequest;
import com.amigos.clients.notification.dto.response.NotificationResponse;
import com.amigos.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping
    public NotificationResponse sendNotification(
            @RequestBody NotificationRequest notificationRequest
    ) {
        log.info("Send notification {}", notificationRequest);

        return notificationService.sendNotification(notificationRequest);
    }
}
