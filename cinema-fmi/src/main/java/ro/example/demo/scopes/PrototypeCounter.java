package ro.example.demo.scopes;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrototypeCounter {

    private int count = 0;

    public int increment() {
        count++;
        return count;
    }
}
