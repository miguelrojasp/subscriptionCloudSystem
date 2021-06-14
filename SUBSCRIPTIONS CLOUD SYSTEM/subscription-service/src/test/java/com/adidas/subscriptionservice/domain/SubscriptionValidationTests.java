package com.adidas.subscriptionservice.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        Subscription subscription = new Subscription("reply@adidas.com", "Title", "Author", Year.of(2000), 9.90, "Polar");
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenEmailNotDefinedThenValidationFails() {
        Subscription subscription = new Subscription("", "Title", "Author", Year.of(2000), 9.90, "Polar");
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages)
                .contains("The subscription EMAIL must be defined.")
                .contains("The EMAIL format must follow the standards EMAIL-10 or EMAIL-13.");
    }

    @Test
    void whenEmailDefinedButIncorrectThenValidationFails() {
        Subscription subscription = new Subscription("a234567890", "Title", "Author", Year.of(2000), 9.90, "Polar");
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The EMAIL format must follow the standards EMAIL-10 or EMAIL-13.");
    }



    @Test
    void whenPublishDateIsNotDefinedThenValidationSucceeds() {
        Subscription subscription = new Subscription("forward@adidas.com", "Title", "Author", null, 9.90, "Polar");
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenPublishDateIsInTheFutureThenValidationFails() {
        Subscription subscription = new Subscription("forwardto@adidas.com", "Title", "Author", Year.of(2050), 9.90, "Polar");
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The subscription cannot have been published in the future.");
    }

    @Test
    void whenFlagconsentIsNotDefinedThenValidationFails() {
        Subscription subscription = new Subscription("welcome@adidas.com", "Title", "Author", Year.of(2000), null, "Polar");
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The subscription flagconsent must be defined.");
    }


    @Test
    void whenPublisherIsNotDefinedThenValidationSucceeds() {
        Subscription subscription = new Subscription("welcome@adidas.com", "Title", "Author", null, 9.90, null);
        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);
        assertThat(violations).isEmpty();
    }
}
