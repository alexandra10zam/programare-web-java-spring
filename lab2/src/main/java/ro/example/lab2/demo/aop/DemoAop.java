package ro.example.lab2.demo.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// DEMO 3 - rulati, apoi comentati @EnableAspectJAutoProxy si rulati din nou.
public class DemoAop {

    @Configuration
    @ComponentScan(basePackages = "ro.example.lab2.demo.aop")
    @EnableAspectJAutoProxy
    static class Config {
    }

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(Config.class)) {

            GreetingService service = context.getBean(GreetingService.class);
            System.out.println("Bean class: " + service.getClass().getName());
            System.out.println("Result: " + service.greet("FMI"));
        }
    }
}
