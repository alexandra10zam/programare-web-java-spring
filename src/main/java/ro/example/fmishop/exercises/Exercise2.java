package ro.example.fmishop.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.fmishop.config.AppConfig;
import ro.example.fmishop.service.OrderService;

// Exercise 2 - Prototype injected into a singleton
public class Exercise2 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // TODO 2.1: ShoppingCart is prototype now. Run this class.
            // Does every client get their own cart? Look at the cart numbers and the amounts paid.
            System.out.println("--- Context created ---");

            OrderService orderService = context.getBean(OrderService.class);

            orderService.checkout("Ana", "Laptop");
            orderService.checkout("Ion", "Mouse", "Keyboard");

            // TODO 2.3: After the fix, run again. Every order must have its own cart.
        }
    }
}
