package com.adidas.subscriptionservice.domain;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import com.adidas.subscriptionservice.persistence.JpaConfig;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubscriptionRepositoryJpaTests {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllOrderByTitle() {
        Subscription expectedSubscription1 = new Subscription("mail@adidas.com", "Title", "Author", Year.of(2000), 12.90, "Polar");
        Subscription expectedSubscription2 = new Subscription("mailg@adidas.com", "Another Title", "Author", Year.of(2000), 12.90, "Polar");
        entityManager.persist(expectedSubscription1);
        entityManager.persist(expectedSubscription2);

        Iterable<Subscription> actualSubscriptions = subscriptionRepository.findAll();

        assertThat(actualSubscriptions).asList().containsAll(List.of(expectedSubscription1, expectedSubscription2));
    }

    @Test
    void findSubscriptionByEmailWhenExisting() {
        String subscriptionEmail = "mail@adidas.com";
        Subscription expectedSubscription = new Subscription(subscriptionEmail, "Title", "Author", Year.of(2000), 12.90, "Polar");
        entityManager.persist(expectedSubscription);

        Optional<Subscription> actualSubscription = subscriptionRepository.findByEmail(subscriptionEmail);

        assertThat(actualSubscription).isPresent();
        assertThat(actualSubscription.get().getEmail()).isEqualTo(expectedSubscription.getEmail());
    }

    @Test
    void findSubscriptionByEmailWhenNotExisting() {
        Optional<Subscription> actualSubscription = subscriptionRepository.findByEmail("mmail@adidas.com");
        assertThat(actualSubscription).isEmpty();
    }

    @Test
    void existsByEmailWhenExisting() {
        String subscriptionEmail = "mailer@adidas.com";
        Subscription subscriptionToCreate = new Subscription(subscriptionEmail, "Title", "Author", Year.of(2000), 12.90, "Polar");
        entityManager.persist(subscriptionToCreate);

        boolean existing = subscriptionRepository.existsByEmail(subscriptionEmail);

        assertThat(existing).isTrue();
    }

    @Test
    void existsByEmailWhenNotExisting() {
        boolean existing = subscriptionRepository.existsByEmail("mailtop@adidas.com");
        assertThat(existing).isFalse();
    }

    @Test
    void deleteByEmail() {
        String subscriptionEmail = "maill@adidas.com";
        Subscription subscriptionToCreate = new Subscription(subscriptionEmail, "Title", "Author", Year.of(2000), 12.90, "Polar");
        Subscription persistedSubscription = entityManager.persist(subscriptionToCreate);

        subscriptionRepository.deleteByEmail(subscriptionEmail);

        assertThat(entityManager.find(Subscription.class, persistedSubscription.getId())).isNull();
    }
}