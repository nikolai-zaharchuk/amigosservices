package com.amigos.clients.fraud;

import com.amigos.clients.fraud.dto.request.NotificationRequest;
import com.amigos.clients.fraud.dto.response.FraudCheckResponse;
import com.amigos.clients.fraud.dto.response.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
