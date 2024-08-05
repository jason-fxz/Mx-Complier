// Lexer rules
lexer grammar MxLexer;

// Arithmetic operators
Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Mod: '%';

// Relation operators
Gt: '>';
Lt: '<';
Geq: '>=';
Leq: '<=';
Ne: '!=';
Eq: '==';

// Logic operator
And: '&&';
Or: '||';
Not: '!';

// Bit operator
RightShift: '>>';
LeftShift: '<<';
BitAnd: '&';
BitOr: '|';
BitXor: '^';
BitNot: '~';

// Assignment operator
Assign: '=';

// Self inc/dec operator
SelfInc: '++';
Selfdec: '--';

// Component operator
Dot: '.';

// Brackets & Partition
LeftParen: '('; // Parentheses 
RightParen: ')';
LeftBrack: '['; // Brackets 
RightBrack: ']';
LeftBrace: '{'; // Curly Braces
RightBrace: '}';
Comma: ',';
SemiColon: ';';


// Ternary operator
Ques: '?';
Colon: ':';

// Keywords
Void: 'void';
Bool: 'bool';
Int: 'int';
String: 'string';
New: 'new';
Class: 'class';
Null: 'null';
True: 'true';
False: 'false';
This: 'this';
If: 'if';
Else: 'else';
For: 'for';
While: 'while';
Break: 'break';
Continue: 'continue';
Return: 'return';



// Special symbols
fragment EscapeChar: '\\\\' | '\\n' | '\\"';   // Escape characters
fragment NumberChar: [0-9];
fragment LetterChar: [A-Za-z];
// fragment PrintableChar: ~[\n\\"];
fragment StringChar: ~[\n\\"]|EscapeChar;
fragment FormatStrChar: ~[\n\\"$]|'$$'|EscapeChar;
Identifier: LetterChar(LetterChar|NumberChar|'_')*;

// Literal 
LogicLiteral: False | True;
IntegerLiteral: [1-9]NumberChar*|'0';
StringLiteral: '"' StringChar*? '"';
NullLiteral: Null;


// Format String
FormatStrI: 'f"' (FormatStrChar)*? '"';

FormatStrL: 'f"' (FormatStrChar)*? '$';
FormatStrM: '$' (FormatStrChar)*? '$';
FormatStrR: '$' (FormatStrChar)*? '"';


// Comments
LineComment: '//' .*? NewLine -> skip;
BlockComment: '/*' .*? '*/' -> skip;

// Skip 
NewLine: ('\r' | '\n' | '\r\n') -> skip;
WhiteSpace: (' ' | '\t') -> skip;