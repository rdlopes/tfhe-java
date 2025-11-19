@asciidoc
@order-98
@glossary
Feature: Glossary

  [glossary]

  [[body,Body]]Body::
  The second component of an LWE ciphertext, computed as the sum of the mask elements multiplied by the secret key elements plus the plaintext.

  [[bootstrapping,Bootstrapping]]Bootstrapping::
  An operation that reduces noise in ciphertexts while maintaining the encrypted data. In TFHE, bootstrapping is programmable, allowing functions to be computed during the noise reduction process.

  [[bootstrapping-key,Bootstrapping Key]]Bootstrapping Key::
  A public evaluation key required for performing Programmable Bootstrapping operations.

  [[carry,Carry]]Carry::
  Extra bits that may be needed when operations on encrypted values exceed the original interval. TFHE-rs uses padding bits on the most significant bits to accommodate carries.

  [[ciphertext,Ciphertext]]Ciphertext::
  An encrypted value consisting of a mask and body, computed using an LWE secret key. Fresh ciphertexts result from encryption, while derived ciphertexts result from operations.

  [[client-key,Client Key]]Client Key::
  The secret key used by the client to encrypt and decrypt data. It must be kept private and secure.

  [[fhe,FHE]]FHE::
  Fully Homomorphic Encryption - a cryptographic scheme that allows arbitrary computations on encrypted data without decrypting it.

  [[glwe,GLWE]]GLWE::
  Generalized Learning With Errors - an extension of LWE that operates on polynomials rather than single values, used in TFHE for certain operations.

  [[homomorphic-operation,Homomorphic Operation]]Homomorphic Operation::
  An operation performed on encrypted data that produces an encrypted result corresponding to the operation on the plaintext data.

  [[keyswitch,Keyswitch]]Keyswitch::
  An operation that changes the encryption key of a ciphertext, typically used before or after bootstrapping to ensure input and output are encrypted under the same key.

  [[key-switching-key,Key switching Key]]Key switching Key::
  A public evaluation key required for performing keyswitch operations.

  [[leveled-operation,Leveled Operation]]Leveled Operation::
  A homomorphic operation that increases the noise in ciphertexts. These operations are typically fast but require noise management.

  [[lwe,LWE]]LWE::
  Learning With Errors - the mathematical problem that forms the basis of TFHE security. It is considered resistant to quantum attacks.

  [[lwe-ciphertext,LWE Ciphertext]]LWE Ciphertext::
  A ciphertext based on the Learning With Errors problem, consisting of a mask (list of random values) and a body (computed value).

  [[lwe-dimension,LWE Dimension]]LWE Dimension::
  The parameter 'n' representing the number of elements in the LWE secret key, which directly affects security level and performance.

  [[mask,Mask]]Mask::
  The first component of an LWE ciphertext, consisting of a list of uniformly random values used in the encryption process.

  [[noise,Noise]]Noise::
  Random values added to plaintexts during encryption to ensure security. Noise is encoded in the least significant bits and grows with leveled operations.

  [[noise-distribution,Noise Distribution]]Noise Distribution::
  The statistical distribution from which noise values are drawn, either Centered Normal Distribution or Tweaked Uniform (TUniform) Distribution.

  [[padding,Padding]]Padding::
  Additional bits defined on the most significant bits of encoded values to prevent precision loss or wraparound when operations produce results outside the original interval.

  [[pbs,PBS]]PBS::
  Programmable BootStrapping - a core TFHE operation that both reduces noise and computes arbitrary functions represented as look-up tables.

  [[plaintext,Plaintext]]Plaintext::
  An encoded message prepared for encryption by shifting the message to the most significant bits and adding noise to the least significant bits.

  [[public-key,Public Key]]Public Key::
  A key derived from the client key that allows encryption without revealing the secret key, enabling third parties to encrypt data.

  [[secret-key,Secret Key]]Secret Key::
  The private key consisting of n random integers used to encrypt and decrypt LWE ciphertexts. Also known as the LWE secret key.

  [[server-key,Server Key]]Server Key::
  The public evaluation key used by the server to perform homomorphic operations on encrypted data without being able to decrypt it.

  [[tfhe,TFHE]]TFHE::
  Torus Fully Homomorphic Encryption - a FHE scheme that enables fast homomorphic operations on booleans, integers, and reals using Learning With Errors over the torus.

  [[tfhe-rs,TFHE-rs]]TFHE-rs::
  A pure Rust implementation of the TFHE scheme for Boolean and integer arithmetics over encrypted data, including Rust, C, and WASM APIs.

  [[trivial-ciphertext,Trivial Ciphertext]]Trivial Ciphertext::
  A ciphertext created without actual encryption, where the plaintext value is publicly known. Useful for mixing encrypted and unencrypted values in operations.

  [[tuniform,TUniform]]TUniform::
  Tweaked Uniform Distribution - a bounded noise distribution where values in the interval (-2^b, ..., 2^b) are selected with specific probabilities.

  [[zero-knowledge-proof,Zero-Knowledge Proof]]Zero-Knowledge Proof::
  A cryptographic protocol that allows one party to prove to another that a statement is true without revealing any information beyond the validity of the statement itself.

  Scenario: Root
