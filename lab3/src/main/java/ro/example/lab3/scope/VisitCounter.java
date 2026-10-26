package ro.example.lab3.scope;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

// Numarul total de vizite ale paginii, pentru toti utilizatorii.
@Component
// TODO 3.3: Adaugati @ApplicationScope: o singura instanta pentru toata aplicatia web.
public class VisitCounter {

    private final AtomicInteger visits = new AtomicInteger();

    public int increment() {
        return visits.incrementAndGet();
    }
}
