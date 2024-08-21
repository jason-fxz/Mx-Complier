find src -name ".antlr" -exec rm -r {} \;
find src -name '*.java' | xargs javac -d bin -cp /ulib/antlr-4.13.1-complete.jar