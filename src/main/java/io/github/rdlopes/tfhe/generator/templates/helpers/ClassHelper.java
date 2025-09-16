package io.github.rdlopes.tfhe.generator.templates.helpers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Options;
import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappings.FheTypeContext;

import java.io.IOException;

import static io.github.rdlopes.tfhe.generator.templates.helpers.Helpers.text;

public record ClassHelper(Handlebars handlebars, SymbolsIndex symbolsIndex) {

  public CharSequence implement(Class<?> interfaceType, Options options) throws IOException {
    String partialContent = handlebars.compile("implementations/" + interfaceType.getSimpleName())
                                      .apply(options.context.parent());
    return text(partialContent);
  }

  public CharSequence test(Class<?> interfaceType, Options options) throws IOException {
    String partialContent = handlebars.compile("tests/" + interfaceType.getSimpleName() + "Test")
                                      .apply(options.context.parent());
    return text(partialContent);
  }

  public CharSequence nativeMethod(FheTypeContext context, Options options) {
    String methodPrefix = context.getNativePrefix() + "_" + options.param(0);
    return symbolsIndex.lookupPrefixed(methodPrefix);
  }

  public CharSequence nativeDefinition(FheTypeContext context, Options options) {
    CharSequence nativeMethod = nativeMethod(context, new Options.Builder(options.handlebars, options.helperName, options.tagType, options.context, options.fn)
      .setParams(options.params)
      .build());

    return text(symbolsIndex.lookupDefinition(String.valueOf(nativeMethod))
                            .replace("/**", "")
                            .replace("*/", "")
                            .trim());
  }

  public CharSequence javadoc(FheTypeContext context, Options options) {
    CharSequence nativeDefinition = nativeDefinition(context, new Options.Builder(options.handlebars, options.helperName, options.tagType, options.context, options.fn)
      .setParams(options.params)
      .build());

    return text("""
      /**
      {@snippet lang = "c":
      %s
      }
      */
      """.formatted(nativeDefinition));
  }

}
