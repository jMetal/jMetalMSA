//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetalmsa.runner;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.qualityindicator.impl.hypervolume.WFGHypervolume;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.archive.impl.HypervolumeArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.neighborhood.impl.L5;
import org.uma.jmetalmsa.algorithm.mocell.MOCellMSABuilder;
import org.uma.jmetalmsa.crossover.SPXMSACrossover;
import org.uma.jmetalmsa.mutation.ShiftClosedGapsMSAMutation;
import org.uma.jmetalmsa.score.impl.PercentageOfAlignedColumnsScore;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetalmsa.score.impl.PercentageOfNonGapsScore;
import org.uma.jmetalmsa.score.impl.SumOfPairsScore;
import org.uma.jmetalmsa.problem.BAliBASE_MSAProblem;
import org.uma.jmetalmsa.problem.Standard_MSAProblem;
import org.uma.jmetalmsa.util.distancematrix.impl.PAM250;
import org.uma.jmetalmsa.score.Score;


/**
 * Class to configure and run the MOCell algorithm
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class MOCellRunner {
  /**
   * Arguments: problemName dataDirectory maxEvaluations populationSize numberOfCores
   * @param args Command line arguments.
   */
  public static void main(String[] args) throws Exception {
    Standard_MSAProblem problem;
    Algorithm<List<MSASolution>> algorithm;
    CrossoverOperator<MSASolution> crossover;
    MutationOperator<MSASolution> mutation;
    SelectionOperator selection;

    if (args.length != 5) {
      throw new JMetalException("Wrong number of arguments") ;
    }

    String msaFile = args[0];
    String dataDirectory = args[1];
    String preComputedAlignments = args[2];
    Integer maxEvaluations = Integer.parseInt(args[3]);
    Integer populationSize = Integer.parseInt(args[4]);
    
    crossover = new SPXMSACrossover(0.8);
    mutation = new ShiftClosedGapsMSAMutation(0.2);
    selection = new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator());

    List<Score> scoreList = new ArrayList<>();

    scoreList.add(new SumOfPairsScore(new PAM250()));
    scoreList.add(new PercentageOfAlignedColumnsScore());
    scoreList.add(new PercentageOfNonGapsScore());

    problem = new Standard_MSAProblem(msaFile, dataDirectory, preComputedAlignments, scoreList);

    SolutionListEvaluator<MSASolution> evaluator;

    evaluator = new SequentialSolutionListEvaluator<>();

    algorithm = new MOCellMSABuilder(problem, crossover, mutation)
            .setArchive(new HypervolumeArchive<>(populationSize, new WFGHypervolume<MSASolution>()))
            //.setArchive(new NewCrowdingDistanceArchive<>(populationSize))
            //.setArchive(new CrowdingDistanceArchive<>(populationSize))
            .setSelectionOperator(selection)
            .setMaxEvaluations(maxEvaluations)
            .setPopulationSize(populationSize)
            .setNeighborhood(new L5<MSASolution>(10, 10))
            .setSolutionListEvaluator(evaluator)
            .build() ;

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
       
    DefaultFileOutputContext varFile = new  DefaultFileOutputContext("VAR.tsv");
    varFile.setSeparator("\n");
    DefaultFileOutputContext funFile = new  DefaultFileOutputContext("FUN.tsv");
    funFile.setSeparator("\t");

   
    new SolutionListOutput(population)
            .setVarFileOutputContext(varFile)
            .setFunFileOutputContext(funFile)
            .print();
    
    evaluator.shutdown();
  }
}
