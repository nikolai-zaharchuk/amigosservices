package com.amigos.shoppingcart.config;

import com.amigos.shoppingcart.filters.JwtAuthenticationFilter;
import com.amigos.shoppingcart.service.CustomerDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final CustomerDetailsService customerDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Stateless sessions (token-based authentification)
        // Disable CSRF
        // Authorize
        httpSecurity
                .sessionManagement(c -> {
                    c.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.POST,  "/auth/login").permitAll()
                        //.requestMatchers(HttpMethod.POST,  "/auth/validate").permitAll()
                        .requestMatchers("/carts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider(customerDetailsService);

        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
