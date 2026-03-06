package com.amigos.shoppingcart.controller;

import com.amigos.shoppingcart.dto.request.UserRegistrationRequest;
import com.amigos.shoppingcart.dto.response.UserResponse;
import com.amigos.shoppingcart.entity.User;
import com.amigos.shoppingcart.mapper.UserMapper;
import com.amigos.shoppingcart.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping()
    public List<UserResponse> getUserList(
            @RequestHeader(name = "x-auth-token", required = false) String authToken,
            @RequestParam(name = "sort", required = false, defaultValue = "sort") String sort
    ) {
        System.out.println(authToken);


        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }

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

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        if (userRepository.existsUserByEmail(userRegistrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email is already registered")
            );
        }

        User user = User.builder()
                .name(userRegistrationRequest.getName())
                .email(userRegistrationRequest.getEmail())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .build();

        userRepository.saveAndFlush(user);

        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build());
    }


}
