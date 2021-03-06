package com.adidas.edgeservice;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfiguration {

	@Bean
	public KeyResolver keyResolver() {
		return exchange -> Mono.just("ONE_BUCKET");
	}
}
