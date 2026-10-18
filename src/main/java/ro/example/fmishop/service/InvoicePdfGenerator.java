package ro.example.fmishop.service;

import org.springframework.stereotype.Component;

// Heavy bean: slow to create and used only when a client asks for an invoice.
@Component
// TODO 3.2: Add @Lazy and run Exercise3 again. Is the bean still created at startup?
public class InvoicePdfGenerator {

    public InvoicePdfGenerator() throws InterruptedException {
        System.out.println("   [Spring] InvoicePdfGenerator created (loading fonts and templates...)");
        Thread.sleep(1500);

        // Step 3.4: uncomment the line below and run Exercise3 with and without @Lazy.
        // throw new IllegalStateException("Invoice template not found!");
    }

    public void generate(String receipt) {
        System.out.println("   Invoice generated: " + receipt + ".pdf");
    }
}
