package org.uma.jmetalmsa.algorithm.movmomsa.util;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.SolutionUtils;

public class Util<S extends Solution<?>> {
	
	public double sharedDist(double min, double max, int evaluations, int maxEvaluations) {

		if (100 * evaluations < 15 * maxEvaluations)
			return range(min, max) / 4;

		if (100 * evaluations < 30 * maxEvaluations)
			return range(min, max) / 8;

		if (100 * evaluations < 60 * maxEvaluations)
			return range(min, max) / 16;

		if (100 * evaluations < 80 * maxEvaluations)
			return range(min, max) / 50;

		return range(min, max) / 100;
	}

	public double range(double a, double b) {
		return Math.abs(a - b);
	}
	
	public double ave(double a, double b){
		return (a + b )/ 2;
	}
	
	public double probabilityByIndividuals(S a, S b) throws JMetalException {
		double d = this.distanceBetweenObjectives(a, b);
		return 1.0 / (1.0 + d);
	}
	
	
	
	public static double distanceBetweenSolutions(DoubleSolution solutionI, DoubleSolution solutionJ) {
        double distance = 0.0;

        double diff;    //Auxiliar var
        //-> Calculate the Euclidean distance
        for (int i = 0; i < solutionI.getNumberOfVariables() ; i++){
            diff = solutionI.getVariableValue(i) - solutionJ.getVariableValue(i);
            distance += Math.pow(diff,2.0);
        } // for
        //-> Return the euclidean distance
        return Math.sqrt(distance);
    } // distanceBetweenSolutions
	
	
	
	//NOTAS debe tratar de usar este metdo desde la misma clase org.uma.jmetal.util.SolutionUtils
	
	/**
	   * Returns the euclidean distance between a pair of solutions in the objective space
	   * @param firstSolution
	   * @param secondSolution
	   * @return
	   */
	private double distanceBetweenObjectives(S firstSolution, S secondSolution) {
	   
		    double diff;  
		    double distance = 0.0;
		    //euclidean distance
		    for (int nObj = 0; nObj < firstSolution.getNumberOfObjectives();nObj++){
		      diff = firstSolution.getObjective(nObj) - secondSolution.getObjective(nObj);
		      distance += Math.pow(diff,2.0);           
		    } // for   
		        
		    return Math.sqrt(distance);
	  }

}
