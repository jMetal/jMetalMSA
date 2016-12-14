package org.uma.jmetalmsa.algorithm.nsgaIII;

import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;


/** Builder class */
public class NSGAIIIMSABuilder extends NSGAIIIBuilder<MSASolution> {

  public NSGAIIIMSABuilder(Problem<MSASolution> problem) {
    super(problem);
  }

  public NSGAIIIMSA build() {
    return new NSGAIIIMSA(this) ;
  }
}
