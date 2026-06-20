package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public class ShortintBivariatePbsLookupTable extends NativePointer {

  ShortintBivariatePbsLookupTable() {
    super(TfheHeader::shortint_destroy_bivariate_pbs_lookup_table);
  }
}
