package ro.example.cinema.service;

import org.springframework.stereotype.Component;

// Heavy bean: connecting to the ticket printer is slow, and few clients want paper tickets.
@Component
// TODO 3.2: Add @Lazy and run Exercise3 again. Is the printer still created at startup?
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
