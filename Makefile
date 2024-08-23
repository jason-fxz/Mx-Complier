.PHONY: build
build:
	./script/compile.sh

.PHONY: run
run:
	cd bin && java -cp /ulib/antlr-4.13.1-complete.jar:. Main -emit-llvm

.PHONY: Sema
Sema: build
	./testcases/sema/scripts/test.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -fsyntax-only' $(file)

.PHONY: Semall
Semall: build
	time -p ./testcases/sema/scripts/test_all.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -fsyntax-only' testcases/sema/

.PHONY: clean
clean:
	rm -r bin

.PHONY: genbuiltin
genbuiltin:
	./script/gen_builtin.sh

.PHONY: irtest
irtest: build genbuiltin
	java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -emit-llvm -f test.mx -o output.ll\
	&& ./script/gen_ir.sh

.PHONY: llvmall
llvmall: build genbuiltin
	./testcases/codegen/scripts/test_llvm_ir_all.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -emit-llvm' testcases/codegen ./builtin.ll


.PHONY: llvm
llvm: build genbuiltin
	./testcases/codegen/scripts/test_llvm_ir.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -emit-llvm -debug-ast' $(file) ./builtin.ll

.PHONY: asmall
asmall: build genbuiltin
	./testcases/codegen/scripts/test_asm_all.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S' testcases/codegen ./builtin.s

.PHONY: asm
asm: build genbuiltin
	./testcases/codegen/scripts/test_asm.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S' $(file) ./builtin.s
