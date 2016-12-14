package org.uma.jmetalmsa.algorithm.spea2;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

public class SPEA2MSA extends SPEA2<MSASolution> {

  public SPEA2MSA(Problem<MSASolution> problem, int maxIterations,
                  int populationSize, CrossoverOperator<MSASolution> crossoverOperator,
                  MutationOperator<MSASolution> mutationOperator,
                  SelectionOperator<List<MSASolution>, MSASolution> selectionOperator,
                  SolutionListEvaluator<MSASolution> evaluator) {
    super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator,
            selectionOperator, evaluator);
  }

  @Override
  protected List<MSASolution> createInitialPopulation() {
    return ((MSAProblem) getProblem()).createInitialPopulation(getMaxPopulationSize());
  }

  @Override
  public String getName() {
    return "SPEA2";
  }

  @Override
  public String getDescription() {
    return "SPEA2";
  }
}
