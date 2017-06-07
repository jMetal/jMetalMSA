package org.uma.jmetalmsa.score;

import org.uma.jmetalmsa.score.impl.PercentageOfAlignedColumnsScore;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetalmsa.solution.MSASolution;

import mockit.Mocked;

import static org.junit.Assert.*;

public class PercentageOfAlignedColumnsScoreTest {
    private static final double EPSILON = 0.00000000001 ;
    private PercentageOfAlignedColumnsScore objective ;

    @Mocked
    MSASolution solution;

    @Before
    public void startup() {
        objective = new PercentageOfAlignedColumnsScore() ;
    }

    @Test
    public void shouldThreeIdenticalSequencesReturn100() {
        double expectedValue = 100.0;
        double receivedValue = objective.compute(solution, new char[][]{{'C', 'F'}, {'C', 'F'}, {'C', 'F'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test
    public void shouldTwoSequencesOneWithGapsReturn0() {
        double expectedValue = 0.0;
        double receivedValue = objective.compute(solution, new char[][]{{'G', 'A'}, {'A', '-'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test
    public void shouldTwoSequencesWithOnlyGapsReturn100() {
        double expectedValue = 100.0;
        double receivedValue = objective.compute(solution, new char[][]{{'-', '-', '-'}, {'-', '-', '-'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test
    public void shouldTwoSequencesWithInitialGapsReturn50() {
        double expectedValue = 50.0;
        double receivedValue = objective.compute(solution, new char[][]{{'-', 'A', 'C', '-'}, {'-', 'A', 'M', 'W'}});

        assertEquals(expectedValue, receivedValue, EPSILON);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldTwoSequencesWithDifferentLengthsRaiseException() {
        objective.compute(solution, new char[][]{{'A', 'T', 'M'}, {'G', '-'}});
    }



    @Test
    public void isAMinimizationScoreReturnsFalse() {
        boolean expectedValue = false;
        boolean receivedValue = objective.isAMinimizationScore();

        assertEquals(expectedValue, receivedValue);
    }

    @Test
    public void getNameReturnsTC() {
        String expectedString = "TC";
        String receivedString = objective.getName();

        assertEquals(expectedString, receivedString);
    }

    @Test
    public void getDescriptionReturnsExpectedString() {
        String expectedString = "Percentage of aligned columns in a multiple sequence";
        String receivedString = objective.getDescription();

        assertEquals(expectedString, receivedString);
    }
}
