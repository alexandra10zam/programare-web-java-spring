package ro.example.cinema.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("ro.example.cinema")
@EnableAspectJAutoProxy
public class AppConfig {
}
