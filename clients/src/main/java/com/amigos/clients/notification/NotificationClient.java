package com.amigos.clients.notification;

import com.amigos.clients.notification.dto.request.NotificationRequest;
import com.amigos.clients.notification.dto.response.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "notification", url = "${clients.notification.url}"
)
public interface NotificationClient {
    @PostMapping(path = "api/notification")
    NotificationResponse sendNotification(
            @RequestBody NotificationRequest notificationRequest
    );
}
