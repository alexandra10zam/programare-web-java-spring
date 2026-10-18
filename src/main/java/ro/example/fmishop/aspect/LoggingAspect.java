package ro.example.fmishop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

// TODO 4.1: Annotate this class with @Aspect and @Component.
public class LoggingAspect {

    // TODO 4.2: Annotate this method with
    // @Around("execution(* ro.example.fmishop.service.OrderService.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        System.out.println("[LOG] Calling " + method + " with arguments " + Arrays.deepToString(joinPoint.getArgs()));
        long start = System.nanoTime();

        // TODO 4.3: Call the real method and keep its result:
        // Object result = joinPoint.proceed();
        Object result = null;

        long duration = (System.nanoTime() - start) / 1000;
        System.out.println("[LOG] " + method + " returned " + result + " in " + duration + " microseconds");
        return result;
    }
}
