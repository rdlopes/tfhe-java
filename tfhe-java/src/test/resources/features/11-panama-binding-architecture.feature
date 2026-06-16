@asciidoc
@order-11
@section-PanamaBinding
@appendix
@gpu
Feature: Project Panama and FFM Binding Architecture

  === Project Panama & FFM API
  TFHE-Java integrates the native Rust `tfhe-rs` library using Java's **Foreign Function & Memory (FFM) API** (introduced in Project Panama, finalized in Java 22). This allows Java to invoke C-compatible Rust functions directly and access off-heap native memory with near-zero overhead, bypassing the legacy Java Native Interface (JNI).

  === Native Library Loading
  Dynamic library loading is handled by the `NativeLibrary` class, which resolves the OS and CPU architecture at runtime and loads the appropriate binary (`.dll`, `.so`, or `.dylib`):
  * **System Path Lookup**: Attempts to load using standard `System.loadLibrary("tfhe")`.
  * **Classpath Extraction**: If not found in the system library path, it extracts the platform-specific library resource from the JAR (`/native/<os>/<arch>/<libname>`) to a temporary folder and loads it via `System.load`.

  Supported operating systems and architectures:
  * **OS**: `osx` (macOS), `windows`, `linux`
  * **Arch**: `x86_64` (AMD64), `aarch_64` (Apple Silicon / ARM64)

  === Native Memory Safety & Garbage Collection Integration
  Because Java's garbage collector (GC) cannot automatically track off-heap memory allocations, TFHE-Java bridges the lifetime of native pointers with Java heap references:
  * **Memory Arenas**: The global `LIBRARY_ARENA` (`java.lang.foreign.Arena.ofAuto()`) is used to allocate global resources.
  * **Cleaner API**: Memory deallocation is managed using the JVM's `java.lang.ref.Cleaner` thread. Every native object register-binds its raw memory segment and a dedicated Rust destructor function to a `NativeHandle` task. When the JVM GC reclaims the Java object, the `Cleaner` thread executes `NativeHandle.run()` to trigger the Rust deallocation routine.
  * **Manual Deallocation**: For heavy operations (e.g. loops), developers can call `.destroy()` to immediately invoke the cleaner's deallocation and prevent high memory watermark states.

  === Panama Address & Pointer Abstractions
  The library provides structured pointer wrappers:
  * **`NativeAddress`**: The base class wrapping a `MemorySegment` off-heap pointer.
  * **`NativePointer`**: Specifically models a pointer-to-pointer (`*mut *mut T`) used by `tfhe-rs` constructors. It handles dereferencing the address to extract the actual value segment.
  * **`NativeHandle`**: Implements `Runnable` and executes the registered Rust `destroyer` function on the `MemorySegment` address.

  Scenario: Root

