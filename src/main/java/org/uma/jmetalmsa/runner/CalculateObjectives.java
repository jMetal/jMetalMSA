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
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.solution.util.ArrayChar;
import org.uma.jmetalmsa.score.Score;

/**
 *
 * @author Cristian
 */
public class CalculateObjectives {

    /**
     * @param args the command line arguments
     */
    
    
   static String  dataDirectory = "C:/msa";
   static double Resultados[][] = new double[9][3];
    public static void main(String[] args) throws Exception{
        
        for(int i=0;i<=8;i++)
            for(int j=0;j<=2;j++)
                Resultados[i][j]=0;
        
        EvalGroup("11",38);
        EvalGroup("12",44);
        EvalGroup("20",41);
        EvalGroup("30",30);
        EvalGroup("40",49);
        EvalGroup("50",16);
        
         for(int i=0;i<=8;i++)
              System.out.println(Resultados[i][0] + "\t"  + Resultados[i][1] + "\t" + Resultados[i][2]);
    }
    
    public static void EvalGroup(String Group, int Limit) throws Exception{
        
        for (int i=1;i<=Limit;i++){
            String Instance="BB" + Group  + "0";
            if (i<10) {
              Instance=Instance+"0";
            }
             Instance=Instance +  i ;
             
             EvaluaInstance(Instance);
                                       
        }
        
    }
    
     public static void EvaluaAlig(String Fichero , BAliBASE_MSAProblem problem, int fila) throws Exception{
         
        List<ArrayChar> seqAligned =problem.readDataFromFastaFile(Fichero);
        MSASolution sol = new MSASolution(seqAligned, problem);
        
        problem.evaluate(sol);
        System.out.println(Fichero + "\t" + -1.0*sol.getObjective(0) + "\t"  + -1.0*sol.getObjective(1) + "\t" + -1.0*sol.getObjective(2));
       
        if (!Double.isNaN(sol.getObjective(0)))
           Resultados[fila][0]=Resultados[fila][0]+(-1.0*sol.getObjective(0));
        if (!Double.isNaN(sol.getObjective(1)))
            Resultados[fila][1]=Resultados[fila][1]+(-1.0*sol.getObjective(1));
        if (!Double.isNaN(sol.getObjective(2)))
            Resultados[fila][2]=Resultados[fila][2]+(-1.0*sol.getObjective(2));
   }
     
     
      public static void EvaluaInstance(String Instance_) throws Exception{
        
        BAliBASE_MSAProblem problem;
        List<Score> scoreList = new ArrayList<>();
        StrikeScore objStrike = new StrikeScore();
        scoreList.add(objStrike);
        scoreList.add(new PercentageOfAlignedColumnsScore());
        scoreList.add(new PercentageOfNonGapsScore());
        problem = new BAliBASE_MSAProblem(Instance_, dataDirectory, scoreList);

        objStrike.initializeParameters(problem.PDBPath, problem.getListOfSequenceNames());
    
        EvaluaAlig(problem.PreComputedPath + ".tfa_clu",problem,0);
        EvaluaAlig(problem.PreComputedPath + ".tfa_muscle",problem,1);
        EvaluaAlig(problem.PreComputedPath + ".tfa_kalign",problem,2);
        EvaluaAlig(problem.PreComputedPath + ".tfa_retalign",problem,3);
        EvaluaAlig(problem.PreComputedPath + ".fasta_aln",problem,4);
        EvaluaAlig(problem.PreComputedPath + ".tfa_probcons",problem,5);
        EvaluaAlig(problem.PreComputedPath + ".tfa_mafft",problem,6);
        EvaluaAlig(problem.PreComputedPath + ".tfa_fsa",problem,7);
        EvaluaAlig(problem.PreComputedPath + ".msf_tfa",problem,8);
        //EvaluaAlig(problem.PreComputedPath + ".fasta",problem);
    }
}
