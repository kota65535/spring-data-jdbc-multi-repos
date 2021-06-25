package com.kota65535.repository.one;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1UserRepository extends CrudRepository<UserEntity, Integer> {
  
}
