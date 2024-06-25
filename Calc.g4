// Calc.g4
grammar Calc;

// 规则定义
prog:   stat+ ;
stat:   expr NEWLINE
    |   ID ASSIGN expr NEWLINE
    |   NEWLINE
    ;
expr:   expr (MUL|DIV) expr
    |   expr (ADD|SUB) expr
    |   INT
    |   ID
    |   '(' expr ')'
    ;

// 词法规则
ID  :   [a-zA-Z]+ ;
INT :   [0-9]+ ;
NEWLINE: [\r\n]+ ;
WS  :   [ \t]+ -> skip ;
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
ASSIGN : '=';


