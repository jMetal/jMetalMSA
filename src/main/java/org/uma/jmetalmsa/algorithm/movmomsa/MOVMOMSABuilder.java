package org.uma.jmetalmsa.algorithm.movmomsa;

import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetalmsa.solution.MSASolution;

/**
 * This class implements the MOVMOMSA algorithm
 */
public class MOVMOMSABuilder<S extends MSASolution> implements AlgorithmBuilder<MOVMOMSA<S>> {

	private Problem<MSASolution> problem;
	protected int populationSize;
	protected int archiveSize;
	protected int neighborhoodSize;

	protected int maxEvaluations;

	protected Double beta;

	protected CrossoverOperator<MSASolution> crossoverOperator;
	protected SelectionOperator selectionOperator;
	protected MutationOperator<MSASolution> mutationOperator;

	protected SolutionListEvaluator<S> evaluator;

	/** Constructor */
	public MOVMOMSABuilder(Problem<MSASolution> problem, CrossoverOperator<MSASolution> crossoverOperator,
            MutationOperator<MSASolution> mutationOperator, SelectionOperator selectionOperator) {		
		this.problem = problem;
		this.maxEvaluations = 50000;
		this.populationSize = 100;
		this.neighborhoodSize = 4;
		this.archiveSize = 100;
		this.beta = 0.0;

		//Iniciacion de los Operadores
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.selectionOperator=selectionOperator;
	}

	
	public MOVMOMSABuilder setMaxEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;

		return this;
	}
		
	public MOVMOMSABuilder setPopulationSize(int populationSize) {
		this.populationSize = populationSize;

		return this;
	}

	public MOVMOMSABuilder setNeighborhoodSize(int neighborhoodSize) {
		this.neighborhoodSize = neighborhoodSize;

		return this;
	}
	
	public MOVMOMSABuilder setArchiveSize(int archiveSize) {
		this.archiveSize = archiveSize;

		return this;
	}	
	
	public MOVMOMSABuilder setBeta(Double beta) {
		this.beta = beta;

		return this;
	}	
	
	/** Setters Operator */
	public MOVMOMSABuilder setCrossover(CrossoverOperator<MSASolution> crossover) {
		crossoverOperator = crossover;

		return this;
	}

	public MOVMOMSABuilder setSelection(SelectionOperator<List<MSASolution>, MSASolution> selection) {
		this.selectionOperator = selection;

		return this;
	}

	public MOVMOMSABuilder setMutation(MutationOperator<MSASolution> mutation) {
		this.mutationOperator = mutation;

		return this;
	}

	
	/** Build Method */
	public MOVMOMSA<S> build() {

		return new MOVMOMSA<S>(
				this.populationSize,
				this.archiveSize,
				this.maxEvaluations,
				this.neighborhoodSize,
				this.problem,
				this.selectionOperator,
				this.crossoverOperator,
				this.mutationOperator) ;
	}

	
	/** Getters */	
	public CrossoverOperator<MSASolution> getCrossoverOperator() {
		return crossoverOperator;
	}

	public int getPopulationSize() {
		return this.populationSize;
	}

	public int getMaxEvaluations() {
		return this.maxEvaluations;
	}

}

