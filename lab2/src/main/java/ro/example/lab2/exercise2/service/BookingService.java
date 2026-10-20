package ro.example.lab2.exercise2.service;

import org.springframework.stereotype.Service;
import ro.example.lab2.exercise2.model.Movie;
import ro.example.lab2.exercise2.model.Reservation;
import ro.example.lab2.exercise2.model.Ticket;
import ro.example.lab2.exercise2.repository.MovieCatalog;

// BookingService este SINGLETON (scope-ul implicit).
@Service
public class BookingService {

    private final MovieCatalog catalog;
    private final Reservation reservation;
    private final PaymentService paymentService;

    // TODO 2: Rezervarea este injectata O SINGURA DATA, cand se creeaza singleton-ul.
    // Inlocuiti campul Reservation cu ObjectProvider<Reservation> reservationProvider
    // si cereti o rezervare noua in book():  Reservation reservation = reservationProvider.getObject();

    public BookingService(MovieCatalog catalog,
                          Reservation reservation,
                          PaymentService paymentService) {
        System.out.println("   [Spring] BookingService created");
        this.catalog = catalog;
        this.reservation = reservation;
        this.paymentService = paymentService;
    }

    public String book(String client, String title, String... seats) {
        Movie movie = catalog.findByTitle(title);
        for (String seat : seats) {
            reservation.addTicket(new Ticket(movie.title(), seat, movie.price()));
        }
        System.out.println("   " + client + "'s reservation: " + reservation.getTickets()
                + " (reservation #" + reservation.hashCode() + ")");
        return paymentService.pay(client, reservation.total());
    }
}
