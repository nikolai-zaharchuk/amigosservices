package com.amigos.shoppingcart.exception;

public class CartProductNotFoundException extends RuntimeException {
  public CartProductNotFoundException(String message) {
    super(message);
  }
}
