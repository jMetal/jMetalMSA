package org.uma.jmetalmsa.algorithm.smsemoa;

import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetalmsa.solution.MSASolution;

/**
 * Created by ajnebro on 3/7/15.
 */
public class SMSEMOAMSABuilder extends SMSEMOABuilder<MSASolution> {

  public SMSEMOAMSABuilder(Problem<MSASolution> problem, CrossoverOperator<MSASolution> crossoverOperator,
      MutationOperator<MSASolution> mutationOperator) {
    super(problem, crossoverOperator, mutationOperator) ;
    
  }

  @Override public SMSEMOASMA build() {
    return new SMSEMOASMA(problem, maxEvaluations, populationSize, offset,
        crossoverOperator, mutationOperator, selectionOperator,
        hypervolumeImplementation);
  }
}
