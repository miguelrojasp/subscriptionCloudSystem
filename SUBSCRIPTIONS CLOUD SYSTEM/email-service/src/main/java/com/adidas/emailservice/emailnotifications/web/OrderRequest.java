package com.adidas.emailservice.emailnotifications.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	@NotBlank(message = "The subscription ISBN must be defined.")
	private String isbn;

	@NotNull(message = "The subscription quantity must be defined.")
	@Min(value = 1, message = "You must emailnotifications at least 1 item.")
	@Max(value = 5, message = "You cannot emailnotifications more than 5 items.")
	private Integer quantity;
}
