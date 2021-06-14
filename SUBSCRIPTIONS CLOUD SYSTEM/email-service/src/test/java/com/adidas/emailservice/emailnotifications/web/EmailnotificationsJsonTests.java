package com.adidas.emailservice.emailnotifications.web;

import com.adidas.emailservice.emailnotifications.domain.Emailnotifications;
import com.adidas.emailservice.emailnotifications.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EmailnotificationsJsonTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<Emailnotifications> json;

    @Test
    void testSerialize() throws Exception {
        Emailnotifications emailnotifications = new Emailnotifications("1234567890", "Subscription Name", 9.90, 1, OrderStatus.ACCEPTED);
        assertThat(json.write(emailnotifications)).extractingJsonPathStringValue("@.bookIsbn")
                .isEqualTo("1234567890");
        assertThat(json.write(emailnotifications)).extractingJsonPathStringValue("@.bookName")
                .isEqualTo("Subscription Name");
        assertThat(json.write(emailnotifications)).extractingJsonPathNumberValue("@.bookPrice")
                .isEqualTo(9.90);
        assertThat(json.write(emailnotifications)).extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(1);
        assertThat(json.write(emailnotifications)).extractingJsonPathStringValue("@.status")
                .isEqualTo("ACCEPTED");
    }
}
