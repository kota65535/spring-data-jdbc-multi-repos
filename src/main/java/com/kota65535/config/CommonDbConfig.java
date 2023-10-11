package com.kota65535.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.dialect.AnsiDialect;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy;
import org.springframework.data.relational.core.mapping.NamingStrategy;

@Configuration
public class CommonDbConfig {

  @Bean
  public JdbcCustomConversions jdbcCustomConversions() {
    return new JdbcCustomConversions();
  }

  @Bean
  public Dialect jdbcDialect() {
    return AnsiDialect.INSTANCE;
  }

  @Bean
  public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy,
      JdbcCustomConversions customConversions) {
    JdbcMappingContext mappingContext = new JdbcMappingContext(
        namingStrategy.orElse(DefaultNamingStrategy.INSTANCE));
    mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
    return mappingContext;
  }
}
