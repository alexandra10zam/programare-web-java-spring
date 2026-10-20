package ro.example.cinema.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// A reservation has STATE that changes: every client needs their own.
@Component
// TODO 1.2: Make this bean prototype: @Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Reservation {

    private final List<Ticket> tickets = new ArrayList<>();

    public Reservation() {
        System.out.println("   [Spring] Reservation created");
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public double total() {
        return tickets.stream().mapToDouble(Ticket::price).sum();
    }
}
