#clang --target=riscv32-unknown-elf -S builtin.ll -o builtin.s
#clang  -falign-functions=1   --target=riscv32-unknown-elf -S output.ll -o test.s
##llc -align-all-functions=1 -align-all-blocks=1 output.ll -o test.s
#reimu

#sed 's/string_/string./g;s/array_/array./g' output_imm.ll >output.ll

echo -e "\033[0;34mgen LLVM IR...\033[0m"
clang -m32 builtin.ll output.ll -o test.out
if [ $? -ne 0 ]; then
    echo "\033[0;31mgen test.out failed\033[0m"
    exit 1
fi
echo -e "\033[0;34mrun test.out\033[0m"
./test.out
echo -e "\033[0;34mreturn \033[0m$?" 