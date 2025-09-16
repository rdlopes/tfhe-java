<#-- @ftlvariable name="symbolsIndex" type="io.github.rdlopes.tfhe.generator.SymbolsIndex" -->
<#-- @ftlvariable name="sourcePackageName" type="String" -->
<#-- @ftlvariable name="testPackageName" type="String" -->
<#-- @ftlvariable name="className" type="String" -->
<#-- @ftlvariable name="compressedClassName" type="String" -->
<#-- @ftlvariable name="testClassName" type="String" -->
<#-- @ftlvariable name="testCompressedClassName" type="String" -->
<#-- @ftlvariable name="dataClass" type="java.lang.Class" -->
<#-- @ftlvariable name="testValues" type="io.github.rdlopes.tfhe.generator.mappers.TestValues" -->
<#-- @ftlvariable name="bitSize" type="String" -->
<#-- @ftlvariable name="bitLength" type="String" -->
<#-- @ftlvariable name="nativePrefix" type="String" -->
<#import "lib/snippets.ftl" as s>
<#import "lib/Object.ftl" as object>
<#import "lib/Compressible.ftl" as compressible>
<#import "lib/Encryptable.ftl" as encryptable>
<#import "lib/Logical.ftl" as logical>
<#import "lib/Comparable.ftl" as comparable>
<#import "lib/Serializable.ftl" as serializable>
<#import "lib/Arithmetic.ftl" as arithmetic>
package ${sourcePackageName};

<@s.sourceImports/>

<#-- @formatter:off -->
public class ${className} extends NativeValue implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(${className}.class);
<#-- @formatter:on -->

<@object.constructor className "${nativePrefix}${bitSize}_destroy"/>

<@encryptable.encryptWithClientKey className "${nativePrefix}${bitSize}_try_encrypt_with_client_key" dataClass.simpleName/>
<@encryptable.encryptWithPublicKey className "${nativePrefix}${bitSize}_try_encrypt_with_public_key" dataClass.simpleName/>
<@encryptable.encryptTrivial className "${nativePrefix}${bitSize}_try_encrypt_trivial" dataClass.simpleName/>
<@encryptable.decryptWithClientKey className "${nativePrefix}${bitSize}_decrypt" dataClass.simpleName/>
<@encryptable.decryptTrivial "${nativePrefix}${bitSize}_try_decrypt_trivial" dataClass.simpleName/>

<@serializable.serialize className "${nativePrefix}${bitSize}_safe_serialize"/>
<@serializable.deserialize className "${nativePrefix}${bitSize}_safe_deserialize_conformant"/>

<@logical.and className "${nativePrefix}${bitSize}_bitand"/>
<@logical.andAssign className "${nativePrefix}${bitSize}_bitand_assign"/>
<@logical.or className "${nativePrefix}${bitSize}_bitor"/>
<@logical.orAssign className "${nativePrefix}${bitSize}_bitor_assign"/>
<@logical.xor className "${nativePrefix}${bitSize}_bitxor"/>
<@logical.xorAssign className "${nativePrefix}${bitSize}_bitxor_assign"/>
<@logical.scalarAnd className "${nativePrefix}${bitSize}_scalar_bitand"/>
<@logical.scalarAndAssign className "${nativePrefix}${bitSize}_scalar_bitand_assign"/>
<@logical.scalarOr className "${nativePrefix}${bitSize}_scalar_bitor"/>
<@logical.scalarOrAssign className "${nativePrefix}${bitSize}_scalar_bitor_assign"/>
<@logical.scalarXor className "${nativePrefix}${bitSize}_scalar_bitxor"/>
<@logical.scalarXorAssign className "${nativePrefix}${bitSize}_scalar_bitxor_assign"/>

