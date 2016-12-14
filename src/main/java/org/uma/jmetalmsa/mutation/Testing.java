/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uma.jmetalmsa.mutation;

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
public class Testing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        List<Score> scoreList = new ArrayList<>();
        StrikeScore objStrike = new StrikeScore();
        scoreList.add(objStrike);
        scoreList.add(new PercentageOfAlignedColumnsScore());
        scoreList.add(new PercentageOfNonGapsScore());
        BAliBASE_MSAProblem problem = new BAliBASE_MSAProblem("BB11002", "C:/msa", scoreList);
    
        List<ArrayChar> seqAligned =problem.readDataFromFastaFile(problem.PreComputedPath + ".tfa_clu");
        
        MergeAdjunctedGapsGroupsMSAMutation mutation = new MergeAdjunctedGapsGroupsMSAMutation (1);
        for(int i=1;i<=1000;i++){
            MSASolution sol = new MSASolution(seqAligned, problem);
            //System.out.println(sol.toString());
            mutation.execute(sol);
            sol.isValid();
            //System.out.println(sol.toString());
        }
        
    }
    
}
