package ro.example.fmishop.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.fmishop.config.AppConfig;
import ro.example.fmishop.service.OrderService;

// Exercise 4 - The first aspect
public class Exercise4 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // Run this class BEFORE and AFTER the TODOs. Compare the bean class name.
            System.out.println("--- Context created ---");

            OrderService orderService = context.getBean(OrderService.class);
            System.out.println("Bean class: " + orderService.getClass().getName());
            System.out.println();

            String receipt = orderService.checkout("Ana", "Laptop", "Mouse");
            System.out.println("Main received: " + receipt);
        }
    }
}
