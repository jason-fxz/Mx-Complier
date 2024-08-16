# IR

## IR Structure 

```plaintext
LLVM IR
|- IRStructDef (s)
|- IRglobalVarDef (s)
|- IRFuncDef (s)
   |- block (s)
      |- ins (s)
      |- jump ins

```

## Build IR from AST

for ExprNode:

   after visit, return a var (type + name), which is the result of the expression. If the expression is Lvalue, also return the addr or expr

   for atomExpr:
      var:
         globelvar: @var is addr
         funcparam: %var.addr is addr 
         localvar:  %var is addr
         this:      %this is addr
      
         return (exprVar, exprAddr)

      func:

for For/While/If StmtNode:

   after visit, no return;
   but current_block is modified to for.end/if.end/while.end
   Once we create a block, put it in the blocklist

(for.init:)
for.cond:
for.body:
for.inc:
for.end:

if.then:
if.else:
if.end:

while.cond:
while.body:
while.end:


for FuncDefNode:

   we need to put all the varDef in entry block
   set current to a entry block


for blockStmt:

   just visit blocklist


