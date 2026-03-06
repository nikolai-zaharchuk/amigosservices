package com.amigos.shoppingcart.controller;

import com.amigos.shoppingcart.dto.request.AddItemToCartRequest;
import com.amigos.shoppingcart.dto.request.UpdateItemRequest;
import com.amigos.shoppingcart.dto.response.CartItemResponse;
import com.amigos.shoppingcart.dto.response.CartProductResponse;
import com.amigos.shoppingcart.dto.response.CartResponse;
import com.amigos.shoppingcart.entity.Cart;

import com.amigos.shoppingcart.entity.CartItem;
import com.amigos.shoppingcart.entity.Product;
import com.amigos.shoppingcart.exception.CartNotFoundException;
import com.amigos.shoppingcart.exception.CartProductNotFoundException;
import com.amigos.shoppingcart.exception.ProductNotFoundException;
import com.amigos.shoppingcart.repository.CartRepository;
import com.amigos.shoppingcart.repository.ProductRepository;
import com.amigos.shoppingcart.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var cartResponse = cartService.createCart();
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(cartResponse);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemResponse> addToCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddItemToCartRequest addItemToCartRequest
    ) {
        return ResponseEntity.ok(cartService.addItemToCart(cartId, addItemToCartRequest));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable UUID cartId
    ) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @PutMapping("/{cartId}/items/{productId}")
    ResponseEntity<?> updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateItemRequest updateItemRequest
    ) {
        return ResponseEntity.ok(cartService.updateCartItem(cartId, productId, updateItemRequest));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    ResponseEntity<?> removeCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ) {
        cartService.removeCartItem(cartId, productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{cartId}/items")
    ResponseEntity<?> clearCart(
            @PathVariable UUID cartId
    ) {
        cartService.clearCart(cartId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




    /*
        Exception block
     */
    @ExceptionHandler(CartProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> cartProductNotFound(
            CartProductNotFoundException errors
    ) {
        System.out.println(1111);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", errors.getMessage()));
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> productNotFound(
            ProductNotFoundException errors
    ) {
        System.out.println(3333);
        return ResponseEntity.badRequest().body(Map.of("error", errors.getMessage()));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> cartNotFound(
            CartNotFoundException errors
    ) {
        System.out.println(2222);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", errors.getMessage()));
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {

        var errors = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
