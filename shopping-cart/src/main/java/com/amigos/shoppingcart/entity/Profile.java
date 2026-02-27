package com.amigos.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    private Long id;

    @Column
    private String bio;

    @Column
    private String phoneNumber;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private Integer loyaltyPoints;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;
}
