package ro.example.fmishop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ro.example.fmishop")
// TODO 4.4: Enable the aspect mechanism with @EnableAspectJAutoProxy.
public class AppConfig {
}
