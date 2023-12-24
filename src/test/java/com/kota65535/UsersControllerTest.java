package com.kota65535;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.kota65535.controller.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UsersControllerTest {

  @LocalServerPort
  int port;
  RestTemplate client;

  @BeforeEach
  void beforeEach() {
    client = new RestTemplateBuilder()
        .rootUri("http://localhost:%d".formatted(port))
        .build();
  }

  @Test
  void testGetUsers() {
    Users users = client.getForObject("/users", Users.class);
    assertThat(users.getUsers()).hasSize(4);
  }
}
