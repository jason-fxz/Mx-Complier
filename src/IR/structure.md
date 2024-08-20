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

use **IRHelper** to handle value the return of visit AST Node

for ExprNode:

   after visit, return a **var** (type + name), which is the result of the expression. If the expression is Lvalue, also return the **addr** for expr 

for atomExpr:
- var:
   - globelvar: @var is addr
   - func param: 
      %var.addr = alloca
      store %var, ptr var.addr
   - localvar:  %var is addr
   - this:      %this is ptr addr, use this as normal ptr function params         
   - class var:
      %this.var is  addr
- func:
   ```plaintext
        funcExpr
        /    \
      Expr   (Exprlist)
   ```

   Expr can be atomExpr or memberExpr, for atomExpr, return the function name @\<funcName\>, for memberExpr, return @\<className\>.\<funcName\>  
      
   For the class functions (methods), bare access to class member should be replace by this.\<member\>

for For/While/If StmtNode:

   after visit, no return;
   but current_block is modified to for.end/if.end/while.end
   Once we create a block, put it in the blocklist

```plaintext
(for.init:)
for.cond:
for.body:
for.step:
for.end:

if.then:
if.else:
if.end:

while.cond:
while.body:
while.end:
```

for FuncDefNode:

   we need to put all the varDef in entry block
   set current to a entry block


for blockStmt:

   just visit blocklist


for Short-circuit evaluation:

```plaintext

   ... ; calc lhs
   ; br to land.rhs or land.end

land.rhs:
   ... ; calc rhs
   ; br to land.end

land.end:
   phi, ; 

```   

for litreal string

   global define
   
   @__const.str = private unnamed_addr constant [n x i8] c"Hello World\00"



for NewExpr:


for NewArray

```
int a[4][5][6]

var a3 = malloc 4 * 4
for (int i = 0; i < 4; ++i) {
   var a2 = malloc 5 * 4
   for (int j = 0; j < 5; ++j) {
      var a1 = malloc 6 * 4
      for (int k = 0; k < 6; ++k) {
         a1[k] = null;
      }
      a2[j] = a1;
   }
   a3[i] = a2;
}


int []asize
ptr array_help(int idx) {
   if (idx == size) return null;
   var a = malloc asize[idx]
   for (int i = 0; i < asize[idx]; ++i) {
      a[i] = array_help(idx + 1);
   }
   return a;
}
```

