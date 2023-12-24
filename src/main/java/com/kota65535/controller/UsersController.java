package com.kota65535.controller;

import com.kota65535.repository.one.Db1UserRepository;
import com.kota65535.repository.one.UserEntity;
import com.kota65535.repository.two.Db2UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

  private final Db1UserRepository repository1;

  private final Db2UserRepository repository2;

  public UsersController(Db1UserRepository repository1, Db2UserRepository repository2) {
    this.repository1 = repository1;
    this.repository2 = repository2;
  }

  @GetMapping(
      value = "/users",
      produces = {"application/json"}
  )
  public ResponseEntity<Users> getUsers(@RequestParam(required = false) String name) {
    List<User> users = new ArrayList<>();
    users.addAll(getUsers1(name));
    users.addAll(getUsers2(name));
    return ResponseEntity.ok(new Users(users));
  }

  private List<User> getUsers1(String name) {
    if (name == null) {
      return StreamSupport.stream(repository1.findAll().spliterator(), false).map(this::toUser).toList();
    } else {
      return repository1.findByNameContaining(name).stream().map(this::toUser).toList();
    }
  }

  private List<User> getUsers2(String name) {
    if (name == null) {
      return StreamSupport.stream(repository2.findAll().spliterator(), false).map(this::toUser).toList();
    } else {
      return repository2.findByNameContaining(name).stream().map(this::toUser).toList();
    }
  }

  private User toUser(UserEntity entity) {
    return new User(entity.getName());
  }

  private User toUser(com.kota65535.repository.two.UserEntity entity) {
    return new User(entity.getName());
  }
}
