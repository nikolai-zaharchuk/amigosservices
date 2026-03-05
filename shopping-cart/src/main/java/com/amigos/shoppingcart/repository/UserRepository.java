package com.amigos.shoppingcart.repository;

import com.amigos.shoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
