package com.adidas.emailservice.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
	private String email;
	private String firstname;
	private String gender;
	private Double flagconsent;
}
