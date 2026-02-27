package com.amigos.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

@Data
@Entity
@Table(name = "addresses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    private Long id;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String zip;

    @Column
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude //!!! Important
    private User user;

}
