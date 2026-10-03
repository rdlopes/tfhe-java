package io.github.rdlopes.tfhe.api.stream;

import io.github.rdlopes.tfhe.api.keys.ServerKey;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BinaryOperator;

/// Internal tree-reduction accumulator for homomorphic stream operations.
///
/// Reduces elements using a logarithmic stack structure. Pairwise combinations
/// bound circuit depth to O(log2 N), which minimizes multiplicative and additive
/// noise accumulation compared to sequential O(N) reductions.
///
/// Intermediate off-heap ciphertexts created at each tree level are eagerly
/// destroyed via [AutoCloseable#close()], preventing native memory exhaustion.
final class TreeReductionAccumulator<T extends AutoCloseable> {

  record Node<T extends AutoCloseable>(T value, boolean isIntermediate) {
    void closeIfIntermediate() {
      if (isIntermediate && value != null) {
        try {
          value.close();
        } catch (Exception e) {
          throw new IllegalStateException("Failed to close intermediate native resource", e);
        }
      }
    }
  }

  private final BinaryOperator<T> combiner;
  private final boolean consumeInputs;
  private final ServerKey serverKey;
  private final ArrayList<Node<T>> stack = new ArrayList<>();

  TreeReductionAccumulator(BinaryOperator<T> combiner, boolean consumeInputs, ServerKey serverKey) {
    this.combiner = Objects.requireNonNull(combiner, "combiner cannot be null");
    this.consumeInputs = consumeInputs;
    this.serverKey = serverKey;
  }

  void ensureServerKey() {
    if (serverKey != null) {
      serverKey.use();
    }
  }

  void add(T item) {
    if (item == null) {
      return;
    }
    ensureServerKey();
    addNode(new Node<>(item, consumeInputs));
  }

  void addInitial(T item, boolean isIntermediate) {
    if (item == null) {
      return;
    }
    ensureServerKey();
    addNode(new Node<>(item, isIntermediate));
  }

  private void addNode(Node<T> initialNode) {
    Node<T> current = initialNode;
    int level = 0;
    while (level < stack.size()) {
      Node<T> existing = stack.get(level);
      if (existing == null) {
        break;
      }
      stack.set(level, null);
      T combined = combiner.apply(existing.value(), current.value());
      existing.closeIfIntermediate();
      current.closeIfIntermediate();
      current = new Node<>(combined, true);
      level++;
    }
    if (level < stack.size()) {
      stack.set(level, current);
    } else {
      stack.add(current);
    }
  }

  TreeReductionAccumulator<T> combine(TreeReductionAccumulator<T> other) {
    if (other == null || other.stack.isEmpty()) {
      return this;
    }
    ensureServerKey();
    for (Node<T> node : other.stack) {
      if (node != null) {
        addNode(node);
      }
    }
    return this;
  }

  T finish() {
    ensureServerKey();
    Node<T> result = null;
    for (Node<T> node : stack) {
      if (node != null) {
        if (result == null) {
          result = node;
        } else {
          T combined = combiner.apply(result.value(), node.value());
          result.closeIfIntermediate();
          node.closeIfIntermediate();
          result = new Node<>(combined, true);
        }
      }
    }
    return result != null ? result.value() : null;
  }
}
