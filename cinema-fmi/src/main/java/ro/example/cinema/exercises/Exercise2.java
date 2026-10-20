package ro.example.cinema.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.cinema.config.AppConfig;
import ro.example.cinema.service.BookingService;

// Exercise 2 - Prototype injected into a singleton
public class Exercise2 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // TODO 2.1: Reservation is prototype now. Run this class.
            // Does every client get their own reservation? Look at the reservation numbers and the amounts paid.
            System.out.println("--- Context created ---");

            BookingService bookingService = context.getBean(BookingService.class);

            bookingService.book("Ana", "Dune", "A5", "A6");
            bookingService.book("Ion", "Barbie", "B1");

            // TODO 2.3: After the fix, run again. Every booking must have its own reservation.
        }
    }
}
