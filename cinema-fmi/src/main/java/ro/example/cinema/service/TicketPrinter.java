package ro.example.cinema.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

// Heavy bean: connecting to the ticket printer is slow, and few clients want paper tickets.
@Component
@Lazy
public class TicketPrinter {

    public TicketPrinter() throws InterruptedException {
        System.out.println("   [Spring] TicketPrinter created (connecting to the printer...)");
        Thread.sleep(1500);

        // Step 3.4: uncomment the line below and run Exercise3 with and without @Lazy.
        // throw new IllegalStateException("Printer not connected!");
    }

    public void print(String receipt) {
        System.out.println("   Tickets printed for " + receipt);
    }
}
