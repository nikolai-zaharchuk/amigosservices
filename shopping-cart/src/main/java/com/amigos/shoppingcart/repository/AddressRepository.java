package com.amigos.shoppingcart.repository;

import com.amigos.shoppingcart.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
