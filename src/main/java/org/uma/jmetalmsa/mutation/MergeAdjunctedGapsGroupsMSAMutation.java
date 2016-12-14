package org.uma.jmetalmsa.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

public class MergeAdjunctedGapsGroupsMSAMutation implements MutationOperator<MSASolution> {
  private double mutationProbability;
  private JMetalRandom randomGenerator;


  public MergeAdjunctedGapsGroupsMSAMutation(double mutationProbability) {
    if ((mutationProbability < 0) || (mutationProbability > 1)) {
      throw new JMetalException("Mutation probability value invalid: " + mutationProbability);
    }
    this.mutationProbability = mutationProbability;

    randomGenerator = JMetalRandom.getInstance();
  }

  @Override
  public MSASolution execute(MSASolution solution) {
    if (null == solution) {
      throw new JMetalException("Null parameter");
    }

    doMutation(solution);
    return solution;
  }

  /**
   * Performs the operation
   */
  public void doMutation(MSASolution solution) {
    if (randomGenerator.nextDouble() < mutationProbability) {
       
       int selectedSequence=getSelectedSequenceWithMoreThanOneGapsGroups(solution);
       //System.out.println(selectedSequence);
       List<Integer> gapsGroup = solution.getVariableValue(selectedSequence);
       //System.out.println(gapsGroup.toString());
       int selectedGapsGroup= randomGenerator.nextInt(0, gapsGroup.size()/2-2);
       selectedGapsGroup*=2;
       //System.out.println(selectedGapsGroup);
       int numberOfGaps= gapsGroup.get(selectedGapsGroup+1)-gapsGroup.get(selectedGapsGroup)+1;
       gapsGroup.set(selectedGapsGroup+2, gapsGroup.get(selectedGapsGroup+2)-numberOfGaps);
       
       gapsGroup.remove(selectedGapsGroup); gapsGroup.remove(selectedGapsGroup);
       
       //System.out.println(gapsGroup.toString());
       solution.setVariableValue(selectedSequence,gapsGroup);
       
       solution.mergeGapsGroups();
    }
  }

 public int getSelectedSequenceWithMoreThanOneGapsGroups(MSASolution solution) {
    int selectedSequence = -1;
    do {
      selectedSequence = randomGenerator.nextInt(0, solution.getNumberOfVariables() - 1);
    }
    while (!hasMoreThanOneGapsGroups(solution.getVariableValue(selectedSequence)));

    return selectedSequence;
  }

  public boolean hasMoreThanOneGapsGroups(List<Integer> gapsGroup) {
     return gapsGroup.size()>2;
  }
  
  
  public double getMutationProbability() {
    return mutationProbability;
  }
}

