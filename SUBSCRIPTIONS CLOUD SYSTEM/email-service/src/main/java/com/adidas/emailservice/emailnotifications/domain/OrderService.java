package com.adidas.emailservice.emailnotifications.domain;

import com.adidas.emailservice.subscription.Subscription;
import com.adidas.emailservice.subscription.BookClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final BookClient bookClient;
	private final OrderRepository orderRepository;

	public Flux<Emailnotifications> getAllOrders() {
		return orderRepository.findAll();
	}

	public Mono<Emailnotifications> getOrder(Long id) {
		return orderRepository.findById(id);
	}

	public Mono<Emailnotifications> submitOrder(String isbn, int quantity) {
		return bookClient.getBookByEmail(isbn)
				.flatMap(subscription -> Mono.just(buildAcceptedOrder(subscription, quantity)))
				.defaultIfEmpty(buildRejectedOrder(isbn, quantity))
				.flatMap(orderRepository::save);
	}

	private Emailnotifications buildAcceptedOrder(Subscription subscription, int quantity) {
		return new Emailnotifications(subscription.getEmail(),
				subscription.getFirstname() + " - " + subscription.getGender(),
				subscription.getFlagconsent(),
				quantity,
				OrderStatus.ACCEPTED);
	}

	private Emailnotifications buildRejectedOrder(String isbn, int quantity) {
		return new Emailnotifications(isbn, quantity, OrderStatus.REJECTED);
	}
}
