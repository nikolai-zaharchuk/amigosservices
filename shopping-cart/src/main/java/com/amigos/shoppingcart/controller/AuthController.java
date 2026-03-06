package com.amigos.shoppingcart.controller;

import com.amigos.shoppingcart.dto.request.LoginRequest;
import com.amigos.shoppingcart.dto.response.JwtResponse;
import com.amigos.shoppingcart.dto.response.UserResponse;
import com.amigos.shoppingcart.repository.UserRepository;
import com.amigos.shoppingcart.service.JwtService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.annotation.HandlesTypes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        var token = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validate")
    public boolean validateToken(
            @RequestHeader("Authorization") String authHeader
    ) {
        System.out.println("Validate called");


        var token = authHeader.replace("Bearer ", "");

        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) authentication.getPrincipal();
        var user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var userResponse = UserResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();

        return ResponseEntity.ok(userResponse);
    }







    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
