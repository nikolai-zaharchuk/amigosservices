package com.amigos.shoppingcart.service;

public class PaYPalService implements PaymentService{
    @Override
    public void processPayment(double amount) {
        System.out.println("PAYPAL");
        System.out.println("Amount " + amount + " paid.");
    }
}
