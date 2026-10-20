package ro.example.cinema.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.cinema.config.AppConfig;
import ro.example.cinema.service.BookingService;

// Exercise 3 - Eager vs lazy instantiation
public class Exercise3 {

    public static void main(String[] args) {

        System.out.println("1. Creating the Spring context...");
        long start = System.currentTimeMillis();

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            System.out.println("2. Context created in " + (System.currentTimeMillis() - start) + " ms");

            BookingService bookingService = context.getBean(BookingService.class);
            String receipt = bookingService.book("Ana", "Dune", "A5");

            System.out.println("3. Ana wants paper tickets...");
            bookingService.printTickets(receipt);
        }
    }
}
