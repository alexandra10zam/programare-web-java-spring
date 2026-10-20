package ro.example.cinema.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import ro.example.cinema.model.Movie;
import ro.example.cinema.model.Reservation;
import ro.example.cinema.model.Ticket;
import ro.example.cinema.repository.MovieCatalog;

// BookingService is a SINGLETON (the default scope).
@Service
public class BookingService {

    private final MovieCatalog catalog;
    private final ObjectProvider<Reservation> reservationProvider;
    private final PaymentService paymentService;
    private final TicketPrinter printer;

    // TODO 3.3: Do the same for TicketPrinter: use ObjectProvider<TicketPrinter>
    // and call getObject() only inside printTickets().

    public BookingService(MovieCatalog catalog,
                          ObjectProvider<Reservation> reservationProvider,
                          PaymentService paymentService,
                          TicketPrinter printer) {
        System.out.println("   [Spring] BookingService created");
        this.catalog = catalog;
        this.reservationProvider = reservationProvider;
        this.paymentService = paymentService;
        this.printer = printer;
    }

    public String book(String client, String title, String... seats) {
        Reservation reservation = reservationProvider.getObject();   // a NEW reservation for every booking
        Movie movie = catalog.findByTitle(title);
        for (String seat : seats) {
            reservation.addTicket(new Ticket(movie.title(), seat, movie.price()));
        }
        System.out.println("   " + client + "'s reservation: " + reservation.getTickets()
                + " (reservation #" + reservation.hashCode() + ")");
        return paymentService.pay(client, reservation.total());
    }

    public void printTickets(String receipt) {
        printer.print(receipt);
    }
}
