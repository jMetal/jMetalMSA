package org.uma.jmetalmsa.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

public class MultipleMSAMutation implements MutationOperator<MSASolution> {
  private double mutationProbability;
  private JMetalRandom randomGenerator;
 private List<MutationOperator<MSASolution>> mutationOperatorList ;


  public MultipleMSAMutation(double mutationProbability,  
          List<MutationOperator<MSASolution>> mutationOperatorList) {
    if ((mutationProbability < 0) || (mutationProbability > 1)) {
      throw new JMetalException("Mutation probability value invalid: " + mutationProbability);
    }else if (null == mutationOperatorList) {
      throw new JMetalException("The operator list is null") ;
    }
   
    this.mutationProbability = mutationProbability;
    this.mutationOperatorList = mutationOperatorList ;
    randomGenerator = JMetalRandom.getInstance();
  }

  @Override
  public MSASolution execute(MSASolution solution) {
    if (null == solution) {
      throw new JMetalException("Null parameter");
    }

    if (randomGenerator.nextDouble() < mutationProbability) {
      for (MutationOperator<MSASolution> mutation : mutationOperatorList) {
         solution = mutation.execute(solution) ;
      }
    }
    
    return solution;
}

  

  public double getMutationProbability() {
    return mutationProbability;
  }
}

