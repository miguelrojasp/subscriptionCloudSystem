package com.adidas.emailservice.emailnotifications.domain;

import com.adidas.emailservice.emailnotifications.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.relational.core.mapping.Table;

@Table("emailnotifications")
@Data @AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Emailnotifications extends PersistableEntity {

	private String bookIsbn;
	private String bookName;
	private Double bookPrice;
	private Integer quantity;
	private OrderStatus status;

	public Emailnotifications(String bookIsbn, int quantity, OrderStatus status) {
		this.bookIsbn = bookIsbn;
		this.quantity = quantity;
		this.status = status;
	}
}
