package ro.example.lab2.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class HelloAspect {

    // Advice: @Before = CAND.  Pointcut: textul din paranteza = CE metode.
    @Before("execution(* ro.example.lab2.demo.aop.GreetingService.*(..))")
    public void beforeGreet(JoinPoint joinPoint) {
        System.out.println("[ASPECT] before " + joinPoint.getSignature().getName()
                + " with " + Arrays.toString(joinPoint.getArgs()));
    }
}
