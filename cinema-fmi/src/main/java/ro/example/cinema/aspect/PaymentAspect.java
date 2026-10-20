package ro.example.cinema.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class PaymentAspect {

    // Pointcut: only the pay(..) method of PaymentService.
    private static final String PAY = "execution(* ro.example.cinema.service.PaymentService.pay(..))";

    @Before(PAY)
    public void beforePayment(JoinPoint joinPoint) {
        System.out.println("[BEFORE] Payment requested with " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = PAY, returning = "receipt")
    public void afterSuccessfulPayment(Object receipt) {
        System.out.println("[AFTER RETURNING] Payment successful, receipt: " + receipt);
    }

    @AfterThrowing(pointcut = PAY, throwing = "ex")
    public void afterFailedPayment(Exception ex) {
        System.out.println("[AFTER THROWING] Payment failed: " + ex.getMessage());
    }

    @After(PAY)
    public void afterPayment() {
        System.out.println("[AFTER] Payment attempt finished");
    }

    // Bonus: @Around can run logic INSTEAD of the intercepted method.
    @Around(PAY)
    public Object limitPayment(ProceedingJoinPoint joinPoint) throws Throwable {
        double amount = (double) joinPoint.getArgs()[1];
        if (amount > 200) {
            System.out.println("[AROUND] Group booking over 200 RON: PaymentService is NOT called");
            return "REFUSED";
        }
        return joinPoint.proceed();
    }
}
