package com.kota65535.config;


import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.BasicJdbcConverter;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJdbcRepositories(
    jdbcOperationsRef = "jdbcOperationsDb2",
    transactionManagerRef = "transactionManagerDb2",
    dataAccessStrategyRef = "dataAccessStrategyDb2",
    basePackages = {
        "com.kota65535.repository.two"
    }
)
@EnableAutoConfiguration(
    exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@Configuration
@ConfigurationPropertiesScan
public class Db2Config {

  @Bean
  @Qualifier("db2")
  public NamedParameterJdbcOperations jdbcOperationsDb2(
      @Qualifier("db2") DataSource dataSourceDb2
  ) {
    return new NamedParameterJdbcTemplate(dataSourceDb2);
  }

  @Bean
  @Qualifier("db2")
  public DataSource dataSourceDb2(
      @Qualifier("db2") DataSourceProperties propertiesDb2
  ) {
    return propertiesDb2.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @Qualifier("db2")
  @ConfigurationProperties(prefix = "spring.datasources.two")
  public DataSourceProperties dataSourcePropertiesDb2() {
    return new DataSourceProperties();
  }

  @Bean
  @Qualifier("db2")
  public JdbcConverter jdbcConverterDb2(
      JdbcMappingContext mappingContext,
      @Qualifier("db2") NamedParameterJdbcOperations jdbcOperationsDb2,
      @Lazy RelationResolver relationResolver,
      JdbcCustomConversions conversions, 
      Dialect dialect
  ) {
    DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(jdbcOperationsDb2.getJdbcOperations());
    return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
        dialect.getIdentifierProcessing());
  }

  @Bean
  @Qualifier("db2")
  public DataAccessStrategy dataAccessStrategyDb2(
      @Qualifier("db2") NamedParameterJdbcOperations operations,
      @Qualifier("db2") JdbcConverter jdbcConverter,
      JdbcMappingContext context,
      Dialect dialect) {
    return new DefaultDataAccessStrategy(new SqlGeneratorSource(context, jdbcConverter, dialect), context, jdbcConverter, operations);
  }
  
  @Bean
  @Qualifier("db2")
  public PlatformTransactionManager transactionManagerDb2(@Qualifier("db2") final DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
