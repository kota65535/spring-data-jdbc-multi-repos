package com.kota65535.repository.one;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("users")
public class UserEntity {
  @Id
  private Integer id;
  
  private String name;
}
