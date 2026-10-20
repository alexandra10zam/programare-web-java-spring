package ro.example.lab2.demo.scopes;

import org.springframework.stereotype.Component;

// Scope implicit: singleton.
@Component
public class SingletonCounter {

    private int count = 0;

    public int increment() {
        count++;
        return count;
    }
}
