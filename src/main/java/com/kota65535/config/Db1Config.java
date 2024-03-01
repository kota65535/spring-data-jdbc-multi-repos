package com.kota65535.config;


import com.zaxxer.hikari.HikariDataSource;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJdbcRepositories(
    jdbcOperationsRef = "jdbcOperationsDb1",
    jdbcAggregateOperationsRef = "jdbcAggregateOperationsDb1",
    transactionManagerRef = "transactionManagerDb1",
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

  private final ApplicationContext applicationContext;
  private final AbstractJdbcConfiguration base;

  public Db1Config(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
    this.base = new AbstractJdbcConfiguration();
    this.base.setApplicationContext(applicationContext);
  }

  @Bean
  @Qualifier("db1")
  @ConfigurationProperties(prefix = "spring.datasources.one")
  public HikariDataSource dataSourceDb1() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean
  @Qualifier("db1")
  public NamedParameterJdbcOperations jdbcOperationsDb1(@Qualifier("db1") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  @Qualifier("db1")
  public PlatformTransactionManager transactionManagerDb1(@Qualifier("db1") DataSource dataSource) {
    return new JdbcTransactionManager(dataSource);
  }

  @Bean
  @Qualifier("db1")
  public JdbcAggregateOperations jdbcAggregateOperationsDb1(
      @Qualifier("db1") NamedParameterJdbcOperations operations
  ) throws ClassNotFoundException {
    Dialect dialect = base.jdbcDialect(operations);
    JdbcCustomConversions conversions = base.jdbcCustomConversions(Optional.of(dialect));
    JdbcMappingContext mappingContext = base.jdbcMappingContext(Optional.empty(), conversions, base.jdbcManagedTypes());

    // Creates a proxy instance of RelationResolver to avoid circular dependency of JdbcConverter and DataAccessStrategy.
    // Target source is a dummy at first, and later it is swapped with the real DataAccessStrategy instance.
    HotSwappableTargetSource targetSource = new HotSwappableTargetSource(new Object());
    RelationResolver relationResolver = ProxyFactory.getProxy(RelationResolver.class, targetSource);
    JdbcConverter converter = base.jdbcConverter(mappingContext, operations, relationResolver, conversions, dialect);
    DataAccessStrategy dataAccessStrategy = base.dataAccessStrategyBean(operations, converter, mappingContext, dialect);
    targetSource.swap(dataAccessStrategy);
    return new JdbcAggregateTemplate(applicationContext, converter, dataAccessStrategy);
  }
}
