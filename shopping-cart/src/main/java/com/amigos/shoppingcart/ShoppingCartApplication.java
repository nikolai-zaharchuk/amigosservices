package com.amigos.shoppingcart;

import com.amigos.shoppingcart.notification.NotificationManager;
import com.amigos.shoppingcart.service.OrderService;
import com.amigos.shoppingcart.service.PaYPalService;
import com.amigos.shoppingcart.service.PaymentService;
import com.amigos.shoppingcart.service.StripePaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShoppingCartApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ShoppingCartApplication.class, args);
        var manager = run.getBean(NotificationManager.class);

        manager.sendNotification("Test");
    }
}
