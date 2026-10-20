package ro.example.lab2.demo.aop;

import org.springframework.stereotype.Service;

// Target object: nu modificam niciodata aceasta clasa.
@Service
public class GreetingService {

    public String greet(String name) {
        System.out.println("      >> GreetingService.greet runs");
        return "Hello, " + name + "!";
    }
}
