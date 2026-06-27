package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

public class ShortintBivariatePbsLookupTable extends NativePointer {

  ShortintBivariatePbsLookupTable() {
    super(TfheHeader::shortint_destroy_bivariate_pbs_lookup_table);
  }
}
