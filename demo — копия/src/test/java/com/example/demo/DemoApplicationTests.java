package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final GenericContainer<?> devContainer = new GenericContainer<>("devapp:latest").withExposedPorts(8080);
    private static final GenericContainer<?> prodContainer = new GenericContainer<>("prodapp:latest").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devContainer.start();
        prodContainer.start();
    }

    @Test
    void testDevApp() {
        Integer devPort = devContainer.getMappedPort(8080);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + devPort + "/profile", String.class);
        System.out.println("Dev Response: " + response.getBody());
        Assertions.assertEquals("Current profile is dev", response.getBody());
    }

    @Test
    void testProdApp() {
        Integer prodPort = prodContainer.getMappedPort(8081);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + prodPort + "/profile", String.class);
        System.out.println("Prod Response: " + response.getBody());
        Assertions.assertEquals("Current profile is production", response.getBody());
    }
}