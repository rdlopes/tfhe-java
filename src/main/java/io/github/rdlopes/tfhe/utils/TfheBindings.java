package io.github.rdlopes.tfhe.utils;

import java.util.*;
import java.util.regex.Pattern;

import static io.github.rdlopes.tfhe.utils.FheType.*;

public class TfheBindings {
  public static final Pattern FHE_INTEGER_TYPE_PATTERN = Pattern.compile("Fhe(Int\\d+|Uint\\d+)");
  public static final Pattern COMPRESSED_FHE_INTEGER_TYPE_PATTERN = Pattern.compile("Compressed" + FHE_INTEGER_TYPE_PATTERN.pattern());
  public static final Pattern FHE_BOOL_TYPE_PATTERN = Pattern.compile("FheBool");
  public static final Pattern COMPRESSED_FHE_BOOL_TYPE_PATTERN = Pattern.compile("CompressedFheBool");

  private static <T extends FheType> Collection<T> buildTypes(JextractSymbols includedSymbols, String packageName, Pattern pattern, Class<T> clazz) {
    List<T> fheTypes = includedSymbols.structs()
                                      .stream()
                                      .filter(pattern.asMatchPredicate())
                                      .map(name -> {
                                        try {
                                          return clazz.getConstructor(String.class, String.class, Map.class, Collection.class)
                                                      .newInstance(packageName, name, new HashMap<>(), new ArrayList<>());
                                        } catch (Exception e) {
                                          throw new RuntimeException(e);
                                        }
                                      })
                                      .toList();
    fheTypes.forEach(fheType -> {
      List<String> methods =
        includedSymbols.functions()
                       .stream()
                       .filter(f ->
                         f.startsWith(fheType.nativeName() + "_") || f.endsWith("_" + fheType.nativeName()))
                       .toList();
      fheType.methods()
             .addAll(methods);
      includedSymbols.definitions()
                     .entrySet()
                     .stream()
                     .filter(entry -> fheType.methods()
                                             .contains(entry.getKey()))
                     .forEach(entry -> fheType.definitions()
                                              .put(entry.getKey(), entry.getValue()));
    });

    return fheTypes;
  }

  public static Collection<FheType> buildTypes(JextractSymbols includedSymbols, String basePackage) {
    Collection<FheType> fheTypes = new ArrayList<>();

    fheTypes.addAll(buildTypes(includedSymbols, basePackage, FHE_INTEGER_TYPE_PATTERN, FheIntType.class));
    fheTypes.addAll(buildTypes(includedSymbols, basePackage, COMPRESSED_FHE_INTEGER_TYPE_PATTERN, CompressedFheIntType.class));
    fheTypes.addAll(buildTypes(includedSymbols, basePackage, FHE_BOOL_TYPE_PATTERN, FheBoolType.class));
    fheTypes.addAll(buildTypes(includedSymbols, basePackage, COMPRESSED_FHE_BOOL_TYPE_PATTERN, CompressedFheBoolType.class));

    return fheTypes;
  }
}
