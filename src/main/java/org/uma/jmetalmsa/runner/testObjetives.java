/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uma.jmetalmsa.runner;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetalmsa.score.impl.PercentageOfAlignedColumnsScore;
import org.uma.jmetalmsa.score.impl.PercentageOfNonGapsScore;
import org.uma.jmetalmsa.score.impl.StrikeScore;
import org.uma.jmetalmsa.problem.BAliBASE_MSAProblem;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.solution.util.ArrayChar;
import org.uma.jmetalmsa.score.Score;

/**
 *
 * @author cristian
 */
public class testObjetives {

    static BAliBASE_MSAProblem problem;
   

     public static void main(String[] args) throws Exception {
             

     EvaluaGroup(1,"BB110");
    
      
       

    }

    public static void EvaluaGroup(int Limit, String Instance_) throws Exception{

     
     for(int i=1;i<=Limit;i++){
            String Instance=Instance_;
            if(i<10)  Instance = Instance +"0";
            Instance = Instance + i;
            List<Score> scoreList = new ArrayList<>() ;

            StrikeScore objStrike = new StrikeScore();
            scoreList.add(objStrike) ;
            scoreList.add(new PercentageOfAlignedColumnsScore()) ;
            scoreList.add(new PercentageOfNonGapsScore());
            problem = new  BAliBASE_MSAProblem(Instance, "/home/cristian/msa/msajmetalbiojava", scoreList) ;
         
            objStrike.initializeParameters(problem.PDBPath, problem.getListOfSequenceNames());
            
            EvaluaAlig(problem.PreComputedPath + ".tfa_clu");
            EvaluaAlig(problem.PreComputedPath + ".tfa_muscle");
            EvaluaAlig(problem.PreComputedPath + ".tfa_kalign");
            EvaluaAlig(problem.PreComputedPath + ".tfa_retalign");
            EvaluaAlig(problem.PreComputedPath + ".fasta_aln");
            EvaluaAlig(problem.PreComputedPath + ".tfa_probcons");
            EvaluaAlig(problem.PreComputedPath + ".tfa_mafft");
            EvaluaAlig(problem.PreComputedPath + ".tfa_fsa");
            EvaluaAlig(problem.PreComputedPath + ".msf_tfa");
     }
    }
                
   public static void EvaluaAlig(String Fichero ) throws Exception{
       List<ArrayChar> strAlignment = problem.readDataFromFastaFile(Fichero);
       MSASolution s=new MSASolution(strAlignment,problem);
     
       problem.evaluate(s);
       System.out.println(Fichero + "\t" + s.getObjective(0)*-1  +  "\t"  +  + s.getObjective(1)*-1  +  "\t"
                        + s.getObjective(2)*-1  +  "\t");
       
   }
    
    
                
  

}
