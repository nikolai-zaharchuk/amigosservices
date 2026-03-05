package com.amigos.shoppingcart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private  String description;
    private BigDecimal price;
    private Byte categoryId;
}
