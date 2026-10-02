package com.banking.portal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerPortalApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void applicationStarts() {
        assertNotNull(restTemplate);
    }

    @Test
    void healthEndpointReturnsUp() {
        String response = restTemplate.getForObject(
                "/health",
                String.class
        );

        assertEquals("UP", response);
    }

    @Test
    void customersEndpointReturnsData() {
        String response = restTemplate.getForObject(
                "/api/customers",
                String.class
        );

        assertNotNull(response);
        assertTrue(response.contains("John Doe"));
    }
}