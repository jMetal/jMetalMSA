package org.uma.jmetalmsa.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

public class SplitANonGapsGroupMSAMutation implements MutationOperator<MSASolution> {
  private double mutationProbability;
  private JMetalRandom randomGenerator;
 

  public SplitANonGapsGroupMSAMutation(double mutationProbability) {
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
           newGapGroupWithARandomGap = insertARandomGapBetweenNonGapsGroup(solution.getVariableValue(i),sizeOfTheAlignment);
           solution.setVariableValue(i,newGapGroupWithARandomGap);
      }
       
      solution.mergeGapsGroups();
    }
  }



  public List<Integer>  insertARandomGapBetweenNonGapsGroup(List<Integer> gapsGroup, int sizeOfTheAlignment) {
    Integer posRandomGap;
   // System.out.println(gapsGroup.toString());
    int posGapGroup;
    do{
        posRandomGap = randomGenerator.nextInt(0, sizeOfTheAlignment-1);
        posGapGroup=isPosBetweenGapGroup(posRandomGap, gapsGroup );
    }while(posGapGroup>-1);
    
    if(posGapGroup<-1) 
        posGapGroup*=-1;
    
    //System.out.println(posRandomGap);
        
    if(posGapGroup>-1){
        gapsGroup.add(posGapGroup,posRandomGap); gapsGroup.add(posGapGroup,posRandomGap);
        posGapGroup+=2;
        while(posGapGroup<gapsGroup.size()){
            gapsGroup.set(posGapGroup,gapsGroup.get(posGapGroup)+1);
            posGapGroup++;
        }
    }else{
        gapsGroup.add(posRandomGap); gapsGroup.add(posRandomGap);
    }
    
    //System.out.println(gapsGroup.toString());
    return gapsGroup;
  }

  public int isPosBetweenGapGroup(int posRandomGap, List<Integer> gapsGroup ){

    for (int i = 0; i < gapsGroup.size() - 1; i += 2) {
          if(gapsGroup.get(i) > posRandomGap){
              return i*-1;
          }
           
          if (posRandomGap>=gapsGroup.get(i)  && posRandomGap<=gapsGroup.get(i + 1)) {
                return i;
          }  
          
       
     }

   return -1;
  }    

  public double getMutationProbability() {
    return mutationProbability;
  }
}

