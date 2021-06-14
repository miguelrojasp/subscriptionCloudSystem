package com.adidas.subscriptionservice.web;

import com.adidas.subscriptionservice.domain.SubscriptionNotFoundException;
import com.adidas.subscriptionservice.domain.SubscriptionService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SubscriptionControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    void whenGetSubscriptionNotExistingThenShouldReturn404() throws Exception {
        String email = "mail@adidas.com";
        given(subscriptionService.viewSubscriptionDetails(email)).willThrow(SubscriptionNotFoundException.class);
        mockMvc
                .perform(get("/subscriptions/" + email))
                .andExpect(status().isNotFound());
    }
}
