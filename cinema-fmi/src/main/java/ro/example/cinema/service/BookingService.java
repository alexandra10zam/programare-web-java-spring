package ro.example.cinema.service;

import org.springframework.stereotype.Service;
import ro.example.cinema.model.Movie;
import ro.example.cinema.model.Reservation;
import ro.example.cinema.model.Ticket;
import ro.example.cinema.repository.MovieCatalog;

// BookingService is a SINGLETON (the default scope).
@Service
public class BookingService {

    private final MovieCatalog catalog;
    private final Reservation reservation;
    private final PaymentService paymentService;
    private final TicketPrinter printer;

    // TODO 2.2: The reservation is injected only ONCE, when this singleton is created.
    // Replace the Reservation field with ObjectProvider<Reservation> reservationProvider
    // and get a new one inside book():  Reservation reservation = reservationProvider.getObject();

    // TODO 3.3: Do the same for TicketPrinter: use ObjectProvider<TicketPrinter>
    // and call getObject() only inside printTickets().

    public BookingService(MovieCatalog catalog,
                          Reservation reservation,
                          PaymentService paymentService,
                          TicketPrinter printer) {
        System.out.println("   [Spring] BookingService created");
        this.catalog = catalog;
        this.reservation = reservation;
        this.paymentService = paymentService;
        this.printer = printer;
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

    public void printTickets(String receipt) {
        printer.print(receipt);
    }
}
