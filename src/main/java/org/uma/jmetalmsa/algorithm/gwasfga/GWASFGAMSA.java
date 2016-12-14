package org.uma.jmetalmsa.algorithm.gwasfga;

import org.uma.jmetal.algorithm.multiobjective.gwasfga.GWASFGA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;


public class GWASFGAMSA extends GWASFGA<MSASolution> {

  public GWASFGAMSA(Problem<MSASolution> problem, int populationSize, int maxIterations,
                    CrossoverOperator<MSASolution> crossoverOperator,
                    MutationOperator<MSASolution> mutationOperator,
                    SelectionOperator<List<MSASolution>,
                            MSASolution> selectionOperator,
                    SolutionListEvaluator<MSASolution> evaluator) {
    super(problem, populationSize, maxIterations, crossoverOperator, mutationOperator, selectionOperator, evaluator);
  }

  @Override
  protected List<MSASolution> createInitialPopulation() {
    return ((MSAProblem) getProblem()).createInitialPopulation(getMaxPopulationSize());
  }
}
