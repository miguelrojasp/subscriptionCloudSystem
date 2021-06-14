package com.adidas.subscriptionservice.web;

import java.time.Year;

import com.adidas.subscriptionservice.domain.Subscription;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class SubscriptionJsonTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<Subscription> json;

    @Test
    void testSerialize() throws Exception {
        Subscription subscription = new Subscription("mail@adidas.com", "FirstName", "Gender", Year.of(1973), 9.90, "Polar");
        assertThat(json.write(subscription)).extractingJsonPathStringValue("@.email")
                .isEqualTo("mail@adidas.com");
        assertThat(json.write(subscription)).extractingJsonPathStringValue("@.firstname")
                .isEqualTo("FirstName");
        assertThat(json.write(subscription)).extractingJsonPathStringValue("@.gender")
                .isEqualTo("Gender");
        assertThat(json.write(subscription)).extractingJsonPathStringValue("@.dateofbirth")
                .isEqualTo("1973");
        assertThat(json.write(subscription)).extractingJsonPathNumberValue("@.flagconsent")
                .isEqualTo(9.90);
        assertThat(json.write(subscription)).extractingJsonPathStringValue("@.idcampaign")
                .isEqualTo("Polar");
    }

    @Test
    void testDeserialize() throws Exception {
        String content = "{\"email\":\"mail@adidas.com\",\"firstname\":\"Firstname\", \"gender\":\"Gender\", \"dateofbirth\":\"1973\", \"flagconsent\":9.90, \"idcampaign\":\"Polar\"}";
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Subscription("mail@adidas.com", "Firstname", "Gender", Year.of(1973), 9.90, "Polar"));
        assertThat(json.parseObject(content).getEmail()).isEqualTo("mail@adidas.com");
    }
}
