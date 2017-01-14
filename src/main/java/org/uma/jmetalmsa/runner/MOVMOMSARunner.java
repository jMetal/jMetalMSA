package org.uma.jmetalmsa.runner;

import java.util.ArrayList;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetalmsa.algorithm.movmomsa.MOVMOMSABuilder;
import org.uma.jmetalmsa.crossover.SPXMSACrossover;
import org.uma.jmetalmsa.mutation.ShiftClosedGapsMSAMutation;
import org.uma.jmetalmsa.problem.BAliBASE_MSAProblem;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.score.Score;
import org.uma.jmetalmsa.score.impl.PercentageOfAlignedColumnsScore;
import org.uma.jmetalmsa.score.impl.SumOfPairsScore;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.util.distancematrix.impl.PAM250;

/**
 * This class is the main program used to configure and run MOVMO
 *   @author R. Villarroel, K. Onofre, C. Guanoquiza
 */
public class MOVMOMSARunner {

  public static void main(String[] args) throws Exception {
      
    MSAProblem problem;
    Algorithm<List<MSASolution>> algorithm;
    CrossoverOperator<MSASolution> crossover;
    MutationOperator<MSASolution> mutation;
    SelectionOperator selection;

//    if (args.length != 4) {
//      throw new JMetalException("Wrong number of arguments") ;
//    }

//    String problemName = args[0];
//    String dataDirectory = args[1];
//    Integer maxEvaluations = Integer.parseInt(args[2]);
//    Integer populationSize = Integer.parseInt(args[3]);

    String problemName = "BB11001";
    String dataDirectory = "C:/msa";
    Integer maxEvaluations = 5000;
    Integer populationSize = 100;

    crossover = new SPXMSACrossover(0.8);
    mutation = new ShiftClosedGapsMSAMutation(1);
    selection = new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator());

    List<Score> scoreList = new ArrayList<>();

    scoreList.add(new SumOfPairsScore(new PAM250()));
    scoreList.add(new PercentageOfAlignedColumnsScore());

    problem = new BAliBASE_MSAProblem(problemName, dataDirectory, scoreList);

    SolutionListEvaluator<MSASolution> evaluator;

    evaluator = new SequentialSolutionListEvaluator<>();

    algorithm = new MOVMOMSABuilder(problem,crossover, mutation, selection)
                .setMaxEvaluations(maxEvaluations)
                .setPopulationSize(populationSize)
                .setNeighborhoodSize(4)
                .setArchiveSize(100)
                .setBeta(0.0)
                .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute();

    List<MSASolution> population = algorithm.getResult();
    long computingTime = algorithmRunner.getComputingTime();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    
    for (MSASolution solution : population) {
      for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
        if (!scoreList.get(i).isAMinimizationScore()) {
          solution.setObjective(i, -1.0 * solution.getObjective(i));
        }
      }
    }
       
    DefaultFileOutputContext varFile = new  DefaultFileOutputContext("VAR." + problemName +"." + algorithm.getName()+ ".tsv");
    varFile.setSeparator("\n");
    DefaultFileOutputContext funFile = new  DefaultFileOutputContext("FUN." + problemName +"." + algorithm.getName()+ ".tsv");
    funFile.setSeparator("\t");

   
    new SolutionListOutput(population)
            .setVarFileOutputContext(varFile)
            .setFunFileOutputContext(funFile)
            .print();
    evaluator.shutdown();

  }
}
