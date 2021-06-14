package com.adidas.subscriptionservice.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Year;
import java.util.Objects;

import com.adidas.subscriptionservice.domain.Subscription;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriptionControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetRequestWithIdThenSubscriptionReturned() {
        String subscriptionEmail = "mail@adidas.com";
        Subscription subscriptionToCreate = new Subscription(subscriptionEmail, "Title", "Author", Year.of(1991), 9.90, "Polar");
        Subscription expectedSubscription = restTemplate.postForEntity("/subscriptions", subscriptionToCreate, Subscription.class).getBody();

        ResponseEntity<Subscription> response = restTemplate.getForEntity("/subscriptions/" + subscriptionEmail, Subscription.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(expectedSubscription);
    }

    @Test
    void whenPostRequestThenSubscriptionCreated() {
        Subscription expectedSubscription = new Subscription("mail@adidas.com", "Title", "Author", Year.of(1991), 9.90, "Polar");

        ResponseEntity<Subscription> response = restTemplate.postForEntity("/subscriptions", expectedSubscription, Subscription.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo(expectedSubscription.getEmail());
    }

    @Test
    void whenPutRequestThenSubscriptionUpdated() throws UnsupportedEncodingException {
        String subscriptionEmail = "mail@adidas.com";

        String encodedString = URLEncoder.encode(subscriptionEmail, "UTF-8");

        Subscription subscriptionToCreate = new Subscription(encodedString, "Title", "Author", Year.of(1991), 9.90, "Polar");
        Subscription createdSubscription = restTemplate.postForEntity("/subscriptions", subscriptionToCreate, Subscription.class).getBody();
        Objects.requireNonNull(createdSubscription).setDateofbirth(Year.of(1990));

        restTemplate.put("/subscriptions/" + encodedString, createdSubscription);

        ResponseEntity<Subscription> response = restTemplate.getForEntity("/subscriptions/" + subscriptionEmail, Subscription.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDateofbirth()).isEqualTo(Year.of(1991));
    }

    @Test
    void whenDeleteRequestThenSubscriptionDeleted() throws UnsupportedEncodingException {
        String subscriptionEmail = "mail@adidas.com";

        String encodedString = URLEncoder.encode(subscriptionEmail, "UTF-8");
        Subscription subscriptionToCreate = new Subscription(encodedString, "Title", "Author", Year.of(1973), 9.90, "Polar");
        restTemplate.postForEntity("/subscriptions", subscriptionToCreate, Subscription.class);

        restTemplate.delete("/subscriptions/" + subscriptionEmail);

        ResponseEntity<String> response = restTemplate.getForEntity("/subscriptions/" + subscriptionEmail, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("The subscription with EMAIL " + subscriptionEmail + " was not found.");
    }
}
