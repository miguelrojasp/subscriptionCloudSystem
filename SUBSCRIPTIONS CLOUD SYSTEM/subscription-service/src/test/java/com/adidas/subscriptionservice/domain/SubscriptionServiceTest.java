package com.adidas.subscriptionservice.domain;

import java.time.Year;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void whenSubscriptionToCreateAlreadyExistsThenThrows() {
        String subscriptionEmail = "welcome@adidas.com";
        Subscription subscriptionToCreate = new Subscription(subscriptionEmail, "Title", "Author", Year.of(2000), 9.90, "Polar");
        when(subscriptionRepository.existsByEmail(subscriptionEmail)).thenReturn(true);
        assertThatThrownBy(() -> subscriptionService.addSubscriptionToCatalog(subscriptionToCreate))
                .isInstanceOf(SubscriptionAlreadyExistsException.class)
                .hasMessage("A subscription with EMAIL " + subscriptionEmail + " already exists.");
    }

    @Test
    void whenSubscriptionToDeleteDoesNotExistThenThrows() {
        String subscriptionEmail = "welcome@adidas.com";
        when(subscriptionRepository.existsByEmail(subscriptionEmail)).thenReturn(false);
        assertThatThrownBy(() -> subscriptionService.removeSubscriptionFromCatalog(subscriptionEmail))
                .isInstanceOf(SubscriptionNotFoundException.class)
                .hasMessage("The subscription with EMAIL " + subscriptionEmail + " was not found.");
    }
}
