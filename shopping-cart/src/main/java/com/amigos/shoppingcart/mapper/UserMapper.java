package com.amigos.shoppingcart.mapper;

import com.amigos.shoppingcart.dto.response.UserResponse;
import com.amigos.shoppingcart.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
