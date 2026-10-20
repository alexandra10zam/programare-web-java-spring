package ro.example.demo.lazy;

import org.springframework.stereotype.Component;

@Component
public class EagerBean {

    public EagerBean() {
        System.out.println("   [Spring] EagerBean created");
    }
}
