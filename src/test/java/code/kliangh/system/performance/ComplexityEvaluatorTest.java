package code.kliangh.system.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;

public class ComplexityEvaluatorTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private ComplexityEvaluator aspect;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        joinPoint = mock(ProceedingJoinPoint.class);
    }

    @Test
    public void evaluateTimeComplexity() throws Throwable {
        aspect.evaluateTimeComplexity(joinPoint);
    }

    @Test
    public void evaluateSpaceComplexity() throws Throwable {
        aspect.evaluateSpaceComplexity(joinPoint);
    }
}