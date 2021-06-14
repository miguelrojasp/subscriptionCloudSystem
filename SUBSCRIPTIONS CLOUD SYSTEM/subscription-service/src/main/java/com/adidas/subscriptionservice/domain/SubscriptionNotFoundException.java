package com.adidas.subscriptionservice.domain;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String email) {
        super("The subscription with EMAIL " + email + " was not found.");
    }
}
