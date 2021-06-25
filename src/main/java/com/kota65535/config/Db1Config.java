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
import org.springframework.context.annotation.Primary;
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
    jdbcOperationsRef = "jdbcOperationsDb1",
    transactionManagerRef = "transactionManagerDb1",
    dataAccessStrategyRef = "dataAccessStrategyDb1",
    basePackages = {
        "com.kota65535.repository.one"
    }
)
@EnableAutoConfiguration(
    exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@Configuration
@ConfigurationPropertiesScan
public class Db1Config {

  @Bean
  @Qualifier("db1")
  public NamedParameterJdbcOperations jdbcOperationsDb1(
      @Qualifier("db1") DataSource dataSourceDb1) {
    return new NamedParameterJdbcTemplate(dataSourceDb1);
  }

  @Bean
  @Qualifier("db1")
  public DataSource dataSourceDb1(
      @Qualifier("db1") DataSourceProperties propertiesDb1
  ) {
    return propertiesDb1.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @Qualifier("db1")
  @ConfigurationProperties(prefix = "spring.datasources.one")
  public DataSourceProperties dataSourcePropertiesDb1() {
    return new DataSourceProperties();
  }

  @Bean
  @Qualifier("db1")
  public JdbcConverter jdbcConverterDb1(
      JdbcMappingContext mappingContext,
      @Qualifier("db1") NamedParameterJdbcOperations jdbcOperationsDb1,
      @Lazy RelationResolver relationResolver,
      JdbcCustomConversions conversions,
      Dialect dialect) {

    DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(jdbcOperationsDb1.getJdbcOperations());
    return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
        dialect.getIdentifierProcessing());
  }
  
  @Bean
  @Qualifier("db1")
  public DataAccessStrategy dataAccessStrategyDb1(
      @Qualifier("db1") NamedParameterJdbcOperations operations,
      @Qualifier("db1") JdbcConverter jdbcConverter, 
      JdbcMappingContext context, 
      Dialect dialect) {
    return new DefaultDataAccessStrategy(new SqlGeneratorSource(context, jdbcConverter, dialect), context, jdbcConverter, operations);
  }
  
  @Bean
  @Qualifier("db1")
  public PlatformTransactionManager transactionManagerDb1(@Qualifier("db1") final DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
