package ro.example.cinema.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

// TODO 4.1: Annotate this class with @Aspect and @Component.
public class AuditAspect {

    // TODO 4.2: Annotate this method with
    // @Around("execution(* ro.example.cinema.service.BookingService.*(..))")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        System.out.println("[AUDIT] " + method + " called with " + Arrays.deepToString(joinPoint.getArgs()));
        long start = System.nanoTime();

        // TODO 4.3: Call the real method and keep its result:
        // Object result = joinPoint.proceed();
        Object result = null;

        long duration = (System.nanoTime() - start) / 1000;
        System.out.println("[AUDIT] " + method + " returned " + result + " in " + duration + " microseconds");
        return result;
    }
}
