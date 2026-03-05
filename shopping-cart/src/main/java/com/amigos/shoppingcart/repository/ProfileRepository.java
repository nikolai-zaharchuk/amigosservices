package com.amigos.shoppingcart.repository;

import com.amigos.shoppingcart.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
