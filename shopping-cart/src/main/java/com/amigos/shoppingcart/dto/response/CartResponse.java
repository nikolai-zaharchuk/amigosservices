package com.amigos.shoppingcart.dto.response;

import com.amigos.shoppingcart.entity.CartItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class CartResponse {
    private UUID id;
    private List<CartItemResponse> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
