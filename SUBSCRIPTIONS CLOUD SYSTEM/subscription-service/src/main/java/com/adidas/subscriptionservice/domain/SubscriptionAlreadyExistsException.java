package com.adidas.subscriptionservice.domain;

public class SubscriptionAlreadyExistsException extends RuntimeException {
    public SubscriptionAlreadyExistsException(String email) {
        super("A subscription with EMAIL " + email + " already exists.");
    }
}
