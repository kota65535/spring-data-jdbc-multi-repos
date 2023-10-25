package com.kota65535.repository.two;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db2UserRepository extends CrudRepository<UserEntity, Integer> {

  List<UserEntity> findByNameContaining(String name);
}
