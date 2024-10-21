JAVA_RUN_OPTS=-ea -Xss10m -cp /usr/share/java/antlr-4.13.1-complete.jar:bin

.PHONY: build
build: 
	./script/compile.sh

.PHONY: run
run: 
	java -Xss10m -cp /ulib/antlr-4.13.1-complete.jar:bin Main -S -log-time && cat ./src/builtin/builtin_opt.s

.PHONY: clean
clean:
	rm -r bin

.PHONY: genbuiltin
genbuiltin:
	./script/gen_builtin.sh

.PHONY: Sema
Sema: build
	./testcases/sema/scripts/test.bash 'java $(JAVA_RUN_OPTS) Main -S' $(file)

.PHONY: Semall
Semall: build
	time -p ./testcases/sema/scripts/test_all.bash 'java $(JAVA_RUN_OPTS) Main -S' testcases/sema/

.PHONY: irtest
irtest: build genbuiltin
	java $(JAVA_RUN_OPTS) Main -emit-llvm -f test.mx -o output.ll\
	&& ./script/gen_ir.sh

.PHONY: asmtest
asmtest: build genbuiltin
	java $(JAVA_RUN_OPTS) Main -S -f test.mx -o output.s\
	&& reimu --all -f output.s,builtin.s -i test.in -o test.out

.PHONY: llvmall
llvmall: build genbuiltin
	./testcases/codegen/scripts/test_llvm_ir_all.bash 'java $(JAVA_RUN_OPTS) Main -emit-llvm' testcases/codegen ./builtin.ll

.PHONY: llvm
llvm: build genbuiltin
	./testcases/codegen/scripts/test_llvm_ir.bash 'java $(JAVA_RUN_OPTS) Main -emit-llvm -debug-ast' $(file) ./builtin.ll

.PHONY: asmall
asmall: build
	./testcases/codegen/scripts/test_asm_all.bash 'java $(JAVA_RUN_OPTS) Main -S' testcases/codegen ./src/builtin/builtin_opt.s

.PHONY: asm
asm: build
	./testcases/codegen/scripts/test_asm.bash 'java $(JAVA_RUN_OPTS) Main -S -log-time' $(file) ./src/builtin/builtin_opt.s