package example.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "example")
@EnableJpaRepositories(basePackages = "example.repository")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
