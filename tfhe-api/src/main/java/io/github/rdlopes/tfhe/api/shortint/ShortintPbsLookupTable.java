package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

public class ShortintPbsLookupTable extends NativePointer {

  ShortintPbsLookupTable() {
    super(TfheHeader::shortint_destroy_pbs_lookup_table);
  }
}
