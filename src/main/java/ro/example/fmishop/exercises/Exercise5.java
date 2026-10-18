package ro.example.fmishop.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.fmishop.config.AppConfig;
import ro.example.fmishop.service.OrderService;

// Exercise 5 - Advice types
public class Exercise5 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // Before running: write down in which ORDER you expect the messages to appear.
            System.out.println("--- Context created ---");
            OrderService orderService = context.getBean(OrderService.class);

            System.out.println();
            System.out.println("=== Order 1: valid order ===");
            System.out.println("Main received: " + orderService.checkout("Ana", "Laptop", "Mouse"));

            System.out.println();
            System.out.println("=== Order 2: empty cart ===");
            try {
                orderService.checkout("Ion");
            } catch (IllegalArgumentException e) {
                System.out.println("Main caught: " + e.getMessage());
            }

            System.out.println();
            System.out.println("=== Order 3: amount over the limit (bonus) ===");
            System.out.println("Main received: " + orderService.checkout("Maria", "Laptop", "Laptop", "Laptop"));
        }
    }
}
