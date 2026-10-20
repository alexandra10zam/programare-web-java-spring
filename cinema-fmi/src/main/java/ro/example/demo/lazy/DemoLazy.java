package ro.example.demo.lazy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// DEMO 2 - watch WHERE each "[Spring] ... created" line appears.
public class DemoLazy {

    @Configuration
    @ComponentScan("ro.example.demo.lazy")
    static class Config {
    }

    public static void main(String[] args) {
        System.out.println("1. Creating the context...");
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(Config.class)) {

            System.out.println("2. Context created");
            System.out.println("3. Asking for LazyBean...");
            context.getBean(LazyBean.class);
            System.out.println("4. Done");
        }
    }
}
