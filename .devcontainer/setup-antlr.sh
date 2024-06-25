#!/bin/bash

# 下载 ANTLR 工具
curl -O https://www.antlr.org/download/antlr-4.13.1-complete.jar

# 将 ANTLR 工具复制到 /usr/local/bin
cp antlr-4.13.1-complete.jar /usr/local/bin/antlr4.jar
chmod +x /usr/local/bin/antlr4.jar

# 设置 CLASSPATH 和 PATH 环境变量
export CLASSPATH=".:/usr/local/bin/antlr4.jar:$CLASSPATH"
export PATH="/usr/local/bin:$PATH"
