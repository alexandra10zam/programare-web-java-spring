package ro.example.cinema.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.cinema.config.AppConfig;
import ro.example.cinema.service.BookingService;

// Exercise 4 - The first aspect
public class Exercise4 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("--- Context created ---");

            BookingService bookingService = context.getBean(BookingService.class);
            System.out.println("Bean class: " + bookingService.getClass().getName());
            System.out.println();

            String receipt = bookingService.book("Ana", "Dune", "A5", "A6");
            System.out.println("Main received: " + receipt);
        }
    }
}
