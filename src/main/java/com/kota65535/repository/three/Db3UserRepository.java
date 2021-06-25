package com.kota65535.repository.three;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db3UserRepository extends CrudRepository<UserEntity, Integer> {
  
}
