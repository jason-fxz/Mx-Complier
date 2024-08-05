@echo off
REM Set the path to the ANTLR JAR file
set ANTLR_JAR=C:\antlr\antlr-4.13.1-complete.jar

REM Set the grammar file name (without .g4 extension)
set GRAMMAR_NAME=Mx

REM Generate the parser and lexer
java -jar %ANTLR_JAR% %GRAMMAR_NAME%.g4

REM Compile the generated Java files
javac *.java

REM Run the test rig (replace 'prog' with the starting rule of your grammar and 'input.txt' with your input file)
java org.antlr.v4.gui.TestRig %GRAMMAR_NAME% prog -gui input.txt

pause