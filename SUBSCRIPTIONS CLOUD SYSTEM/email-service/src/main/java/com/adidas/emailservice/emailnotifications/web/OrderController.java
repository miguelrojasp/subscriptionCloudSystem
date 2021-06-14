package com.adidas.emailservice.emailnotifications.web;

import javax.validation.Valid;

import com.adidas.emailservice.emailnotifications.domain.Emailnotifications;
import com.adidas.emailservice.emailnotifications.domain.OrderService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	public Flux<Emailnotifications> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("{id}")
	public Mono<Emailnotifications> getOrderById(@PathVariable Long id) {
		return orderService.getOrder(id);
	}

	@PostMapping
	public Mono<Emailnotifications> submitOrder(@RequestBody @Valid OrderRequest orderRequest) {
		return orderService.submitOrder(orderRequest.getIsbn(), orderRequest.getQuantity());
	}
}
