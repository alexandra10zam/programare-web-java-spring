package ro.example.lab2.exercise4.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuditAspect {

    // Pointcut: toate metodele din BookingService.
    @Around("execution(* ro.example.lab2.exercise4.service.BookingService.*(..))")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        System.out.println("[AUDIT] " + method + " called with " + Arrays.deepToString(joinPoint.getArgs()));
        long start = System.nanoTime();

        Object result = joinPoint.proceed();   // apeleaza metoda reala

        long duration = (System.nanoTime() - start) / 1000;
        System.out.println("[AUDIT] " + method + " returned " + result + " in " + duration + " microseconds");
        return result;
    }
}
