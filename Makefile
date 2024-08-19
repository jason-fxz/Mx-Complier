.PHONY: build
build:
	find -name '*.java' | xargs javac -d bin -cp /ulib/antlr-4.13.1-complete.jar

.PHONY: run
run:
	cd bin && java -cp /ulib/antlr-4.13.1-complete.jar:. Main

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

	