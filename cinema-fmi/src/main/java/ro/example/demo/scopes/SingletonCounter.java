package ro.example.demo.scopes;

import org.springframework.stereotype.Component;

// Default scope: singleton.
@Component
public class SingletonCounter {

    private int count = 0;

    public int increment() {
        count++;
        return count;
    }
}
