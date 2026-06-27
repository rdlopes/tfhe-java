package io.github.rdlopes.tfhe.core.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/// Indique qu'une classe est générée car elle correspond aux différents
/// types de TFHE, assurant la couverture des types déclarés.
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface Generated {
}
