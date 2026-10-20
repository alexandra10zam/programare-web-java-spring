package ro.example.lab2.demo.scopes;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// DEMO 1 - rulati, apoi stergeti @Scope de pe PrototypeCounter si rulati din nou.
public class DemoScopes {

    @Configuration
    @ComponentScan(basePackages = "ro.example.lab2.demo.scopes")
    static class Config {
    }

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(Config.class)) {

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
