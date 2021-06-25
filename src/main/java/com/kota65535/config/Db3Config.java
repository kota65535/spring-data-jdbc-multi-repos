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
    jdbcOperationsRef = "jdbcOperationsDb3",
    transactionManagerRef = "transactionManagerDb3",
    dataAccessStrategyRef = "dataAccessStrategyDb3",
    basePackages = {
        "com.kota65535.repository.three"
    }
)
@EnableAutoConfiguration(
    exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@Configuration
@ConfigurationPropertiesScan
public class Db3Config {

  @Bean
  @Qualifier("db3")
  public NamedParameterJdbcOperations jdbcOperationsDb3(
      @Qualifier("db3") DataSource dataSourceDb3
  ) {
    return new NamedParameterJdbcTemplate(dataSourceDb3);
  }

  @Bean
  @Qualifier("db3")
  public DataSource dataSourceDb3(
      @Qualifier("db3") DataSourceProperties propertiesDb3
  ) {
    return propertiesDb3.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @Qualifier("db3")
  @ConfigurationProperties(prefix = "spring.datasources.three")
  public DataSourceProperties dataSourcePropertiesDb3() {
    return new DataSourceProperties();
  }

  @Bean
  @Qualifier("db3")
  public JdbcConverter jdbcConverterDb3(
      JdbcMappingContext mappingContext,
      @Qualifier("db3") NamedParameterJdbcOperations jdbcOperationsDb3,
      @Lazy RelationResolver relationResolver,
      JdbcCustomConversions conversions, 
      Dialect dialect
  ) {
    DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(jdbcOperationsDb3.getJdbcOperations());
    return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
        dialect.getIdentifierProcessing());
  }

  @Bean
  @Qualifier("db3")
  public DataAccessStrategy dataAccessStrategyDb3(
      @Qualifier("db3") NamedParameterJdbcOperations operations,
      @Qualifier("db3") JdbcConverter jdbcConverter,
      JdbcMappingContext context,
      Dialect dialect) {
    return new DefaultDataAccessStrategy(new SqlGeneratorSource(context, jdbcConverter, dialect), context, jdbcConverter, operations);
  }
  
  @Bean
  @Qualifier("db3")
  public PlatformTransactionManager transactionManagerDb3(@Qualifier("db3") final DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
