package com.amigos.shoppingcart.repository;

import com.amigos.shoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
