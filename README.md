# Mx-Complier

A Compiler of Mx, See [Compiler-Design-Implementation](https://github.com/ACMClassCourses/Compiler-Design-Implementation).

## TODO

- [x] Semantic
  - [x] Antlr Lexer / Parser
  - [x] Build an AST
  - [x] Semantic Check
- [x] Codegen
  - [x] Translate AST to LLVM IR
  - [x] Translate IR to RISC-V Assembly
- [x] Optimization
  - [x] Mem2reg
  - [x] DCE
  - [x] Register Allocation
  - [x] SCCP
  - [x] Arithmetic-Simplification
  - [x] Inlining
  - [ ] loop-invariant-code-motion
  - [ ] local-CSE
  - [x] Jump-Elimination
  - [ ] Tail-Call-Optimization
  - [x] Trail Optimization
  - Some useful tricks:
    - [x] Global Localization
    - [x] use s0-s11 to save caller-saved registers
    - [ ] buildin functions optimization (by human intelligence)

## Overview

- Semantic
  - Use Antlr to generate lexer and parser, build an syntax tree.
    See .g4 file in `src/Grammar`.
  - Build an AST on the basis of syntax tree.
    See `src/AST` for AST Nodes, file `src/Frontend/ASTBuilder.java` is ASTBuilder.
  - Do semantic check on AST.
    See `src/Frontend/SemanticCollector.java` and `src/Frontend/SemanticChecker.java` for details.

- Codegen
  - Translate AST to LLVM IR.
    See `src/IR` for IR Nodes. See `src/Frontend/IRBuilder.java` for details.
  - Translate IR to RISC-V Assembly.
    See `src/Backend/NaiveASMBuilder.java` for details. `src/ASM` for ASM Nodes and RISC-V Register Definition. `src/builtin` for builtin functions implementation in C-language and their corresponding RISC-V Assembly.

- Optimize
  - Mem2reg
    remove alloc instructions in IR and insert phi instructions.
    See `src/Optimize/Mem2Reg.java` for details.

  - Register Allocation
    Use **SSA-RA** to allocate registers. (Since llvm IR is already in SSA form)

    liveness -> spill -> color -> coalesce -> eliminate

    See `src/Allocator` for details.

    ref: [寄存器分配引论 华保健](https://books.google.com.hk/books/about/%E5%AF%84%E5%AD%98%E5%99%A8%E5%88%86%E9%85%8D%E5%BC%95%E8%AE%BA.html?id=bPdOzwEACAAJ&redir_esc=y)

  - DCE (Dead Code Elimination)
    See `src/Optimize/DCE.java` for details.

  - SCCP (Sparse Conditional Constant Propagation)
    See `src/Optimize/SCCP.java` for details.

  - Jump-Elimination
    Just delete the block that only has one jump instruction.

  - Inlining
    Run Tarjan algorithm to find SCCs in call-graph, and inline functions in SCCs.

    See `src/Optimize/Inline.java` for details.
