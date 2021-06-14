package com.adidas.subscriptionservice.domain;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public Iterable<Subscription> viewSubscriptionList() {
        return subscriptionRepository.findAll();
    }

    public Subscription viewSubscriptionDetails(String email) {
        return subscriptionRepository.findByEmail(email)
                .orElseThrow(() -> new SubscriptionNotFoundException(email));
    }

    public Subscription addSubscriptionToCatalog(Subscription subscription) {
        if (subscriptionRepository.existsByEmail(subscription.getEmail())) {
            throw new SubscriptionAlreadyExistsException(subscription.getEmail());
        }
        return subscriptionRepository.save(subscription);
    }

    public void removeSubscriptionFromCatalog(String email) {
        if (!subscriptionRepository.existsByEmail(email)) {
            throw new SubscriptionNotFoundException(email);
        }
        subscriptionRepository.deleteByEmail(email);
    }

    public Subscription editSubscriptionDetails(String email, Subscription subscription) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findByEmail(email);
        if (existingSubscription.isEmpty()) {
            return addSubscriptionToCatalog(subscription);
        }
        Subscription subscriptionToUpdate = existingSubscription.get();
        subscriptionToUpdate.setFirstname(subscription.getFirstname());
        subscriptionToUpdate.setGender(subscription.getGender());
        subscriptionToUpdate.setDateofbirth(subscription.getDateofbirth());
        subscriptionToUpdate.setFlagconsent(subscription.getFlagconsent());
        return subscriptionRepository.save(subscriptionToUpdate);
    }
}
