package ro.example.lab2.exercise5.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import ro.example.lab2.exercise5.model.Movie;
import ro.example.lab2.exercise5.model.Reservation;
import ro.example.lab2.exercise5.model.Ticket;
import ro.example.lab2.exercise5.repository.MovieCatalog;

// BookingService este SINGLETON (scope-ul implicit).
@Service
public class BookingService {

    private final MovieCatalog catalog;
    private final ObjectProvider<Reservation> reservationProvider;
    private final PaymentService paymentService;

    public BookingService(MovieCatalog catalog,
                          ObjectProvider<Reservation> reservationProvider,
                          PaymentService paymentService) {
        System.out.println("   [Spring] BookingService created");
        this.catalog = catalog;
        this.reservationProvider = reservationProvider;
        this.paymentService = paymentService;
    }

    public String book(String client, String title, String... seats) {
        Reservation reservation = reservationProvider.getObject();   // o rezervare NOUA la fiecare apel
        Movie movie = catalog.findByTitle(title);
        for (String seat : seats) {
            reservation.addTicket(new Ticket(movie.title(), seat, movie.price()));
        }
        System.out.println("   " + client + "'s reservation: " + reservation.getTickets()
                + " (reservation #" + reservation.hashCode() + ")");
        return paymentService.pay(client, reservation.total());
    }
}
