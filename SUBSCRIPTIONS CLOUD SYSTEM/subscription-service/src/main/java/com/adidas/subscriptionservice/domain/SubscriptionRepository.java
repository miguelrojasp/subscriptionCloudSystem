package com.adidas.subscriptionservice.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SubscriptionRepository
		extends CrudRepository<Subscription,Long> {
	Optional<Subscription> findByEmail(String email);
	boolean existsByEmail(String email);
	@Transactional
	void deleteByEmail(String email);
}
