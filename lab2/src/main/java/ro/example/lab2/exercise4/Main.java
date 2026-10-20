package ro.example.lab2.exercise4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.lab2.exercise4.config.AppConfig;
import ro.example.lab2.exercise4.service.BookingService;

public class Main {

    public static void main(String[] args) {

        try (var context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("=== Context created ===");

            BookingService bookingService = context.getBean(BookingService.class);
            System.out.println("Bean class: " + bookingService.getClass().getName());
            System.out.println();

            String receipt = bookingService.book("Ana", "Dune", "A5", "A6");
            System.out.println("Main received: " + receipt);
        }
    }
}
