package org.uma.jmetalmsa.algorithm.mocell;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.neighborhood.Neighborhood;
import org.uma.jmetal.util.neighborhood.impl.C9;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

/**
 * Created by juanjo
 *  */
public class MOCellMSABuilder implements AlgorithmBuilder<MOCellMSA> {

  /**
   * MOCellBuilder class
   */
  private final Problem<MSASolution> problem;
  private int maxEvaluations;
  private int populationSize;
  private int archiveSize ;
  private CrossoverOperator<MSASolution> crossoverOperator;
  private MutationOperator<MSASolution> mutationOperator;
  private SelectionOperator<List<MSASolution>,MSASolution> selectionOperator;
  private SolutionListEvaluator<MSASolution> evaluator;
  private Neighborhood<MSASolution> neighborhood ;
  private BoundedArchive<MSASolution> archive ;

  /**
   * MOCellBuilder constructor
   */
  public MOCellMSABuilder(Problem<MSASolution> problem,
                          CrossoverOperator<MSASolution> crossoverOperator,
                          MutationOperator<MSASolution> mutationOperator) {
    this.problem = problem;
    maxEvaluations = 25000;
    populationSize = 100;
    archiveSize = 100 ;
    this.crossoverOperator = crossoverOperator ;
    this.mutationOperator = mutationOperator ;
    selectionOperator = new BinaryTournamentSelection<MSASolution>(new RankingAndCrowdingDistanceComparator<MSASolution>());
    this.neighborhood = new C9<MSASolution>((int)Math.sqrt(populationSize), (int)Math.sqrt(populationSize)) ;
    evaluator = new SequentialSolutionListEvaluator<MSASolution>();
  }

  public MOCellMSABuilder setMaxEvaluations(int maxEvaluations) {
    if (maxEvaluations < 0) {
      throw new JMetalException("maxEvaluations is negative: " + maxEvaluations);
    }
    this.maxEvaluations = maxEvaluations;

    return this;
  }

  public MOCellMSABuilder setPopulationSize(int populationSize) {
    if (populationSize < 0) {
      throw new JMetalException("Population size is negative: " + populationSize);
    }

    this.populationSize = populationSize;

    return this;
  }

  public MOCellMSABuilder setArchive(BoundedArchive<MSASolution> archive) {
    this.archive = archive ;

    return this;
  }
   
  public MOCellMSABuilder setArchiveSize(int archiveSize) {
    if (archiveSize < 0) {
      throw new JMetalException("archive size is negative: " + populationSize);
    }

    this.archiveSize = archiveSize;

    return this;
  }

  public MOCellMSABuilder setNeighborhood(Neighborhood<MSASolution> neighborhood) {
    this.neighborhood = neighborhood;

    return this;
  }

  public MOCellMSABuilder setSelectionOperator(SelectionOperator<List<MSASolution>,MSASolution> selectionOperator) {
    if (selectionOperator == null) {
      throw new JMetalException("selectionOperator is null");
    }
    this.selectionOperator = selectionOperator;

    return this;
  }

  public MOCellMSABuilder setSolutionListEvaluator(SolutionListEvaluator<MSASolution> evaluator) {
    if (evaluator == null) {
      throw new JMetalException("evaluator is null");
    }
    this.evaluator = evaluator;

    return this;
  }


  public MOCellMSA build() {
    return new MOCellMSA(problem, archive, neighborhood, maxEvaluations, populationSize,crossoverOperator, mutationOperator, selectionOperator, evaluator);
  }

  /* Getters */
  public Problem<MSASolution> getProblem() {
    return problem;
  }

  public int getMaxEvaluations() {
    return maxEvaluations;
  }

  public int getPopulationSize() {
    return populationSize;
  }

  public int getArchiveSize() {
    return archiveSize ;
  }

  public CrossoverOperator<MSASolution> getCrossoverOperator() {
    return crossoverOperator;
  }

  public MutationOperator<MSASolution> getMutationOperator() {
    return mutationOperator;
  }

  public SelectionOperator<List<MSASolution>,MSASolution> getSelectionOperator() {
    return selectionOperator;
  }

  public SolutionListEvaluator<MSASolution> getSolutionListEvaluator() {
    return evaluator;
  }
}
