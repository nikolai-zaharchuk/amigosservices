package com.amigos.customer;

import com.amigos.amqp.config.RabbitMQMessageProducer;
import com.amigos.notification.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.amigos.customer",
                "com.amigos.amqp",
        }
)
@EnableFeignClients(
        basePackages = "com.amigos.clients"
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

