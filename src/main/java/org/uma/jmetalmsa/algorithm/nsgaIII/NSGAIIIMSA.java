package org.uma.jmetalmsa.algorithm.nsgaIII;

import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetalmsa.problem.MSAProblem;
import org.uma.jmetalmsa.solution.MSASolution;

import java.util.List;

/**
 * Created by ajnebro on 21/10/16.
 */
public class NSGAIIIMSA extends NSGAIII<MSASolution> {
  public NSGAIIIMSA(NSGAIIIBuilder<MSASolution> builder) {
    super(builder);
  }

  protected List<MSASolution> createInitialPopulation() {
    return ((MSAProblem)getProblem()).createInitialPopulation(getMaxPopulationSize());
  }
}
