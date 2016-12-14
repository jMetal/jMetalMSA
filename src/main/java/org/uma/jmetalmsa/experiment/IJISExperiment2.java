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
public class IJISExperiment2 {
  
  private static String Algorithm;

  public static void main(String[] args) throws Exception {
//    if (args.length != 7) {
//      throw new JMetalException("Needed arguments: experimentBaseDirectory dataBaseDirectory Algorithm RVGroup Problem NumberOfExecutions NumberOfCores") ;
//    }
//    String experimentBaseDirectory = args[0] ;
//    String dataBaseDirectory = args[1];
//    Algorithm = args[2];
//    String RV = args[3] ;
//    RV= RV.substring(2,4);
//    String Instance = args[4] ;
//    Integer NumExes = Integer.parseInt(args[5]) ;
//    Integer NumCores = Integer.parseInt(args[6]) ;
//    
//    List<Problem<MSAArrayCharSolution>> problemList = new ArrayList<>();
//
//    problemList.add(new MSAProblem(Instance, dataBaseDirectory, new PAM250b()));
//
//    List<TaggedAlgorithm<List<MSAArrayCharSolution>>> algorithmList = configureAlgorithmList(problemList, NumExes) ;
//
//    Experiment<MSAArrayCharSolution, List<MSAArrayCharSolution>> experiment =
//        new ExperimentBuilder<MSAArrayCharSolution, List<MSAArrayCharSolution>>("Study2")
//            .setAlgorithmList(algorithmList)
//            .setProblemList(problemList)
//            .setExperimentBaseDirectory(experimentBaseDirectory)
//            .setOutputParetoFrontFileName("FUN")
//            .setOutputParetoSetFileName("VAR")
//            .setReferenceFrontDirectory(experimentBaseDirectory + "/Study2/referenceFront" )
//            .setIndicatorList(Arrays.asList(
//                new Epsilon<MSAArrayCharSolution>(), new Spread<MSAArrayCharSolution>(), new GenerationalDistance<MSAArrayCharSolution>(),
//                new PISAHypervolume<MSAArrayCharSolution>(),
//                new InvertedGenerationalDistance<MSAArrayCharSolution>(),
//                new InvertedGenerationalDistancePlus<MSAArrayCharSolution>())
//            )
//            .setIndependentRuns(NumExes)
//            .setNumberOfCores(NumCores)
//            .build();
//
//    new ExecuteAlgorithms<>(experiment).run();
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
//  static List<TaggedAlgorithm<List<MSAArrayCharSolution>>> configureAlgorithmList(
//      List<Problem<MSAArrayCharSolution>> problemList,
//      int independentRuns) {
//    
//    List<TaggedAlgorithm<List<MSAArrayCharSolution>>> algorithms = new ArrayList<>() ;
//
//    for (int run = 0; run < independentRuns; run++) {
//
//     if(Algorithm.equals("NSGAII")){
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new NSGAIIMSABuilder(problem,
//            new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2), NSGAIIVariant.NSGAII)
//            .setSelectionOperator(new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()))
//            .setMaxEvaluations(50000)
//            .setPopulationSize(100)
//            .build();
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//      }
//
//       if(Algorithm.equals("SPEA2")){
//
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new SPEA2MSABuilder(problem,
//            new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2))
//            .setSelectionOperator(new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()))
//            .setMaxIterations(500)
//            .setPopulationSize(100)
//            .build();
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//      }
//
//      if(Algorithm.equals("MOCELL")){
//
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new MOCellMSABuilder(problem,
//            new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2))
//            .setSelectionOperator(new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()))
//            .setMaxEvaluations(50000)
//            .setPopulationSize(100)
//            .setArchiveSize(100)
//            .setNeighborhood(new L5<MSAArrayCharSolution>(10, 10))
//            .build();
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//      }
//
//    if(Algorithm.equals("MOEADMSA")){
//
//        for (Problem<MSAArrayCharSolution>  problem : problemList) {
//
//           Algorithm<List<MSAArrayCharSolution>> algorithm = new MOEADMSABuilder(problem, MOEADMSABuilder.Variant.MOEAD)
//             .setCrossover(new SinglePointMSACrossover(0.8))
//             .setMutation(new ShiftClosedGapsMSAMutation(0.2))
//             .setMaxEvaluations(50000)
//             .setPopulationSize(100)
//             .setResultPopulationSize(100)
//             .setNeighborhoodSelectionProbability(0.9)
//             .setMaximumNumberOfReplacedSolutions(2)
//             .setNeighborSize(20)
//             .setFunctionType(AbstractMOEAD.FunctionType.TCHE)
//             .setDataDirectory("MOEAD_Weights")
//             .build() ;
//
//          algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem,run));
//        }
//     }
//
//     if(Algorithm.equals("SMS-EMOA")){
//
//      Hypervolume<MSAArrayCharSolution> hypervolume;
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//        hypervolume = new PISAHypervolume<>();
//        hypervolume.setOffset(100.0);
//
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new SMSEMOAMSABuilder(problem, new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2),
//            hypervolume)
//            .setSelectionOperator(new RandomSelection<MSAArrayCharSolution>())
//            .setMaxEvaluations(50000)
//            .setPopulationSize(100)
//            .build();
//
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//      }
//
//      if(Algorithm.equals("GWASFGA")){
//
//      for (Problem<MSAArrayCharSolution> problem : problemList) {
//
//        Algorithm<List<MSAArrayCharSolution>> algorithm = new GWASFGAMSA(problem, 100, 500, new SinglePointMSACrossover(0.8), new ShiftClosedGapsMSAMutation(0.2), new BinaryTournamentSelection(new RankingAndCrowdingDistanceComparator()),
//            new SequentialSolutionListEvaluator<MSAArrayCharSolution>());
//
//        algorithms.add(new TaggedAlgorithm<List<MSAArrayCharSolution>>(algorithm, problem, run));
//      }
//
//      }
//
//    }
//    return algorithms ;
//  }
  
}