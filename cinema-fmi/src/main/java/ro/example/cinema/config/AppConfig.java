package ro.example.cinema.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ro.example.cinema")
// TODO 4.4: Enable the aspect mechanism with @EnableAspectJAutoProxy.
public class AppConfig {
}
