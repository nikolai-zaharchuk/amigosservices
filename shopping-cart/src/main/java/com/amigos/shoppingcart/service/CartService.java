package com.amigos.shoppingcart.service;

import com.amigos.shoppingcart.dto.request.UpdateItemRequest;
import com.amigos.shoppingcart.exception.CartNotFoundException;
import com.amigos.shoppingcart.dto.request.AddItemToCartRequest;
import com.amigos.shoppingcart.dto.response.CartItemResponse;
import com.amigos.shoppingcart.dto.response.CartProductResponse;
import com.amigos.shoppingcart.dto.response.CartResponse;
import com.amigos.shoppingcart.entity.Cart;
import com.amigos.shoppingcart.entity.CartItem;
import com.amigos.shoppingcart.entity.Product;
import com.amigos.shoppingcart.exception.CartProductNotFoundException;
import com.amigos.shoppingcart.exception.ProductNotFoundException;
import com.amigos.shoppingcart.repository.CartRepository;
import com.amigos.shoppingcart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartResponse createCart() {
        Cart cart = new Cart();
        cartRepository.saveAndFlush(cart);

        return CartResponse
                .builder()
                .id(cart.getId())
                .items(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
    }

    public CartItemResponse addItemToCart(UUID cartId, AddItemToCartRequest addItemToCartRequest) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        Product product = productRepository.findById(addItemToCartRequest.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        CartItem cartItem = cart.addItem(product);
        cartRepository.save(cart);

        return CartItemResponse
                .builder()
                .product(CartProductResponse
                        .builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .id(product.getId())
                        .build()
                )
                .quantity(cartItem.getQuantity())
                .totalPrice(product.getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .build();
    }

    public CartResponse getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartResponse cartResponse = CartResponse
                .builder()
                .id(cartId)
                .items(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        cart.getCartItems().forEach(cartItem -> {
            BigDecimal cartItemTotalPrice = cartItem
                    .getProduct()
                    .getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity()));

            CartItemResponse cartItemResponse = CartItemResponse
                    .builder()
                    .quantity(cartItem.getQuantity())
                    .product(CartProductResponse
                            .builder()
                            .id(cartItem.getProduct().getId())
                            .name(cartItem.getProduct().getName())
                            .price(cartItem.getProduct().getPrice())
                            .build()
                    )
                    .totalPrice(cartItemTotalPrice)
                    .build();

            cartResponse.getItems().add(cartItemResponse);
            cartResponse.setTotalPrice(cartResponse.getTotalPrice().add(cartItemTotalPrice));
        });

        return cartResponse;
    }

    public CartItemResponse updateCartItem(UUID cartId, Long productId, UpdateItemRequest updateItemRequest) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        CartItem cartItem = cart.getItem(productId);

        if (cartItem == null) {
            throw new CartProductNotFoundException("Can not found product in the cart");
        }

        cartItem.setQuantity(cartItem.getQuantity() + updateItemRequest.getQuantity());

        cartRepository.save(cart);

        return CartItemResponse
                .builder()
                .product(CartProductResponse
                        .builder()
                        .id(productId)
                        .name(product.getName())
                        .price(product.getPrice())
                        .build()
                )
                .quantity(cartItem.getQuantity())
                .totalPrice(product.getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .build();
    }


    public void removeCartItem (UUID cartId, Long productId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));

        cart.clear();
        cartRepository.save(cart);
    }
}
