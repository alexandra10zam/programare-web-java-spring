package ro.example.lab2.exercise5.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

// TODO 2: Adnotati clasa cu @Aspect si @Component.
public class PaymentAspect {

    // Pointcut: doar metoda pay(..) din PaymentService.
    private static final String PAY = "execution(* ro.example.lab2.exercise5.service.PaymentService.pay(..))";

    // TODO 3: @Before(PAY)
    public void beforePayment(JoinPoint joinPoint) {
        System.out.println("[BEFORE] Payment requested with " + Arrays.toString(joinPoint.getArgs()));
    }

    // TODO 4: @AfterReturning(pointcut = PAY, returning = "receipt")
    public void afterSuccessfulPayment(Object receipt) {
        System.out.println("[AFTER RETURNING] Payment successful, receipt: " + receipt);
    }

    // TODO 5: @AfterThrowing(pointcut = PAY, throwing = "ex")
    public void afterFailedPayment(Exception ex) {
        System.out.println("[AFTER THROWING] Payment failed: " + ex.getMessage());
    }

    // TODO 6: @After(PAY)
    public void afterPayment() {
        System.out.println("[AFTER] Payment attempt finished");
    }

    // TODO 7 (BONUS): Adnotati cu @Around(PAY). Daca suma (joinPoint.getArgs()[1])
    // depaseste 200 RON, afisati un mesaj si returnati "REFUSED" FARA sa apelati proceed().
    public Object limitPayment(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
