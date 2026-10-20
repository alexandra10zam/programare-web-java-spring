package ro.example.demo.scopes;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// DEMO 1 - run it live, then remove @Scope from PrototypeCounter and run it again.
public class DemoScopes {

    @Configuration
    @ComponentScan("ro.example.demo.scopes")
    static class Config {
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(Config.class)) {

            SingletonCounter s1 = context.getBean(SingletonCounter.class);
            SingletonCounter s2 = context.getBean(SingletonCounter.class);
            s1.increment();
            System.out.println("Singleton: s2.increment() = " + s2.increment() + ", same object? " + (s1 == s2));

            PrototypeCounter p1 = context.getBean(PrototypeCounter.class);
            PrototypeCounter p2 = context.getBean(PrototypeCounter.class);
            p1.increment();
            System.out.println("Prototype: p2.increment() = " + p2.increment() + ", same object? " + (p1 == p2));
        }
    }
}
