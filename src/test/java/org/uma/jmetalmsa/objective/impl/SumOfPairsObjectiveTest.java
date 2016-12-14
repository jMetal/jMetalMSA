package org.uma.jmetalmsa.objective.impl;

import org.uma.jmetalmsa.score.impl.SumOfPairsScore;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.util.distancematrix.DistanceMatrix;
import org.uma.jmetalmsa.util.distancematrix.impl.PAM250;

import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import static org.junit.Assert.*;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class SumOfPairsObjectiveTest {
  private static final double EPSILON = 0.00000000001 ;
  private SumOfPairsScore objective ;

  @Mocked
  MSASolution solution;

  @Before
  public void startup() {
    objective = new SumOfPairsScore(new PAM250()) ;
  }

  @Test
  public void shouldComputeReturnTheCorrectValueWhenUsingPAM250OnTwoSequencesFullOfGapsButOneChar() {
    new Expectations() {{
        char[][]decodedSequence = new char[][]{{'A', '-'}, {'-', 'B'}};
        solution.decodeToMatrix() ;
        result = decodedSequence;
        times = 1;
    }};

    DistanceMatrix distanceMatrix = new PAM250() ;

    assertEquals(distanceMatrix.getGapPenalty()*2+0, objective.compute(solution, solution.decodeToMatrix()), EPSILON);
  }

  @Test
  public void shouldComputeReturnTheCorrectValueWhenUsingPAM250OnThreeSequences() {
    new Expectations() {{
        char[][]decodedSequence = new char[][]{{'A', '-'}, {'-', 'B'}, {'A', 'C'}};
        solution.decodeToMatrix() ;
        result = decodedSequence;
        times = 1;
      }};

    DistanceMatrix distanceMatrix = new PAM250() ;

    double result = 0 ;
    result += distanceMatrix.getDistance('A', '-') ;
    result += distanceMatrix.getDistance('A', 'A') ;
    result += distanceMatrix.getDistance('-', 'A') ;

    result += distanceMatrix.getDistance('-', 'B') ;
    result += distanceMatrix.getDistance('-', 'C') ;
    result += distanceMatrix.getDistance('B', 'C') ;

    assertEquals(result, objective.compute(solution, solution.decodeToMatrix()), EPSILON);
  }
}