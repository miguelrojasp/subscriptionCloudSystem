package com.adidas.subscriptionservice.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adidasbienvenida")
@Data
public class AdidasProperties {

	/**
	 * A message to welcome users.
	 */
	private String greeting;
}
