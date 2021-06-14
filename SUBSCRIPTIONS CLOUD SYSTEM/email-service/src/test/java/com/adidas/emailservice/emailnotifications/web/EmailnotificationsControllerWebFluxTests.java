package com.adidas.emailservice.emailnotifications.web;

import com.adidas.emailservice.emailnotifications.domain.Emailnotifications;
import com.adidas.emailservice.emailnotifications.domain.OrderService;
import com.adidas.emailservice.emailnotifications.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebFluxTest(OrderController.class)
class EmailnotificationsControllerWebFluxTests {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	private OrderService orderService;

	@Test
	void whenBookNotAvailableThenRejectOrder() {
		OrderRequest orderRequest = new OrderRequest("1234567890", 3);
		Emailnotifications expectedEmailnotifications = new Emailnotifications(orderRequest.getIsbn(), orderRequest.getQuantity(),
				OrderStatus.REJECTED);
		given(orderService.submitOrder(orderRequest.getIsbn(), orderRequest.getQuantity()))
				.willReturn(Mono.just(expectedEmailnotifications));

		Emailnotifications createdEmailnotifications = webClient.post().uri("/orders/")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Emailnotifications.class).returnResult().getResponseBody();

		assertThat(createdEmailnotifications).isNotNull();
		assertThat(createdEmailnotifications.getStatus()).isEqualTo(OrderStatus.REJECTED);
	}
}
