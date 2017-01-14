package org.uma.jmetalmsa.algorithm.moead;

import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.algorithm.multiobjective.moead.util.MOEADUtils;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.ArrayList;
import java.util.List;


public class MOEADMSA extends AbstractMOEAD<MSASolution> {


  public MOEADMSA(Problem<MSASolution> problem,
                  int populationSize,
                  int resultPopulationSize,
                  int maxEvaluations,
                  MutationOperator<MSASolution> mutation,
                  CrossoverOperator<MSASolution> crossover,
                  FunctionType functionType,
                  String dataDirectory,
                  double neighborhoodSelectionProbability,
                  int maximumNumberOfReplacedSolutions,
                  int neighborSize) {
    super(problem, populationSize, resultPopulationSize, maxEvaluations, crossover, mutation, functionType,
            dataDirectory, neighborhoodSelectionProbability, maximumNumberOfReplacedSolutions,
            neighborSize);

    randomGenerator = JMetalRandom.getInstance();
  }

  @Override
  public void run() {
    initializePopulation();
    initializeUniformWeight();
    initializeNeighborhood();
    initializeIdealPoint();

    evaluations = populationSize;
    do {
      int[] permutation = new int[populationSize];
      MOEADUtils.randomPermutation(permutation, populationSize);

      for (int i = 0; i < populationSize; i++) {
        int subProblemId = permutation[i];

        NeighborType neighborType = chooseNeighborType();
        List<MSASolution> parents = parentSelection(subProblemId, neighborType);

        List<MSASolution> children = crossoverOperator.execute(parents);

        MSASolution child = children.get(0);
        mutationOperator.execute(child);
        problem.evaluate(child);

        evaluations++;

        updateIdealPoint(child);
        updateNeighborhood(child, subProblemId, neighborType);
      }
    } while (evaluations < maxEvaluations);
  }

  protected void initializePopulation() {
    population = ((MSAProblem) problem).createInitialPopulation(populationSize);
  }

  @Override
  public List<MSASolution> getResult() {
    return population;
  }

  protected List<MSASolution> parentSelection(int subProblemId, NeighborType neighborType) {
    List<Integer> matingPool = matingSelection(subProblemId, 2, neighborType);

    List<MSASolution> parents = new ArrayList<>(3);

    parents.add(population.get(matingPool.get(0)));
    parents.add(population.get(matingPool.get(1)));

    return parents;
  }

  /**
   * @param subproblemId  the id of current subproblem
   * @param neighbourType neighbour type
   */
  protected List<Integer> matingSelection(int subproblemId, int numberOfSolutionsToSelect, NeighborType neighbourType) {
    int neighbourSize;
    int selectedSolution;

    List<Integer> listOfSolutions = new ArrayList<>(numberOfSolutionsToSelect);

    neighbourSize = neighborhood[subproblemId].length;
    while (listOfSolutions.size() < numberOfSolutionsToSelect) {
      int random;
      if (neighbourType == NeighborType.NEIGHBOR) {
        random = randomGenerator.nextInt(0, neighbourSize - 1);
        selectedSolution = neighborhood[subproblemId][random];
      } else {
        selectedSolution = randomGenerator.nextInt(0, populationSize - 1);
      }
      boolean flag = true;
      for (Integer individualId : listOfSolutions) {
        if (individualId == selectedSolution) {
          flag = false;
          break;
        }
      }

      if (flag) {
        listOfSolutions.add(selectedSolution);
      }
    }

    return listOfSolutions;
  }

  @Override
  public String getName() {
    return "MOEAD";
  }

  @Override
  public String getDescription() {
    return "Version of MOEA/D for solving MSA problems";
  }
}
