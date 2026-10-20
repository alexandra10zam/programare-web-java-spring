package ro.example.lab2.exercise4.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

// TODO 2: Adnotati clasa cu @Aspect si @Component.
public class AuditAspect {

    // TODO 3: Adnotati metoda cu
    // @Around("execution(* ro.example.lab2.exercise4.service.BookingService.*(..))")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        System.out.println("[AUDIT] " + method + " called with " + Arrays.deepToString(joinPoint.getArgs()));
        long start = System.nanoTime();

        // TODO 4: Apelati metoda reala si pastrati rezultatul:
        // Object result = joinPoint.proceed();
        Object result = null;

        long duration = (System.nanoTime() - start) / 1000;
        System.out.println("[AUDIT] " + method + " returned " + result + " in " + duration + " microseconds");
        return result;
    }
}
