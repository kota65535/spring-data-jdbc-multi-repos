package com.kota65535.repository.three;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db3UserRepository extends CrudRepository<UserEntity, Integer> {

  List<UserEntity> findByNameContaining(String name, Pageable pageable);
}
