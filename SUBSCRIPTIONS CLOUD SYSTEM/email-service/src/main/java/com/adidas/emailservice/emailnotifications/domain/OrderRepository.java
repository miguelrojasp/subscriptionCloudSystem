package com.adidas.emailservice.emailnotifications.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository
		extends ReactiveCrudRepository<Emailnotifications,Long> {
}
