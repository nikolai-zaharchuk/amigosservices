package com.amigos.shoppingcart;

import com.amigos.shoppingcart.service.PaymentService;
import com.amigos.shoppingcart.service.StripePaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public PaymentService stripe() {
        return new StripePaymentService();
    }
}
