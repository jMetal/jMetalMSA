package org.uma.jmetalmsa.algorithm.spea2;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetalmsa.solution.MSASolution;

/**
 * Created by ajnebro on 3/7/15.
 */
public class SPEA2MSABuilder extends SPEA2Builder<MSASolution> {
  /**
   * SPEA2MSABuilder class
   */
  /**
   * SPEA2MSABuilder constructor
   */
  public SPEA2MSABuilder(Problem<MSASolution> problem, CrossoverOperator<MSASolution> crossoverOperator,
      MutationOperator<MSASolution> mutationOperator) {
    super(problem, crossoverOperator, mutationOperator);

  }

  public SPEA2MSA build() {
    SPEA2MSA algorithm = null ;
    algorithm = new SPEA2MSA(problem, maxIterations, populationSize, crossoverOperator,
        mutationOperator, selectionOperator, evaluator);

    return algorithm ;
  }
}
