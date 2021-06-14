package com.adidas.subscriptionservice.demo;

import java.time.Year;
import java.util.List;

import com.adidas.subscriptionservice.domain.Subscription;
import com.adidas.subscriptionservice.domain.SubscriptionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("test-data")
@RequiredArgsConstructor
public class SubscriptionDataLoader {

	private final SubscriptionRepository subscriptionRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void loadSubscriptionTestData() {
		subscriptionRepository.deleteAll();
		Subscription subscription1 = new Subscription("subscripciones@adidas.com", "Miguel Angel", "Hombre", Year.of(1983), 1.0, "Campaña Competitiva");
		Subscription subscription2 = new Subscription("notificaciones@adidas.com", "Alexandra ", "Mujer", Year.of(1988), 2.0, "Campaña Formidable");
		subscriptionRepository.saveAll(List.of(subscription1, subscription2));
	}
}
