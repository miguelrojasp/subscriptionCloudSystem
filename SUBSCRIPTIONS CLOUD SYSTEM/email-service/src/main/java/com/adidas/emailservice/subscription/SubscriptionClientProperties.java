package com.adidas.emailservice.subscription;

import java.net.URI;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
@Data
public class SubscriptionClientProperties {

	/**
	 * The URL of the SubscriptionService application.
	 */
	@NotNull
	private URI subscriptionServiceUrl;
}
