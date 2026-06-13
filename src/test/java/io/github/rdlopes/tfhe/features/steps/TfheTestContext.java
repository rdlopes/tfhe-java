package io.github.rdlopes.tfhe.features.steps;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CompactPkeCrs;
import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.shortint.ShortintBivariatePbsLookupTable;
import io.github.rdlopes.tfhe.api.shortint.ShortintCiphertext;
import io.github.rdlopes.tfhe.api.shortint.ShortintClientKey;
import io.github.rdlopes.tfhe.api.shortint.ShortintCompressedCiphertext;
import io.github.rdlopes.tfhe.api.shortint.ShortintPbsLookupTable;
import io.github.rdlopes.tfhe.api.shortint.ShortintPublicKey;
import io.github.rdlopes.tfhe.api.shortint.ShortintServerKey;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextList;
import io.github.rdlopes.tfhe.api.types.CompactCiphertextListExpander;
import io.github.rdlopes.tfhe.api.types.CompressedCiphertextList;
import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.api.types.FheUint128;
import io.github.rdlopes.tfhe.api.types.FheUint128Array;
import io.github.rdlopes.tfhe.api.types.FheUint8;
import io.github.rdlopes.tfhe.api.types.FheUint8Array;
import io.github.rdlopes.tfhe.api.types.ProvenCompactCiphertextList;
import io.github.rdlopes.tfhe.ffm.NativeAddress;

import java.util.ArrayList;
import java.util.List;

public class TfheTestContext {

  private final List<NativeAddress> nativeObjects = new ArrayList<>();

  // Core KeySets, Keys, CRS
  public KeySet keySet;
  public PublicKey publicKey;
  public CompactPkeCrs crs;
  public CompactPublicKey compactPublicKey;

  // High-Level FHE Types
  public final List<FheUint8> uint8s = new ArrayList<>();
  public final List<FheUint128> uint128s = new ArrayList<>();
  public final List<FheBool> bools = new ArrayList<>();

  // High-Level Arrays
  public FheUint8Array uint8Array;
  public FheUint128Array uint128Array;

  // Shortint Types
  public ShortintClientKey shortintClientKey;
  public ShortintServerKey shortintServerKey;
  public ShortintPublicKey shortintPublicKey;
  public final List<ShortintCiphertext> shortintCiphertexts = new ArrayList<>();
  public final List<ShortintCompressedCiphertext> shortintCompressedCiphertexts = new ArrayList<>();
  public ShortintPbsLookupTable shortintLut;
  public ShortintBivariatePbsLookupTable shortintBivariateLut;

  // Compact / Compressed / Proven Lists
  public CompactCiphertextList compactList;
  public CompressedCiphertextList compressedList;
  public ProvenCompactCiphertextList provenList;
  public CompactCiphertextListExpander expander;



  public <T extends NativeAddress> T track(T obj) {
    if (obj != null) {
      nativeObjects.add(obj);
    }
    return obj;
  }

  public List<NativeAddress> getNativeObjects() {
    return nativeObjects;
  }
}
