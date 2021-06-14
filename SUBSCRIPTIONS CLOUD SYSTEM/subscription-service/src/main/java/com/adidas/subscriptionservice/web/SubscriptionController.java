package com.adidas.subscriptionservice.web;

import javax.validation.Valid;

import com.adidas.subscriptionservice.domain.Subscription;
import com.adidas.subscriptionservice.domain.SubscriptionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public Iterable<Subscription> get() {
        return subscriptionService.viewSubscriptionList();
    }

    @GetMapping("{email}")
    public Subscription getByEmail(@PathVariable String email) {
        System.out.println("Calling");
        return subscriptionService.viewSubscriptionDetails(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription post(@Valid @RequestBody Subscription subscription) {
        return subscriptionService.addSubscriptionToCatalog(subscription);
    }

    @DeleteMapping("{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String email) {
        subscriptionService.removeSubscriptionFromCatalog(email);
    }

    @PutMapping("{email}")
    public Subscription put(@PathVariable String email, @Valid @RequestBody Subscription subscription) {
        return subscriptionService.editSubscriptionDetails(email, subscription);
    }
}