<@comparable.eq className "${nativePrefix}${bitSize}_eq"/>
<@comparable.ne className "${nativePrefix}${bitSize}_ne"/>
<@comparable.scalarEq className "${nativePrefix}${bitSize}_scalar_eq" dataClass.simpleName/>
<@comparable.scalarNe className "${nativePrefix}${bitSize}_scalar_ne" dataClass.simpleName/>
<@comparable.ge className "${nativePrefix}${bitSize}_ge"/>
<@comparable.greaterThan className "${nativePrefix}${bitSize}_gt"/>
<@comparable.le className "${nativePrefix}${bitSize}_le"/>
<@comparable.lessThan className "${nativePrefix}${bitSize}_lt"/>
<@comparable.scalarGe className "${nativePrefix}${bitSize}_scalar_ge" dataClass.simpleName/>
<@comparable.scalarGreaterThan className "${nativePrefix}${bitSize}_scalar_gt" dataClass.simpleName/>
<@comparable.scalarLe className "${nativePrefix}${bitSize}_scalar_le" dataClass.simpleName/>
<@comparable.scalarLessThan className "${nativePrefix}${bitSize}_scalar_lt" dataClass.simpleName/>

<@arithmetic.add className "${nativePrefix}${bitSize}_add"/>
<@arithmetic.addAssign className "${nativePrefix}${bitSize}_add_assign"/>
<@arithmetic.sub className "${nativePrefix}${bitSize}_sub"/>
<@arithmetic.subAssign className "${nativePrefix}${bitSize}_sub_assign"/>
<@arithmetic.mul className "${nativePrefix}${bitSize}_mul"/>
<@arithmetic.mulAssign className "${nativePrefix}${bitSize}_mul_assign"/>
<@arithmetic.scalarAdd className "${nativePrefix}${bitSize}_scalar_add" dataClass.simpleName/>
<@arithmetic.scalarAddAssign className "${nativePrefix}${bitSize}_scalar_add_assign" dataClass.simpleName/>
<@arithmetic.scalarSub className "${nativePrefix}${bitSize}_scalar_sub" dataClass.simpleName/>
<@arithmetic.scalarSubAssign className "${nativePrefix}${bitSize}_scalar_sub_assign" dataClass.simpleName/>
<@arithmetic.scalarMul className "${nativePrefix}${bitSize}_scalar_mul" dataClass.simpleName/>
<@arithmetic.scalarMulAssign className "${nativePrefix}${bitSize}_scalar_mul_assign" dataClass.simpleName/>

<@arithmetic.div className "${nativePrefix}${bitSize}_div"/>
<@arithmetic.divAssign className "${nativePrefix}${bitSize}_div_assign"/>
<@arithmetic.rem className "${nativePrefix}${bitSize}_rem"/>
<@arithmetic.remAssign className "${nativePrefix}${bitSize}_rem_assign"/>
<@arithmetic.scalarDiv className "${nativePrefix}${bitSize}_scalar_div" dataClass.simpleName/>
<@arithmetic.scalarDivAssign className "${nativePrefix}${bitSize}_scalar_div_assign" dataClass.simpleName/>
<@arithmetic.scalarRem className "${nativePrefix}${bitSize}_scalar_rem" dataClass.simpleName/>
<@arithmetic.scalarRemAssign className "${nativePrefix}${bitSize}_scalar_rem_assign" dataClass.simpleName/>
<@arithmetic.shl className "${nativePrefix}${bitSize}_shl"/>
<@arithmetic.shlAssign className "${nativePrefix}${bitSize}_shl_assign"/>
<@arithmetic.shr className "${nativePrefix}${bitSize}_shr"/>
<@arithmetic.shrAssign className "${nativePrefix}${bitSize}_shr_assign"/>
<@arithmetic.scalarShl className "${nativePrefix}${bitSize}_scalar_shl" dataClass.simpleName/>
<@arithmetic.scalarShlAssign className "${nativePrefix}${bitSize}_scalar_shl_assign" dataClass.simpleName/>
<@arithmetic.scalarShr className "${nativePrefix}${bitSize}_scalar_shr" dataClass.simpleName/>
<@arithmetic.scalarShrAssign className "${nativePrefix}${bitSize}_scalar_shr_assign" dataClass.simpleName/>

<@compressible.compress compressedClassName "${nativePrefix}${bitSize}_compress"/>

<@object.clone className "${nativePrefix}${bitSize}_clone"/>

}
