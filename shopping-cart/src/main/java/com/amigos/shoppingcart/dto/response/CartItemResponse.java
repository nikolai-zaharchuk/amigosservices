package com.amigos.shoppingcart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CartItemResponse {
    private CartProductResponse product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
