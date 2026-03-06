package com.amigos.shoppingcart.controller;

import com.amigos.shoppingcart.dto.request.AddItemToCartRequest;
import com.amigos.shoppingcart.dto.request.UpdateItemRequest;
import com.amigos.shoppingcart.dto.response.CartItemResponse;
import com.amigos.shoppingcart.dto.response.CartProductResponse;
import com.amigos.shoppingcart.dto.response.CartResponse;
import com.amigos.shoppingcart.entity.Cart;

import com.amigos.shoppingcart.entity.CartItem;
import com.amigos.shoppingcart.entity.Product;
import com.amigos.shoppingcart.repository.CartRepository;
import com.amigos.shoppingcart.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Cart cart = new Cart();
        cartRepository.saveAndFlush(cart);

        CartResponse cartResponse = CartResponse
                .builder()
                .id(cart.getId())
                .items(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(cartResponse);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemResponse> addToCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddItemToCartRequest addItemToCartRequest
    ) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(addItemToCartRequest.getProductId()).orElse(null);

        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        CartItem cartItem = cart.addItem(product);
        cartRepository.save(cart);

        CartItemResponse cartItemResponse = CartItemResponse
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

        return ResponseEntity.ok(cartItemResponse);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable UUID cartId
    ) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

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


        return ResponseEntity.ok(cartResponse);
    }

    @PutMapping("/{cartId}/items/{productId}")
    ResponseEntity<?> updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateItemRequest updateItemRequest
    ) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found")
            );
        }

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Product not found")
            );
        }

        CartItem cartItem = cart.getItem(productId);

        if (cartItem == null) {
            return ResponseEntity.badRequest().build();
        }

        cartItem.setQuantity(cartItem.getQuantity() + updateItemRequest.getQuantity());

        cartRepository.save(cart);

        CartItemResponse cartItemResponse = CartItemResponse
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

        return ResponseEntity.ok(cartItemResponse);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    ResponseEntity<?> removeCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found")
            );
        }

        cart.removeItem(productId);
        cartRepository.save(cart);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{cartId}/items")
    ResponseEntity<?> clearCart(
            @PathVariable UUID cartId
    ) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found")
            );
        }

        cart.clear();
        cartRepository.save(cart);


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

















    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {

        System.out.println("test");

        var errors = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
