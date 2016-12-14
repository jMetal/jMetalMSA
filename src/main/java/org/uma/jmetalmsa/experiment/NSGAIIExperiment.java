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

package org.uma.jmetalmsa.experiment;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.experiment.util.TaggedAlgorithm;

/**
 * Example of experimental study based on solving the ZDT problems with four versions of NSGA-II, each
 * of them applying a different crossover probability (from 0.7 to 1.0).
 *
 * This experiment assumes that the reference Pareto front are known, so the names of files containing
 * them and the directory where they are located must be specified.
 *
 * Six quality indicators are used for performance assessment.
 *
 * The steps to carry out the experiment are:
 * 1. Configure the experiment
 * 2. Execute the algorithms
 * 3. Compute the quality indicators
 * 4. Generate Latex tables reporting means and medians
 * 5. Generate Latex tables with the result of applying the Wilcoxon Rank Sum Test
 * 6. Generate Latex tables with the ranking obtained by applying the Friedman test
 * 7. Generate R scripts to obtain boxplots
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NSGAIIExperiment {
  
  private static int INDEPENDENT_RUNS=2;

  public static void main(String[] args) throws Exception {
//    //if (args.length != 7) {
//    //  throw new JMetalException("Needed arguments: experimentBaseDirectory dataBaseDirectory Algorithm RVGroup InitialProblem FinalProblem NumberOfCores") ;
//    //}
//    String experimentBaseDirectory = "/home/cristian/msa/msajmetalbiojava/src/main/resources";  //args[0] ;
//    String dataBaseDirectory = "/home/cristian/msa/msajmetalbiojava/src/main/resources"; //args[1];
//    //Algorithm = args[2];
//    String RV = "11"; //args[3] ;
//    Integer Start = 1; //Integer.parseInt(args[4]) ;
//    Integer End = 3; //Integer.parseInt(args[5]) ;
//    Integer NumCores = 4; //Integer.parseInt(args[6]) ;
//    
//    List<Problem<MSAArrayCharSolution>> problemList = new ArrayList<>();
//    String ProblemName;
//
//    for (int k=Start;k<=End;k++){
//      //if(k==5 || k==7 || k==8 || k==13 || k==14 || k==15 || k==20 || k==25 || k==30 || k==34){
//      ProblemName = "BB" + RV +"0";
//      if (k<10) ProblemName= ProblemName + "0";
//      ProblemName=ProblemName+k;
//      problemList.add(new MSAProblem(ProblemName,dataBaseDirectory, new PAM250b()));
//      //problemList.add(new MSAProblemStrike_TC(ProblemName,dataBaseDirectory));
//      //}
//    }
//
//    List<TaggedAlgorithm<List<MSAArrayCharSolution>>> algorithmList = configureAlgorithmList(problemList, INDEPENDENT_RUNS) ;
//
//    Experiment<MSAArrayCharSolution, List<MSAArrayCharSolution>> experiment =
//        new ExperimentBuilder<MSAArrayCharSolution, List<MSAArrayCharSolution>>("NSGAIIStudy")
//            .setAlgorithmList(algorithmList)
//            .setProblemList(problemList)
//            .setExperimentBaseDirectory(experimentBaseDirectory)
//            .setOutputParetoFrontFileName("FUN")
//            .setOutputParetoSetFileName("VAR")
//            .setReferenceFrontDirectory(experimentBaseDirectory + "/NSGAIIStudy/referenceFront")
//            .setIndicatorList(Arrays.asList(
//                new Epsilon<MSAArrayCharSolution>(), new Spread<MSAArrayCharSolution>(), new GenerationalDistance<MSAArrayCharSolution>(),
//                new PISAHypervolume<MSAArrayCharSolution>(),
//                new InvertedGenerationalDistance<MSAArrayCharSolution>(),
//                new InvertedGenerationalDistancePlus<MSAArrayCharSolution>())
//            )
//            .setIndependentRuns(INDEPENDENT_RUNS)
//            .setNumberOfCores(NumCores)
//            .build();
//
//    new ExecuteAlgorithms<>(experiment).run();
//    
//    new PrintMSAs(experiment).run();
//    
//    
////    new GenerateReferenceParetoFront(experiment).run();
////    new ComputeQualityIndicators<>(experiment).run() ;
////    new GenerateLatexTablesWithStatistics(experiment).run() ;
////    new GenerateWilcoxonTestTablesWithR<>(experiment).run() ;
////    new GenerateFriedmanTestTables<>(experiment).run();
////    new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(2).setDisplayNotch().run();
  }

  
   /**
   * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem} which form part of a
   * {@link TaggedAlgorithm}, which is a decorator for class {@link Algorithm}. The {@link TaggedAlgorithm}
   * has an optional tag component, that can be set as it is shown in this example, where four variants of a
   * same algorithm are defined.
   *
   * @param problemList
   * @return
   */
//   static List<TaggedAlgorithm<List<MSAArrayCharSolution>>> configureAlgorithmList(
//      List<Problem<MSAArrayCharSolution>> problemList,
//      int independentRuns) {
//    
//    List<TaggedAlgorithm<List<MSAArrayCharSolution>>> algorithms = new ArrayList<>() ;
//
//    for (int run = 0; run < independentRuns; run++) {
//
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new NSGAIIMSABuilder(problem,
//            new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2), NSGAIIVariant.NSGAII)
//            .setSelectionOperator(new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()))
//            .setMaxEvaluations(25000)
//            .setPopulationSize(100)
//            .build();
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new NSGAIIMSABuilder(problem,
//            new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2), NSGAIIVariant.SteadyStateNSGAII)
//            .setSelectionOperator(new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()))
//            .setMaxEvaluations(25000)
//            .setPopulationSize(100)
//            .build();
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//    }
//    return algorithms ;
//  }
//  
}