package ro.example.fmishop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

// TODO 5.1: Annotate this class with @Aspect and @Component.
public class PaymentAspect {

    // Pointcut: only the pay(..) method of PaymentService.
    private static final String PAY = "execution(* ro.example.fmishop.service.PaymentService.pay(..))";

    // TODO 5.2: @Before(PAY)
    public void beforePayment(JoinPoint joinPoint) {
        System.out.println("[BEFORE] Payment requested with " + Arrays.toString(joinPoint.getArgs()));
    }

    // TODO 5.3: @AfterReturning(pointcut = PAY, returning = "receipt")
    public void afterSuccessfulPayment(Object receipt) {
        System.out.println("[AFTER RETURNING] Payment successful, receipt: " + receipt);
    }

    // TODO 5.4: @AfterThrowing(pointcut = PAY, throwing = "ex")
    public void afterFailedPayment(Exception ex) {
        System.out.println("[AFTER THROWING] Payment failed: " + ex.getMessage());
    }

    // TODO 5.5: @After(PAY)
    public void afterPayment() {
        System.out.println("[AFTER] Payment attempt finished");
    }

    // TODO 5.6 (bonus): Annotate with @Around(PAY). If the amount (joinPoint.getArgs()[1])
    // is over 10 000, print a message and return "REFUSED" WITHOUT calling proceed().
    public Object limitPayment(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
