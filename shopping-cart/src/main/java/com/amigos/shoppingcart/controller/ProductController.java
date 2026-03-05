package com.amigos.shoppingcart.controller;

import com.amigos.shoppingcart.dto.response.ProductResponse;
import com.amigos.shoppingcart.entity.Product;
import com.amigos.shoppingcart.repository.ProductRepository;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    @GetMapping
    ResponseEntity<List<ProductResponse>> getProductLis(
            @PathParam("categoryId") Byte categoryId
    ) {
        List<Product> products = new ArrayList<>();

        if (categoryId != null) {
            products = productRepository.findProductsByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }


        return ResponseEntity.ok(products.stream()
                .map(product -> {
                    return ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .categoryId(product.getCategory().getId())
                            .build();
                })
                .toList());
    }


    @GetMapping("/{productId}")
    ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ProductResponse.builder()
                        .id(productId)
                        .name(product.getName())
                        .description(product.getName())
                        .price(product.getPrice())
                        .categoryId(product.getCategory().getId())
                .build());
    }
}
