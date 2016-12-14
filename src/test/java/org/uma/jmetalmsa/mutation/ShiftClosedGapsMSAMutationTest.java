package org.uma.jmetalmsa.mutation;

import org.junit.Test;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.solution.util.ArrayChar;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import static org.junit.Assert.*;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class ShiftClosedGapsMSAMutationTest {
  private static final double EPSILON = 0.00000000000001;

  @Test
  public void shouldConstructorAssignTheCorrectProbabilityValue() {
    double mutationProbability = 0.2;
    ShiftClosedGapsMSAMutation mutation = new ShiftClosedGapsMSAMutation(mutationProbability);

    assertEquals(mutationProbability, Deencapsulation.getField(mutation, "mutationProbability"), EPSILON);
  }

  @Test(expected = JMetalException.class)
  public void shouldConstructorFailWhenPassedANegativeProbabilityValue() {
    double mutationProbability = -0.1;
    new ShiftClosedGapsMSAMutation(mutationProbability);
  }

  @Test
  public void shouldGetMutationProbabilityReturnTheRightValue() {
    double mutationProbability = 0.1;
    ShiftClosedGapsMSAMutation mutation = new ShiftClosedGapsMSAMutation(mutationProbability);
    assertEquals(mutationProbability, mutation.getMutationProbability(), EPSILON);
  }

  @Test(expected = JMetalException.class)
  public void shouldExecuteWithNullParameterThrowAnException() {
    ShiftClosedGapsMSAMutation mutation = new ShiftClosedGapsMSAMutation(0.1);

    mutation.execute(null);
  }

  @Mocked
  JMetalRandom randomGenerator;
  @Mocked
  MSASolution solution;
  @Test
  public void shouldMutateASingleVariableSolutionReturnTheSameSolutionIfTheProbabilityIsZero() {
    double mutationProbability = 0.1;

    new Expectations() {{
      randomGenerator.nextDouble();
      result = 0.5;
      times = 1;
    }};

    ShiftClosedGapsMSAMutation mutation = new ShiftClosedGapsMSAMutation(mutationProbability);

    MSASolution mutatedSolution = mutation.execute(solution);

    assertEquals(solution, mutatedSolution);
  }

  @Test
  public void shouldHasSequenceClosedGapsReturnFalseIfTheGapsGroupIsNull() {
    // TODO
  }

  @Test
  public void shouldHasSequenceClosedGapsReturnFalseIfThereIsOnlyOneGapGroup() {
    // TODO
  }
}