package ro.example.lab3.scope;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.concurrent.atomic.AtomicInteger;

// Numarul total de vizite ale paginii, pentru toti utilizatorii.
@Component
@ApplicationScope
public class VisitCounter {

    private final AtomicInteger visits = new AtomicInteger();

    public int increment() {
        return visits.incrementAndGet();
    }
}
