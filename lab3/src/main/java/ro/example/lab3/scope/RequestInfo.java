package ro.example.lab3.scope;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// TODO 3.1: Adaugati @RequestScope pentru a crea o instanta noua la fiecare cerere HTTP.
public class RequestInfo {

    private final String id = UUID.randomUUID().toString().substring(0, 8);

    public String getId() {
        return id;
    }
}
