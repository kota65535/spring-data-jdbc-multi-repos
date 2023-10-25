package com.kota65535.config;


import com.kota65535.config.Db3Config.JdbcRepositoryFactoryBeanDb3;
import com.zaxxer.hikari.HikariDataSource;
import java.io.Serializable;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactoryBean;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJdbcRepositories(
    repositoryFactoryBeanClass = JdbcRepositoryFactoryBeanDb3.class,
    transactionManagerRef = "transactionManagerDb3",
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

  private final AbstractJdbcConfiguration base;

  public Db3Config(ApplicationContext applicationContext) {
    this.base = new AbstractJdbcConfiguration();
    this.base.setApplicationContext(applicationContext);
  }

  @Bean
  @Qualifier("db3")
  @ConfigurationProperties(prefix = "spring.datasources.three")
  public HikariDataSource dataSourceDb3() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean
  @Qualifier("db3")
  public NamedParameterJdbcOperations jdbcOperationsDb3(
      @Qualifier("db3") DataSource dataSource
  ) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  @Qualifier("db3")
  public PlatformTransactionManager transactionManagerDb3(
      @Qualifier("db3") final DataSource dataSource
  ) {
    return new JdbcTransactionManager(dataSource);
  }

  @Bean
  @Qualifier("db3")
  public RelationalManagedTypes jdbcManagedTypesDb3() throws ClassNotFoundException {
    return base.jdbcManagedTypes();
  }

  @Bean
  @Qualifier("db3")
  public JdbcMappingContext jdbcMappingContextDb3(
      Optional<NamingStrategy> namingStrategy,
      @Qualifier("db3") JdbcCustomConversions customConversions,
      @Qualifier("db3") RelationalManagedTypes jdbcManagedTypes) {
    return base.jdbcMappingContext(namingStrategy, customConversions, jdbcManagedTypes);
  }

  @Bean
  @Qualifier("db3")
  public JdbcConverter jdbcConverterDb3(
      @Qualifier("db3") JdbcMappingContext mappingContext,
      @Qualifier("db3") NamedParameterJdbcOperations operations,
      @Qualifier("db3") @Lazy RelationResolver relationResolver,
      @Qualifier("db3") JdbcCustomConversions conversions,
      @Qualifier("db3") Dialect dialect) {
    return base.jdbcConverter(mappingContext, operations, relationResolver, conversions, dialect);
  }

  @Bean
  @Qualifier("db3")
  public JdbcCustomConversions jdbcCustomConversionsDb3() {
    return base.jdbcCustomConversions();
  }

  @Bean
  @Qualifier("db3")
  public JdbcAggregateTemplate jdbcAggregateTemplateDb3(
      ApplicationContext applicationContext,
      @Qualifier("db3") JdbcMappingContext mappingContext,
      @Qualifier("db3") JdbcConverter converter,
      @Qualifier("db3") DataAccessStrategy dataAccessStrategy) {
    return base.jdbcAggregateTemplate(applicationContext, mappingContext, converter, dataAccessStrategy);
  }

  @Bean
  @Qualifier("db3")
  public DataAccessStrategy dataAccessStrategyDb3(
      @Qualifier("db3") NamedParameterJdbcOperations operations,
      @Qualifier("db3") JdbcConverter jdbcConverter,
      @Qualifier("db3") JdbcMappingContext context,
      @Qualifier("db3") Dialect dialect) {
    return base.dataAccessStrategyBean(operations, jdbcConverter, context, dialect);
  }

  @Bean
  @Qualifier("db3")
  public Dialect jdbcDialectDb3(@Qualifier("db3") NamedParameterJdbcOperations operations) {
    return base.jdbcDialect(operations);
  }
  
  public static class JdbcRepositoryFactoryBeanDb3<T extends Repository<S, ID>, S, ID extends Serializable> extends
        JdbcRepositoryFactoryBean<T, S, ID> {

    public JdbcRepositoryFactoryBeanDb3(Class<T> repositoryInterface) {
      super(repositoryInterface);
    }

    @Override
    @Autowired
    public void setJdbcOperations(@Qualifier("db3") NamedParameterJdbcOperations operations) {
      super.setJdbcOperations(operations);
    }

    @Override
    @Autowired
    public void setDataAccessStrategy(@Qualifier("db3") DataAccessStrategy dataAccessStrategy) {
      super.setDataAccessStrategy(dataAccessStrategy);
    }

    @Override
    @Autowired
    public void setMappingContext(@Qualifier("db3") RelationalMappingContext mappingContext) {
      super.setMappingContext(mappingContext);
    }

    @Override
    @Autowired
    public void setDialect(@Qualifier("db3") Dialect dialect) {
      super.setDialect(dialect);
    }

    @Override
    @Autowired
    public void setConverter(@Qualifier("db3") JdbcConverter converter) {
      super.setConverter(converter);
    }
  }
}
