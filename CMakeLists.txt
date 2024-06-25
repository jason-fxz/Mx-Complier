cmake_minimum_required(VERSION 3.20)
project(Calc)

# set(CMAKE_CXX_COMPILER /usr/bin/g++-11)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)


add_subdirectory(generated)

include_directories(/usr/include/antlr4-runtime/)
include_directories(/usr/local/include/antlr4-runtime/)
include_directories(/usr/local/include/c++/11)
include_directories(generated/)


file(GLOB_RECURSE sources src/*.cpp src/*.h)

# add_definitions(-D__bool_32)
add_executable(Calc main.cpp ${sources})

target_link_libraries(Calc CalcAntlr)