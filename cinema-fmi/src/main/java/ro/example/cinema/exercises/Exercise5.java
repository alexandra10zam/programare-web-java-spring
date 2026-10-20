package ro.example.cinema.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.cinema.config.AppConfig;
import ro.example.cinema.service.BookingService;

// Exercise 5 - Advice types
public class Exercise5 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("--- Context created ---");
            BookingService bookingService = context.getBean(BookingService.class);

            System.out.println();
            System.out.println("=== Booking 1: valid booking ===");
            System.out.println("Main received: " + bookingService.book("Ana", "Dune", "A5", "A6"));

            System.out.println();
            System.out.println("=== Booking 2: no seats selected ===");
            try {
                bookingService.book("Ion", "Barbie");
            } catch (IllegalArgumentException e) {
                System.out.println("Main caught: " + e.getMessage());
            }

            System.out.println();
            System.out.println("=== Booking 3: group booking over 200 RON (bonus) ===");
            System.out.println("Main received: "
                    + bookingService.book("Maria", "Dune", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"));
        }
    }
}
