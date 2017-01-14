package org.uma.jmetalmsa.algorithm.movmomsa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import org.uma.jmetalmsa.algorithm.movmomsa.util.Pair;
import org.uma.jmetalmsa.algorithm.movmomsa.util.Util;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.util.MSADistance;

public class MOVMOMSA<S extends MSASolution> implements Algorithm<List<S>> {

    private Problem problem_;
    private JMetalRandom pseudoRandom_;

    protected int populationSize;
    protected int maxEvaluations;
    protected int evaluations;
    protected int neighborhoodSize;
    protected double beta;

    public MSADistance distanceMSA;
    protected CrowdingDistanceArchive<MSASolution> archive;

    protected DominanceComparator<MSASolution> dominance;
    protected CrowdingDistanceComparator<MSASolution> crowding;
    protected Util<MSASolution> util;

    private CrossoverOperator<MSASolution> crossoverOperator;
    private MutationOperator<MSASolution> mutationOperator;
    private SelectionOperator selectionOperator;

    protected int archiveSize;

    public MOVMOMSA(
            int populationSize,
            int archiveSize,
            int maxEvaluations,
            int neighborhoodSize,
            Problem<MSASolution> problem,
            SelectionOperator selectionOperator,
            CrossoverOperator<MSASolution> crossoverOperator,
            MutationOperator<MSASolution> mutationOperator) {

        this.problem_ = problem;
        this.populationSize = populationSize;
        this.archiveSize = archiveSize;
        this.maxEvaluations = maxEvaluations;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.selectionOperator = selectionOperator;
        this.neighborhoodSize = neighborhoodSize;

        this.dominance = new DominanceComparator<MSASolution>();
        this.crowding = new CrowdingDistanceComparator<MSASolution>();
        this.pseudoRandom_ = JMetalRandom.getInstance();
        this.util = new Util<MSASolution>();

        evaluations = 0;

        distanceMSA = new MSADistance();
    }

    public List<S> getResult() {
        return SolutionListUtils.getNondominatedSolutions((List<S>) archive.getSolutionList());
    }

    protected void updateArchiveCrowdingDistances() {
        if (archive.size() < archiveSize) {
            archive.computeDensityEstimator();
        }
    }

    protected MSASolution selectBestFrom(List<MSASolution> solutionSet) {
        MSASolution best = solutionSet.get(0);

        for (int i = 1; i < solutionSet.size(); i++) {
            if (dominance.compare(solutionSet.get(i), best) < 0) {
                best = solutionSet.get(i);
            }
        }
        return best;
    }

    protected void initArchive() {
        archive = new CrowdingDistanceArchive<MSASolution>(this.archiveSize);
    }

    protected MSASolution binaryTournamentSelection(CrowdingDistanceArchive<MSASolution> archive) {

        // selection by tournament with size 2
        int pos1 = this.pseudoRandom_.nextInt(0, archive.size() - 1);
        int pos2 = this.pseudoRandom_.nextInt(0, archive.size() - 1);

        while (archive.size() > 1 && pos1 == pos2) {
            pos2 = this.pseudoRandom_.nextInt(0, archive.size() - 1);
        }

        MSASolution one = archive.get(pos1);
        MSASolution two = archive.get(pos2);

        if (crowding.compare(one, two) < 0) {
            return one;
        }
        return two;
    }

    protected MSASolution globalBest() {
        return binaryTournamentSelection(archive);
    }

    protected List<MSASolution> kNN(List<MSASolution> population, MSASolution reference, int k)
            throws JMetalException {

        ArrayList<Pair<MSASolution>> arr = new ArrayList<Pair<MSASolution>>();
        for (int i = 0; i < population.size(); i++) {
            MSASolution sol = population.get(i);
            if (sol != reference) {
                arr.add(new Pair<MSASolution>(sol, distanceMSA.getLevenshteinDistance(sol, reference)));
               //arr.add(new Pair<MSASolution>(sol, this.pseudoRandom_.nextInt(1, 30)));
               
               //System.out.println(distanceMSA.getLevenshteinDistance(sol, reference));
            }
        }

        arr.sort(new Comparator<Pair<MSASolution>>() {
            public int compare(Pair<MSASolution> o1, Pair<MSASolution> o2) {
                if (o1.d < o2.d) {
                    return -1;
                }
                if (o1.d > o2.d) {
                    return 1;
                }
                //return dominance.compare(o1.s, o2.s);
                return 0;
            }
        });
        
        int kk = Math.min(k, arr.size());
        List<MSASolution> a = new ArrayList<MSASolution>(kk);
        for (int i = 0; i < kk; i++) {
            a.add(arr.get(i).s);
        }
        return a;
    }

   

    
    public void run() {

        List<MSASolution> population;
        MSASolution solution;
        MSASolution a, local, global, x;

        initArchive();
        population = new ArrayList<MSASolution>(populationSize);
        
        population = ((MSAProblem) problem_).createInitialPopulation(populationSize);
        
        for (int i = 0; i < populationSize; i++) {
            archive.add(new MSASolution(population.get(i)));
        }
        evaluations=populationSize;
        updateArchiveCrowdingDistances();

        // Generations
        while (evaluations < maxEvaluations) {

            for (int i = 0; i < populationSize; i++) {

                a = population.get(i);

                // local producing....
                List<MSASolution> neighbors = kNN(population, a, neighborhoodSize);
                local = selectBestFrom(neighbors);

                if (dominance.compare(local, a) < 0) {
                     local = crossSolutions(a, local);
                     //mutationOperator.execute(local);
                } else {
                    local = a;
                }

                // global producing....				
                global = globalBest();
//				if (PseudoRandom.randDouble(0, 1) <= beta)
//					global = globalProducing(a, global);

                // crossover...
                x = crossSolutions(local, global);

                //mutation
//				mutation.execute(x);
                // evaluation....
                problem_.evaluate(x);
//				problem_.evaluateConstraints(x); NO Existe Ver IBEA Algorithm
                evaluations++;

                // updating leaders archive
                archive.add(new MSASolution(x));
                updateArchiveCrowdingDistances();

                // dynamic population updating 
                if (dominance.compare(x, a) <= 0) {
                    population.set(i, x);
                }
                
                System.out.println(evaluations);

            }
        }
    }

    public MSASolution crossSolutions(MSASolution A, MSASolution B){
          List<MSASolution> parents = new ArrayList<MSASolution>();
          parents.add(A);
          parents.add(B);
          List<MSASolution> offspring = crossoverOperator.execute(parents);
          return offspring.get(0);
    }
    
    @Override
    public String getName() {
        return "MOVMO";
    }

    @Override
    public String getDescription() {
        return "MOVMO";
    }
}
