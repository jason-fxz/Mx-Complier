#!/usr/bin/zsh
alias antlr4="java -jar ./lib/antlr-4.13.1-complete.jar"
antlr4 -visitor ./src/Grammar/MxLexer.g4
antlr4 -visitor ./src/Grammar/MxParser.g4
# mv ./MxAntlr/src/*.java ./src/Parser