package com.amigos.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @SequenceGenerator(
            name = "notification_id_sequence",
            sequenceName = "notification_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_id_sequence"
    )
    Long id;

    @Column
    Long toCustomerId;

    @Column
    String toCustomerEmail;

    @Column
    String sender;

    @Column
    String message;

    @Column
    LocalDateTime createdAt;

    @Column
    LocalDateTime updatedAt;
}
