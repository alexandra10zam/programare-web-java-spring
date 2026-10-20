package ro.example.lab2.exercise5.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "ro.example.lab2.exercise5")
@EnableAspectJAutoProxy
public class AppConfig {
}
