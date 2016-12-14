package org.uma.jmetalmsa.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

public class InsertARandomGapMSAMutation implements MutationOperator<MSASolution> {
  private double mutationProbability;
  private JMetalRandom randomGenerator;

  public InsertARandomGapMSAMutation(double mutationProbability) {
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
       
       int sizeOfTheAlignment=solution.getAlignmentLength();
       List<Integer> newGapGroupWithARandomGap;
       for(int i=0; i<solution.getNumberOfVariables();i++) {
           newGapGroupWithARandomGap = insertARandomGap(solution.getVariableValue(i),sizeOfTheAlignment);
           solution.setVariableValue(i,newGapGroupWithARandomGap);
      }
      
       solution.mergeGapsGroups();
    }
  }



  public List<Integer>  insertARandomGap(List<Integer> gapsGroup, int sizeOfTheAlignment) {
    Integer posRandomGap;
    //System.out.println(gapsGroup.toString());
    posRandomGap = randomGenerator.nextInt(0, sizeOfTheAlignment-1);
    //System.out.println(posRandomGap);
    int i=0;
    boolean added=false;
    for (i = 0; i < gapsGroup.size() - 1; i += 2) {
        if(gapsGroup.get(i)>posRandomGap){
            gapsGroup.add(i,posRandomGap); gapsGroup.add(i,posRandomGap);
            i+=2;
            added=true;
            break;
        }
        if (posRandomGap>=gapsGroup.get(i)  && posRandomGap<=gapsGroup.get(i + 1)) {
            i++;
            added=true;
            break;
        }         
    }
    if(added){
        while(i<gapsGroup.size()){
            gapsGroup.set(i,gapsGroup.get(i)+1);
            i++;
        }
    }else{
         gapsGroup.add(posRandomGap); gapsGroup.add(posRandomGap);
    }
    //System.out.println(gapsGroup.toString());
    return gapsGroup;
  }


  public double getMutationProbability() {
    return mutationProbability;
  }
}

