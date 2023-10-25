package com.kota65535.repository.one;

import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1UserRepository extends CrudRepository<UserEntity, Integer> {

  Set<UserEntity> findByName(String name);

  List<UserEntity> findByNameContaining(String name);
}
