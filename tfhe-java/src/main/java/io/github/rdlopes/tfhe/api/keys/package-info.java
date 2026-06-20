/// # FHE Key Management
///
/// This package contains all key types required for Fully Homomorphic Encryption (FHE) operations.
/// Three roles are distinguished: **key generation**, **client-side computation**, and **server-side computation**.
///
/// ---
///
/// ## Key overview
///
/// | Class | Role | Shareable |
/// |---|---|---|
/// | [ClientKey] | Encryption and decryption — always kept secret | ❌ never |
/// | [CompressedServerKey] | Canonical server key — CPU/GPU pivot | ✅ public |
/// | [ServerKey] | Decompressed CPU view — conformant deserialization key | via [CompressedServerKey] |
/// | [PublicKey] | Encryption without [ClientKey] | ✅ public |
///
/// ---
///
/// ## Scenario 1 — Local computation (client only)
///
/// The user generates keys and performs computations on the same machine.
///
/// {@snippet lang=java :
/// // Generate keys
/// KeySet keySet = KeySet.builder()
///     .useCustomParameters(CustomParameters.SHORTINT_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128)
///     .build();
///
/// // Activate the server key (CPU, or CPU+GPU when -Dtfhe.gpu=true)
/// keySet.getCompressedServerKey().use();
///
/// // Encrypt, compute, decrypt
/// FheUint8 a = FheUint8.encrypt((byte) 10, keySet.getClientKey());
/// FheUint8 b = FheUint8.encrypt((byte) 20, keySet.getClientKey());
/// FheUint8 sum = a.add(b);
/// byte result = sum.decrypt(keySet.getClientKey()); // == 30
/// }
///
/// ---
///
/// ## Scenario 2 — Client/Server architecture
///
/// The **client** generates keys, encrypts data, and sends the compressed server key.
/// The **server** receives the key, activates it, and computes over ciphertexts without
/// ever seeing data in the clear.
///
/// ### Client side
///
/// {@snippet lang=java :
/// KeySet keySet = KeySet.builder().build();
///
/// // Serialize the CompressedServerKey and send it to the server
/// DynamicBuffer serverKeyBytes = keySet.getCompressedServerKey().serialize();
/// sendToServer(serverKeyBytes.toByteArray()); // network, file, etc.
///
/// // Encrypt the inputs and send them
/// FheUint32 encrypted = FheUint32.encrypt(42, keySet.getClientKey());
/// sendToServer(encrypted.serialize().toByteArray());
/// }
///
/// ### Server side
///
/// {@snippet lang=java :
/// // Deserialize and activate the server key
/// CompressedServerKey serverKey = CompressedServerKey.deserialize(receivedKeyBuffer);
/// serverKey.use(); // CPU, or CPU+GPU automatically when -Dtfhe.gpu=true
///
/// // Deserialize ciphertexts and compute
/// FheUint32 a = FheUint32.deserialize(ciphertextBuffer, serverKey.decompress());
/// FheUint32 b = FheUint32.deserialize(anotherBuffer,    serverKey.decompress());
/// FheUint32 sum = a.add(b);
///
/// // Serialize and return the result
/// sendToClient(sum.serialize().toByteArray());
/// }
///
/// ### Client side — decrypting the result
///
/// {@snippet lang=java :
/// FheUint32 result = FheUint32.deserialize(resultBuffer, keySet.getServerKey());
/// int clearResult = result.decrypt(keySet.getClientKey()); // == 84
/// }
///
/// ---
///
/// ## GPU activation
///
/// **No code changes required.** Simply pass the system property at JVM startup:
///
/// ```
/// java -Dtfhe.gpu=true -jar my-app.jar
/// ```
///
/// [CompressedServerKey#use()] then activates both engines automatically:
/// the CPU key (required for ciphertext deserialization) and the GPU key
/// (for accelerated homomorphic operations).
///
/// ---
///
/// ## CPU-only path — direct ServerKey deserialization
///
/// If you hold serialized bytes of a [ServerKey] (CPU binary format), you can
/// deserialize them directly — but without GPU acceleration, even if `tfhe.gpu=true`:
///
/// {@snippet lang=java :
/// // CPU-only, regardless of tfhe.gpu
/// ServerKey serverKey = ServerKey.deserialize(buffer);
/// serverKey.use();
/// }
///
/// To benefit from GPU after deserialization, always serialize and transfer a
/// [CompressedServerKey] (not a [ServerKey]):
///
/// {@snippet lang=java :
/// // Client: serialize the CompressedServerKey
/// keySet.getCompressedServerKey().serialize();
///
/// // Server: deserialize and activate with GPU support
/// CompressedServerKey.deserialize(buffer).use(); // GPU activated if -Dtfhe.gpu=true
/// }
package io.github.rdlopes.tfhe.api.keys;
