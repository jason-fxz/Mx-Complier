.PHONY: build
build: 
	./script/compile.sh

.PHONY: run
run: 
	java -Xss10m -cp /ulib/antlr-4.13.1-complete.jar:bin Main -S && cat ./src/builtin/builtin.s

.PHONY: Sema
Sema: build
	./testcases/sema/scripts/test.bash 'java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S' $(file)

.PHONY: Semall
Semall: build
	time -p ./testcases/sema/scripts/test_all.bash 'java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S' testcases/sema/

.PHONY: clean
clean:
	rm -r bin

.PHONY: genbuiltin
genbuiltin:
	./script/gen_builtin.sh

.PHONY: irtest
irtest: build genbuiltin
	java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -emit-llvm -f test.mx -o output.ll\
	&& ./script/gen_ir.sh

.PHONY: asmtest
asmtest: build genbuiltin
	java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S -f test.mx -o output.s\
	&& reimu -f output.s,builtin.s -i test.in -o test.out && cat test.out

.PHONY: llvmall
llvmall: build genbuiltin
	./testcases/codegen/scripts/test_llvm_ir_all.bash 'java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -emit-llvm' testcases/codegen ./builtin.ll

.PHONY: llvm
llvm: build genbuiltin
	./testcases/codegen/scripts/test_llvm_ir.bash 'java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -emit-llvm -debug-ast' $(file) ./builtin.ll

.PHONY: asmall
asmall: build genbuiltin
	./testcases/codegen/scripts/test_asm_all.bash 'java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S' testcases/codegen ./builtin.s

.PHONY: asm
asm: build genbuiltin
	./testcases/codegen/scripts/test_asm.bash 'java -ea -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main -S' $(file) ./builtin.s
