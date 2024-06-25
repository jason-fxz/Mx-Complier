cmake_minimum_required(VERSION 3.20)

include_directories(/usr/include/antlr4-runtime/)
include_directories(/usr/local/include/antlr4-runtime/)

file(GLOB AntlrSources *.cpp)

add_library(CalcAntlr STATIC ${AntlrSources})
target_link_libraries(CalcAntlr PUBLIC antlr4-runtime)