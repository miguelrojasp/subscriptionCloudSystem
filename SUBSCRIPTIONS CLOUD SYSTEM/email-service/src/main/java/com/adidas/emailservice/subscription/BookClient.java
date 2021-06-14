package com.adidas.emailservice.subscription;

import java.time.Duration;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class BookClient {

	private final WebClient webClient;

	public BookClient(SubscriptionClientProperties subscriptionClientProperties, WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.baseUrl(subscriptionClientProperties.getSubscriptionServiceUrl().toString())
				.build();
	}

	public Mono<Subscription> getBookByEmail(String isbn) {
		return webClient.get().uri(isbn)
				.retrieve()
				.bodyToMono(Subscription.class)
				.timeout(Duration.ofSeconds(1), Mono.empty())
				.onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
				.retryWhen(Retry.backoff(3, Duration.ofMillis(100)));
	}
}
