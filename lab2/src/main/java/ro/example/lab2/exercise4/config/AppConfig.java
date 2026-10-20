package ro.example.lab2.exercise4.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ro.example.lab2.exercise4")
// TODO 5: Activati aspectele cu @EnableAspectJAutoProxy.
public class AppConfig {
}
