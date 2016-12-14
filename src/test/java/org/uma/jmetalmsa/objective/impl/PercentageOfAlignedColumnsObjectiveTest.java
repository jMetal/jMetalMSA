package org.uma.jmetalmsa.objective.impl;

import org.uma.jmetalmsa.score.impl.PercentageOfAlignedColumnsScore;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.util.SolutionListUtils;

import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import static org.junit.Assert.*;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class PercentageOfAlignedColumnsObjectiveTest {
  private static final double EPSILON = 0.00000000001 ;

  private PercentageOfAlignedColumnsScore objective ;

  @Mocked
  MSASolution solution ;

  @Before
  public void startup() {
    objective = new PercentageOfAlignedColumnsScore() ;
  }

  @Test
  public void shouldComputeReturnTheCorrectValueWhenTheSequenceOnlyHasOneAlignedColumn() {
    new Expectations() {{
      char[][]decodedSequence = new char[][]{
              {'-', 'B', 'C', '-', 'D'},
              {'-', 'B', 'E', 'C', '-'},
              {'-', '-', 'L', 'Ñ', '-'},
              {'-', 'T', 'C', 'D', 'G'},
      };
      solution.decodeToMatrix() ;
      result = decodedSequence;
      times = 1;
    }};

    assertEquals(100.0 * 1.0/5.0, objective.compute(solution, solution.decodeToMatrix()), EPSILON) ;
  }

  @Test
  public void shouldComputeReturnTheCorrectValueWhenTheSequenceHasTwoAlignedColumns() {
    new Expectations() {
      {
        char[][] decodedSequence = new char[][]{
                {'-', 'B', 'C', '-', 'D', '-'},
                {'-', 'B', 'E', 'C', '-', '-'},
                {'-', '-', 'L', 'N', '-', '-'},
                {'-', 'T', 'C', 'D', 'G', '-'},
                {'-', 'B', 'C', 'D', 'G', '-'},
        } ;
        solution.decodeToMatrix() ;
        result = decodedSequence;
        times = 1;
      }};

    assertEquals(100.0 * 2.0/6.0, objective.compute(solution, solution.decodeToMatrix()), EPSILON) ;
  }
}