package example.config;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "example.repository")
@ComponentScan(basePackages = "example")
public class JpaConfig {

    // создаем и регистрируем подключение к БД. Можно так
    // @Bean
    // public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    //     return new PropertySourcesPlaceholderConfigurer();
    // }

    // @Value("${db.url}")
    // private String jdbcUrl;

    // @Value("${db.username}")
    // private String username;

    // @Value("${db.password}")
    // private String password;

    // @Bean
    // public DataSource dataSource() {
    //     var ds = new com.zaxxer.hikari.HikariDataSource();
    //     ds.setJdbcUrl(jdbcUrl);
    //     ds.setUsername(username);
    //     ds.setPassword(password);
    //     ds.setDriverClassName("org.postgresql.Driver");
    //     return ds;
    // }

    // или так
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.username"));
        ds.setPassword(env.getProperty("db.password"));
        ds.setDriverClassName("org.postgresql.Driver");
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("example.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public org.springframework.jdbc.core.JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new org.springframework.jdbc.core.JdbcTemplate(dataSource);
    }

    private Properties jpaProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update"); // или validate/create
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        return props;
    }
}
