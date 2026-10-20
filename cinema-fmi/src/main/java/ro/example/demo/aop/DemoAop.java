package ro.example.demo.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// DEMO 3 - run it, then comment out @EnableAspectJAutoProxy and run it again.
public class DemoAop {

    @Configuration
    @ComponentScan("ro.example.demo.aop")
    @EnableAspectJAutoProxy
    static class Config {
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(Config.class)) {

            GreetingService service = context.getBean(GreetingService.class);
            System.out.println("Bean class: " + service.getClass().getName());
            System.out.println("Result: " + service.greet("FMI"));
        }
    }
}
