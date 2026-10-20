package ro.example.demo.aop;

import org.springframework.stereotype.Service;

// Target object: we never change this class.
@Service
public class GreetingService {

    public String greet(String name) {
        System.out.println("      >> GreetingService.greet runs");
        return "Hello, " + name + "!";
    }
}
