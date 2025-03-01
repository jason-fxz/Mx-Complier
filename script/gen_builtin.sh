#!/bin/bash


# 定义输入文件和输出文件
input_file=$(find . -name "builtin.c")

echo $input_file
tmp_file="builtin.tmp.ll"
output_file="builtin.ll"
asm_file="builtin.s"

# 生成 LLVM IR
echo "gen LLVM IR..."
clang -S -emit-llvm --target=riscv32-unknown-elf -O2 -fno-discard-value-names -fno-builtin-printf -fno-builtin-memcpy -fno-builtin-malloc -fno-builtin-strlen \
    $input_file -o $tmp_file


# 定义要替换的字符串和替换后的字符串的二元组数组
declare -A replacements=(
    ["string_length"]="string.length"
    ["string_substring"]="string.substring"
    ["string_parseInt"]="string.parseInt"
    ["string_ord"]="string.ord"
)

# 读取文件内容
content=$(cat "$tmp_file")

# 进行替换
for key in "${!replacements[@]}"; do
    content=$(echo "$content" | sed "s/$key/${replacements[$key]}/g")
done

# 保存到新文件
echo "$content" > "$output_file"

# 删除临时文件
rm "$tmp_file"

echo "replace ok, save to $output_file"

# 生成汇编代码

echo "gen asm..."
llc -march=riscv32 -filetype=asm $output_file -o $asm_file