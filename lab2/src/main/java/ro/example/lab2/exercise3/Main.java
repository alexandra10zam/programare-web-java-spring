package ro.example.lab2.exercise3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.lab2.exercise3.config.AppConfig;
import ro.example.lab2.exercise3.service.BookingService;

public class Main {

    public static void main(String[] args) {

        System.out.println("1. Creating the Spring context...");
        long start = System.currentTimeMillis();

        try (var context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            System.out.println("2. Context created in " + (System.currentTimeMillis() - start) + " ms");

            BookingService bookingService = context.getBean(BookingService.class);
            String receipt = bookingService.book("Ana", "Dune", "A5");

            System.out.println("3. Ana wants paper tickets...");
            bookingService.printTickets(receipt);
        }
    }
}
