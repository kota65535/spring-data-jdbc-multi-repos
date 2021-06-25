package com.kota65535.controller;

import com.kota65535.repository.one.Db1UserRepository;
import com.kota65535.repository.one.UserEntity;
import com.kota65535.repository.two.Db2UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
  
  @Autowired
  private Db1UserRepository repository1;

  @Autowired
  private Db2UserRepository repository2;

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
}
