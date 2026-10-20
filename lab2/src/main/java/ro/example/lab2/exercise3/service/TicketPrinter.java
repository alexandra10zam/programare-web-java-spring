package ro.example.lab2.exercise3.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

// Bean "greu": conectarea la imprimanta dureaza, iar putini clienti vor bilete pe hartie.
@Component
@Lazy
public class TicketPrinter {

    public TicketPrinter() throws InterruptedException {
        System.out.println("   [Spring] TicketPrinter created (connecting to the printer...)");
        Thread.sleep(1500);

        // Pasul 4: decomentati linia de mai jos si rulati cu si fara @Lazy.
        // throw new IllegalStateException("Printer not connected!");
    }

    public void print(String receipt) {
        System.out.println("   Tickets printed for " + receipt);
    }
}
