package io.github.rdlopes.tfhe.jca;

import java.security.spec.AlgorithmParameterSpec;

public record TfheParameterSpec(boolean serverKey) implements AlgorithmParameterSpec {
}
