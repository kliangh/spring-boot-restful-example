package code.kliangh.system.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;

class ComplexityEvaluatorTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private ComplexityEvaluator aspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        joinPoint = mock(ProceedingJoinPoint.class);
    }

    @Test
    void evaluateTimeComplexity() throws Throwable {
        aspect.evaluateTimeComplexity(joinPoint);
    }

    @Test
    void evaluateSpaceComplexity() throws Throwable {
        aspect.evaluateSpaceComplexity(joinPoint);
    }
}