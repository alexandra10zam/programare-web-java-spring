package ro.example.cinema.exercises;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.example.cinema.config.AppConfig;
import ro.example.cinema.model.Movie;
import ro.example.cinema.model.Reservation;
import ro.example.cinema.model.Ticket;
import ro.example.cinema.repository.MovieCatalog;

// Exercise 1 - Singleton vs prototype
public class Exercise1 {

    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            // TODO 1.1: Run this class. Is there one catalog or two?
            // Ana and Ion are two different clients: which seats does each of them have?
            System.out.println("--- Context created ---");

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

            // TODO 1.3: After adding @Scope, run again. Count the "[Spring] Reservation created" messages.
        }
    }
}
