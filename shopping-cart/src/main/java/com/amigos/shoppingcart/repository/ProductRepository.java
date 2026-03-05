package com.amigos.shoppingcart.repository;

import com.amigos.shoppingcart.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//public interface ProductRepository extends JpaRepository<Product, Long> {
//}

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategoryId(Byte categoryId);

    @Query("SELECT p FROM Product p")
    @EntityGraph(attributePaths = "category")
    List<Product> findAllWithCategory();
}
