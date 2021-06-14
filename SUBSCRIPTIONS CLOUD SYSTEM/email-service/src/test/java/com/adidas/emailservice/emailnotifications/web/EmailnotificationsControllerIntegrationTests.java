package com.adidas.emailservice.emailnotifications.web;

import com.adidas.emailservice.subscription.Subscription;
import com.adidas.emailservice.subscription.BookClient;
import com.adidas.emailservice.emailnotifications.domain.Emailnotifications;
import com.adidas.emailservice.emailnotifications.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
class EmailnotificationsControllerIntegrationTests {

	@Container
	static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"));

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookClient bookClient;

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", EmailnotificationsControllerIntegrationTests::r2dbcUrl);
		registry.add("spring.r2dbc.username", postgresql::getUsername);
		registry.add("spring.r2dbc.password", postgresql::getPassword);

		registry.add("spring.flyway.url", postgresql::getJdbcUrl);
		registry.add("spring.flyway.user", postgresql::getUsername);
		registry.add("spring.flyway.password", postgresql::getPassword);
	}

	private static String r2dbcUrl() {
		return String.format("r2dbc:postgresql://%s:%s/%s", postgresql.getHost(),
				postgresql.getFirstMappedPort(), postgresql.getDatabaseName());
	}

	@Test
	void whenGetRequestWithIdThenOrderReturned() {
		String bookIsbn = "1234567893";
		Subscription subscription = new Subscription(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByEmail(bookIsbn)).willReturn(Mono.just(subscription));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 1);
		Emailnotifications expectedEmailnotifications = webTestClient.post().uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Emailnotifications.class).returnResult().getResponseBody();
		assertThat(expectedEmailnotifications).isNotNull();

		Emailnotifications fetchedEmailnotifications = webTestClient.get().uri("/orders/" + expectedEmailnotifications.getId())
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Emailnotifications.class).returnResult().getResponseBody();

		assertThat(fetchedEmailnotifications).isNotNull();
		assertThat(fetchedEmailnotifications).usingRecursiveComparison().isEqualTo(expectedEmailnotifications);
	}

	@Test
	void whenPostRequestAndBookExistsThenOrderAccepted() {
		String bookIsbn = "1234567899";
		Subscription subscription = new Subscription(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByEmail(bookIsbn)).willReturn(Mono.just(subscription));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		Emailnotifications createdEmailnotifications = webTestClient.post().uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Emailnotifications.class).returnResult().getResponseBody();

		assertThat(createdEmailnotifications).isNotNull();
		assertThat(createdEmailnotifications.getBookIsbn()).isEqualTo(orderRequest.getIsbn());
		assertThat(createdEmailnotifications.getQuantity()).isEqualTo(orderRequest.getQuantity());
		assertThat(createdEmailnotifications.getBookName()).isEqualTo(subscription.getTitle() + " - " + subscription.getAuthor());
		assertThat(createdEmailnotifications.getBookPrice()).isEqualTo(subscription.getPrice());
		assertThat(createdEmailnotifications.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
	}

	@Test
	void whenPostRequestAndBookNotExistsThenOrderRejected() {
		String bookIsbn = "1234567894";
		given(bookClient.getBookByEmail(bookIsbn)).willReturn(Mono.empty());
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		Emailnotifications createdEmailnotifications = webTestClient.post().uri("/orders")
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Emailnotifications.class).returnResult().getResponseBody();

		assertThat(createdEmailnotifications).isNotNull();
		assertThat(createdEmailnotifications.getBookIsbn()).isEqualTo(orderRequest.getIsbn());
		assertThat(createdEmailnotifications.getQuantity()).isEqualTo(orderRequest.getQuantity());
		assertThat(createdEmailnotifications.getStatus()).isEqualTo(OrderStatus.REJECTED);
	}
}
