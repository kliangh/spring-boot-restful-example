package code.kliangh.system.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Profile("dev")
@Aspect
@Component
public class ComplexityEvaluator {

    @Around("@annotation(TimeComplexity)")
    public Object evaluateTimeComplexity(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Instant start = Instant.now();
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        final Instant finish = Instant.now();
        final long duration = Duration.between(start, finish).toMillis();

        System.out.println(String.format("%s executed in %s ms",
                                         proceedingJoinPoint.getSignature(), duration));

        return result;
    }

    @Around("@annotation(SpaceComplexity)")
    public Object evaluateSpaceComplexity(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        Long before = runtime.totalMemory() - runtime.freeMemory();
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        Long after = runtime.totalMemory() - runtime.freeMemory();

        System.out.println(String.format("%s consumed %s bytes of memory",
                                         proceedingJoinPoint.getSignature(), after - before));

        return result;
    }
}
