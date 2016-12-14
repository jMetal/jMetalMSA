package org.uma.jmetalmsa.score.impl;

import org.uma.jmetalmsa.problem.DynamicallyComposedProblem;
import org.uma.jmetalmsa.util.distancematrix.DistanceMatrix;
import org.uma.jmetalmsa.solution.MSASolution;
import org.uma.jmetalmsa.score.Score;

public class SumOfPairsScore implements Score {
  private DistanceMatrix distanceMatrix;

  public SumOfPairsScore(DistanceMatrix distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
  }

  @Override
  public <S extends MSASolution> double compute(S solution,char [][]decodedSequences) {
    double sumOfPairs = 0;

    int numberOfVariables = decodedSequences.length;
    int lengthSequences = decodedSequences[0].length;

    for (int i = 0; i < (numberOfVariables - 1); i++) {
      for (int j = i + 1; j < numberOfVariables; j++) {
        for (int k = 0; k < lengthSequences; k++) {
          sumOfPairs += distanceMatrix.getDistance(decodedSequences[i][k], decodedSequences[j][k]);
        }
      }
    }

    return sumOfPairs;
  }

  @Override
  public boolean isAMinimizationScore() {
    return false;
  }

  @Override
  public String getName() {
    return "SOP";
  }

  @Override
  public String getDescription() {
    return "Sum of pairs";
  }
}
