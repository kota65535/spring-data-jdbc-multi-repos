package com.kota65535;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.kota65535.controller.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UsersControllerTest {

  @LocalServerPort
  int port;
  RestClient client;

  @BeforeEach
  void beforeEach() {
    client = RestClient.builder()
        .baseUrl("http://localhost:%d".formatted(port))
        .build();
  }

  @Test
  void testGetUsers() {
    Users users = client.get()
        .uri("/users")
        .retrieve()
        .body(Users.class);
    assertThat(users.getUsers()).hasSize(4);
  }
}
