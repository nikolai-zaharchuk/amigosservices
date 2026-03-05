package com.amigos.shoppingcart.controller;

import com.amigos.shoppingcart.dto.response.UserResponse;
import com.amigos.shoppingcart.entity.User;
import com.amigos.shoppingcart.mapper.UserMapper;
import com.amigos.shoppingcart.repository.UserRepository;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping()
    public List<UserResponse> getUserList(
            @RequestParam(name = "sort", required = false) String sort
    ) {
        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }

        System.out.println(sort);

        List<User> users = userRepository.findAll(Sort.by(sort));
        List<UserResponse> userResponses = new ArrayList<>();

        users.forEach(user -> {
            userResponses.add(
                    UserResponse.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .id(user.getId())
                    .build());
        });


        return userResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable(name = "id") Long id
    ) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();

        System.out.println(userResponse);

        return ResponseEntity.ok(userResponse);
    }
}
