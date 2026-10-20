package ro.example.lab2.exercise3.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import ro.example.lab2.exercise3.model.Movie;
import ro.example.lab2.exercise3.model.Reservation;
import ro.example.lab2.exercise3.model.Ticket;
import ro.example.lab2.exercise3.repository.MovieCatalog;

// BookingService este SINGLETON (scope-ul implicit).
@Service
public class BookingService {

    private final MovieCatalog catalog;
    private final ObjectProvider<Reservation> reservationProvider;
    private final PaymentService paymentService;
    private final TicketPrinter printer;

    // TODO 3: TicketPrinter este cerut in constructor, deci e creat odata cu BookingService.
    // Folositi ObjectProvider<TicketPrinter>, la fel ca pentru rezervare la exercitiul 2,
    // si apelati getObject() doar in printTickets().

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
        Reservation reservation = reservationProvider.getObject();   // o rezervare NOUA la fiecare apel
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
