package ro.example.fmishop.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.fmishop.config.AppConfig;
import ro.example.fmishop.service.OrderService;

// Exercise 3 - Eager vs lazy instantiation
public class Exercise3 {

    public static void main(String[] args) {

        // TODO 3.1: Run this class. When is InvoicePdfGenerator created:
        // before or after "2. Context created"? How long does the startup take?

        System.out.println("1. Creating the Spring context...");
        long start = System.currentTimeMillis();

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            System.out.println("2. Context created in " + (System.currentTimeMillis() - start) + " ms");

            OrderService orderService = context.getBean(OrderService.class);
            String receipt = orderService.checkout("Ana", "Laptop");

            System.out.println("3. Ana asks for an invoice...");
            orderService.generateInvoice(receipt);
        }
    }
}
