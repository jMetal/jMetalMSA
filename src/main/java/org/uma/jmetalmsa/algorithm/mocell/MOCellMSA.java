package org.uma.jmetalmsa.algorithm.mocell;

import org.uma.jmetal.algorithm.multiobjective.mocell.MOCell;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.Neighborhood;
import org.uma.jmetal.util.solutionattribute.impl.LocationAttribute;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
public class MOCellMSA extends MOCell<MSASolution> {

  /**
   * Constructor
   */
  public MOCellMSA(Problem<MSASolution> problem, BoundedArchive<MSASolution> archive, Neighborhood<MSASolution> neighborhood, int maxIterations, int populationSize,
                   CrossoverOperator<MSASolution> crossoverOperator,
                   MutationOperator<MSASolution> mutationOperator,
                   SelectionOperator<List<MSASolution>, MSASolution> selectionOperator,
                   SolutionListEvaluator<MSASolution> evaluator) {
    super(problem, maxIterations, populationSize, archive, neighborhood, crossoverOperator, mutationOperator, selectionOperator, evaluator);
  }

  @Override
  protected List<MSASolution> createInitialPopulation() {
    List<MSASolution> population = ((MSAProblem) getProblem()).createInitialPopulation(getMaxPopulationSize());
    for (MSASolution solution : population)
      archive.add(solution);

    location = new LocationAttribute<>(population);
    return population;
  }

  @Override
  public String getName() {
    return "MOCell";
  }

  @Override
  public String getDescription() {
    return "MOCell";
  }
}
