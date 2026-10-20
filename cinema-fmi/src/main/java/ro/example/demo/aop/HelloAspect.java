package ro.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class HelloAspect {

    // Advice: @Before = WHEN.  Pointcut: the string = WHICH methods.
    @Before("execution(* ro.example.demo.aop.GreetingService.*(..))")
    public void beforeGreet(JoinPoint joinPoint) {
        System.out.println("[ASPECT] before " + joinPoint.getSignature().getName()
                + " with " + Arrays.toString(joinPoint.getArgs()));
    }
}
