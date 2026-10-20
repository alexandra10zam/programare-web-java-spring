package ro.example.lab2.exercise1.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// O rezervare are STARE care se schimba: fiecare client are nevoie de propria rezervare.
@Component
// TODO 2: Faceti bean-ul prototype: @Scope(BeanDefinition.SCOPE_PROTOTYPE)
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
