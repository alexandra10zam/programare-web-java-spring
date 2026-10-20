package ro.example.lab2.exercise2.model;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// O rezervare are STARE care se schimba: fiecare client are nevoie de propria rezervare.
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
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
