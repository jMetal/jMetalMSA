package org.uma.jmetalmsa.algorithm.smsemoa;

import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;


public class SMSEMOASMA extends SMSEMOA<MSASolution> {

  /**
   * Constructor
   */
  public SMSEMOASMA(Problem<MSASolution> problem, int maxEvaluations,
                    int populationSize, double offset,
                    CrossoverOperator<MSASolution> crossoverOperator,
                    MutationOperator<MSASolution> mutationOperator,
                    SelectionOperator<List<MSASolution>, MSASolution> selectionOperator,
                    Hypervolume<MSASolution> hypervolume) {
    super(problem, maxEvaluations, populationSize, offset, crossoverOperator, mutationOperator,
            selectionOperator, hypervolume);

  }

  @Override
  protected List<MSASolution> createInitialPopulation() {
    return ((MSAProblem) getProblem()).createInitialPopulation(getMaxPopulationSize());
  }

  @Override
  public String getName() {
    return "SMSEMOA";
  }

  @Override
  public String getDescription() {
    return "SMSEMOA";
  }
}
