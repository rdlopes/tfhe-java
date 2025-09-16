<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="compressedClassName" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="testCompressedClassName" type="String" -->
<#-- @ftlvariable name="dataClass" type="java.lang.Class" -->
<#-- @ftlvariable name="testValues" type="io.github.rdlopes.tfhe.generator.mappers.TestValues" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Object.ftl" as object>
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Encryptable.ftl" as encryptable>
<#import "lib/Logical.ftl" as logical>
<#import "lib/Comparable.ftl" as comparable>
<#import "lib/Serializable.ftl" as serializable>
package ${sourcePackageName};

<@s.sourceImports/>

<#-- @formatter:off -->
public class ${className} extends NativeValue implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(${className}. class);
<#-- @formatter:on -->

<@object.constructor className "fhe_bool_destroy"/>

<@encryptable.encryptWithClientKey className "fhe_bool_try_encrypt_with_client_key_bool" dataClass.simpleName/>
<@encryptable.encryptWithPublicKey className "fhe_bool_try_encrypt_with_public_key_bool" dataClass.simpleName/>
<@encryptable.encryptTrivial className "fhe_bool_try_encrypt_trivial_bool" dataClass.simpleName/>
<@encryptable.decryptWithClientKey className "fhe_bool_decrypt" dataClass.simpleName/>
<@encryptable.decryptTrivial "fhe_bool_try_decrypt_trivial" dataClass.simpleName/>

<@compressible.compress compressedClassName "fhe_bool_compress"/>

<@logical.and className "fhe_bool_bitand"/>
<@logical.andAssign className "fhe_bool_bitand_assign"/>
<@logical.or className "fhe_bool_bitor"/>
<@logical.orAssign className "fhe_bool_bitor_assign"/>
<@logical.xor className "fhe_bool_bitxor"/>
<@logical.xorAssign className "fhe_bool_bitxor_assign"/>
<@logical.not className "fhe_bool_not"/>
<@logical.scalarAnd className "fhe_bool_scalar_bitand"/>
<@logical.scalarAndAssign className "fhe_bool_scalar_bitand_assign"/>
<@logical.scalarOr className "fhe_bool_scalar_bitor"/>
<@logical.scalarOrAssign className "fhe_bool_scalar_bitor_assign"/>
<@logical.scalarXor className "fhe_bool_scalar_bitxor"/>
<@logical.scalarXorAssign className "fhe_bool_scalar_bitxor_assign"/>

<@comparable.eq className "fhe_bool_eq"/>
<@comparable.ne className "fhe_bool_ne"/>
<@comparable.scalarEq className "fhe_bool_scalar_eq"  dataClass.simpleName/>
<@comparable.scalarNe className "fhe_bool_scalar_ne"  dataClass.simpleName/>

<@serializable.serialize className "fhe_bool_safe_serialize"/>
<@serializable.deserialize className "fhe_bool_safe_deserialize_conformant"/>
<@serializable.unsafeSerialize "fhe_bool_serialize"/>
<@serializable.unsafeDeserialize className "fhe_bool_deserialize"/>

<@object.clone className "fhe_bool_clone"/>

}

