package com.kota65535.controller;

import com.kota65535.repository.one.Db1UserRepository;
import com.kota65535.repository.three.Db3UserRepository;
import com.kota65535.repository.two.Db2UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
  
  private final Db1UserRepository repository1;

  private final Db2UserRepository repository2;

  private final Db3UserRepository repository3;

  public UsersController(Db1UserRepository repository1, Db2UserRepository repository2, Db3UserRepository repository3) {
    this.repository1 = repository1;
    this.repository2 = repository2;
    this.repository3 = repository3;
  }

  @GetMapping(
      value = "/users1",
      produces = { "application/json" }
  )
  public ResponseEntity<User> getUser1() {
    User u = repository1.findById(1)
        .map(e -> new User((e.getName())))
        .orElseThrow(RuntimeException::new);
    return ResponseEntity.ok(u);
  }

  @GetMapping(
      value = "/users2",
      produces = { "application/json" }
  )
  public ResponseEntity<User> getUser2() {
    User u = repository2.findById(1)
        .map(e -> new User((e.getName())))
        .orElseThrow(RuntimeException::new);
    return ResponseEntity.ok(u);
  }

  @GetMapping(
      value = "/users3",
      produces = { "application/json" }
  )
  public ResponseEntity<User> getUser3() {
    User u = repository3.findById(1)
        .map(e -> new User((e.getName())))
        .orElseThrow(RuntimeException::new);
    return ResponseEntity.ok(u);
  }
}
