package ro.example.lab2.demo.lazy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// DEMO 2 - urmariti UNDE apare fiecare mesaj "[Spring] ... created".
public class DemoLazy {

    @Configuration
    @ComponentScan(basePackages = "ro.example.lab2.demo.lazy")
    static class Config {
    }

    public static void main(String[] args) {
        System.out.println("1. Creating the context...");
        try (var context = new AnnotationConfigApplicationContext(Config.class)) {

            System.out.println("2. Context created");
            System.out.println("3. Asking for LazyBean...");
            context.getBean(LazyBean.class);
            System.out.println("4. Done");
        }
    }
}
