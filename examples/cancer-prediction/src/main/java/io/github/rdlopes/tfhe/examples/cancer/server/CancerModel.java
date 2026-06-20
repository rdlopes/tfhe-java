package io.github.rdlopes.tfhe.examples.cancer.server;

/// Holds the pre-trained linear model weights and bias for the breast cancer classifier.
/// Weights correspond to the 12 enriched Breast Cancer Wisconsin dataset features:
/// clump thickness, cell size uniformity, cell shape uniformity, marginal adhesion,
/// single epithelial cell size, bare nuclei, bland chromatin, normal nucleoli,
/// mitoses, age scale, genetic risk, family history.
public final class CancerModel {

  /// Pre-trained integer weights for the linear classifier.
  public static final int[] WEIGHTS = {2, 12, 6, 5, -8, 12, -3, 6, 1, -29, 9, 10};

  /// Pre-trained bias for the linear classifier.
  public static final int BIAS = -46;

  private CancerModel() {}
}
