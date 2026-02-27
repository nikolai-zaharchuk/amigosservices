package com.amigos.clients.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NotificationRequest {
    private Long toCustomerId;
    private String toCustomerEmail;
    private String message;
}
