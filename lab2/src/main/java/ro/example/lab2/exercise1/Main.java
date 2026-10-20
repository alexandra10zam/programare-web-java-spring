package ro.example.lab2.exercise1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.lab2.exercise1.config.AppConfig;
import ro.example.lab2.exercise1.model.Movie;
import ro.example.lab2.exercise1.model.Reservation;
import ro.example.lab2.exercise1.model.Ticket;
import ro.example.lab2.exercise1.repository.MovieCatalog;

public class Main {

    public static void main(String[] args) {

        try (var context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            System.out.println("=== Context created ===");

            MovieCatalog catalog1 = context.getBean(MovieCatalog.class);
            MovieCatalog catalog2 = context.getBean(MovieCatalog.class);
            System.out.println("Same catalog object? " + (catalog1 == catalog2));

            Movie dune = catalog1.findByTitle("Dune");
            Movie barbie = catalog1.findByTitle("Barbie");

            Reservation anaReservation = context.getBean(Reservation.class);
            anaReservation.addTicket(new Ticket(dune.title(), "A5", dune.price()));

            Reservation ionReservation = context.getBean(Reservation.class);
            ionReservation.addTicket(new Ticket(barbie.title(), "B1", barbie.price()));

            System.out.println("Ana's tickets: " + anaReservation.getTickets());
            System.out.println("Ion's tickets: " + ionReservation.getTickets());
            System.out.println("Same reservation object? " + (anaReservation == ionReservation));
        }
    }
}
