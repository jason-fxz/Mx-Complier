.PHONY: build
build:
	find -name '*.java' | xargs javac -d bin -cp /ulib/antlr-4.13.1-complete.jar

.PHONY: run
run:
	cd bin && java -cp /ulib/antlr-4.13.1-complete.jar:. Main

.PHONY: Compiler
Compiler:
	./script/compile.sh

.PHONY: Sema
Sema: Compiler
	./testcases/sema/scripts/test.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main' $(file)

.PHONY: Semall
Semall: Compiler
	./testcases/sema/scripts/test_all.bash 'java -cp /usr/share/java/antlr-4.13.1-complete.jar:bin Main' testcases/sema/