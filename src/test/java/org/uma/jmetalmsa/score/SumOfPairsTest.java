package org.uma.jmetalmsa.score;

import org.uma.jmetalmsa.score.impl.SumOfPairsScore;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.util.distancematrix.impl.PAM250;

import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import static org.junit.Assert.*;

public class SumOfPairsTest {
    private static final double EPSILON = 0.00000000001 ;
    private SumOfPairsScore objective ;

    @Mocked
    MSASolution solution;

    @Before
    public void startup() {
        objective = new SumOfPairsScore(new PAM250()) ;
    }

    @Test
    public void shouldThreeIdenticalSequencesReturn12() {
        double expectedValue = 12;
        double receivedValue = objective.compute(solution, new char[][]{{'A', 'A'}, {'A', 'A'}, {'A', 'A'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test
    public void shouldTwoSequencesOneWithGapsReturnMinus11() {
        double expectedValue = -11;
        double receivedValue = objective.compute(solution, new char[][]{{'F', 'A'}, {'A', '-'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test
    public void shouldTwoSequencesWithOnlyGapsReturn3() {
        double expectedValue = 3;
        double receivedValue = objective.compute(solution, new char[][]{{'-', '-', '-'}, {'-', '-', '-'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test(expected = org.uma.jmetal.util.JMetalException.class)
    public void shouldRaiseExceptionWhenInvalidCharacterIsInTheSequence() {
        objective.compute(solution, new char[][]{{'?'}, {'A'}});
    }
}