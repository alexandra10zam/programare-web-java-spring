package ro.example.lab2.exercise2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.lab2.exercise2.config.AppConfig;
import ro.example.lab2.exercise2.service.BookingService;

public class Main {

    public static void main(String[] args) {

        try (var context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("=== Context created ===");

            BookingService bookingService = context.getBean(BookingService.class);

            bookingService.book("Ana", "Dune", "A5", "A6");
            bookingService.book("Ion", "Barbie", "B1");
        }
    }
}
