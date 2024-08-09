parser grammar MxParser;

@header {package Grammar;}

options {
	tokenVocab = MxLexer;
}

program: (varDef | classDef | funcDef)* EOF;

classDef:
	Class name = Identifier '{' (
		varDef
		| funcDef
		| classConstruct
	)* '}' ';';
classConstruct: name = Identifier '(' ')' blockStmt;

funcDef:
	typeName name = Identifier '(' (funcParamList)? ')' body = blockStmt;
funcParamList: funcParam (',' funcParam)*;
funcParam: typeName varConstruct;

varDef: typeName varConstruct (',' varConstruct)* ';';
varConstruct: name = Identifier ('=' expr)?;

// Expression Stament
exprStmt: expr ';';
exprList: expr (',' expr)*;

typeName: type arrayUnit*;
type: Identifier | baseType;
baseType: Bool | Int | String | Void;

// Expression
expr:
	'(' expr ')'														# ParenExpr
	| expr '(' exprList? ')'											# FuncExpr
	| New type ('(' ')')?												# NewVarExpr
	| New arrayFuck arrayInitial?								# NewArrayExpr
	| array = expr '[' index = expr ']'									# ArrayExpr
	| expr '.' Identifier												# MemberExpr
	| lhs = expr op = (SelfInc | Selfdec)								# RightExpr
	| <assoc = right> op = (SelfInc | Selfdec) rhs = expr				# LeftExpr
	| <assoc = right> op = (Not | BitNot | Sub) rhs = expr				# LeftExpr
	| lhs = expr op = (Mul | Div | Mod) rhs = expr						# BinaryExpr
	| lhs = expr op = (Add | Sub) rhs = expr							# BinaryExpr
	| lhs = expr op = (RightShift | LeftShift) rhs = expr				# BinaryExpr
	| lhs = expr op = (Gt | Lt | Geq | Leq) rhs = expr					# BinaryExpr
	| lhs = expr op = (Ne | Eq) rhs = expr								# BinaryExpr
	| lhs = expr op = BitAnd rhs = expr									# BinaryExpr
	| lhs = expr op = BitXor rhs = expr									# BinaryExpr
	| lhs = expr op = BitOr rhs = expr									# BinaryExpr
	| lhs = expr op = And rhs = expr									# BinaryExpr
	| lhs = expr op = Or rhs = expr										# BinaryExpr
	| <assoc = right> cond = expr Ques then = expr Colon else = expr	# ConditionExpr
	| <assoc = right> lhs = expr op = Assign rhs = expr					# AssignExpr
	| arrayInitial														# ArrayInitExpr
	| literalExpr														# LiterExpr
	| formatStrExpr														# FormatStringExpr
	| (Identifier | This)												# AtomExpr;

// literal Expression
literalExpr:
	False
	| True
	| Null
	| StringLiteral
	| IntegerLiteral;

arrayInitial: '{' exprList '}';

// Format String
formatStrExpr:
	FormatStrI
	| FormatStrL expr (FormatStrM expr)* FormatStrR;

// Statement
stmt:
	blockStmt
	| forStmt
	| ifStmt
	| forStmt
	| whileStmt
	| jumpStmt
	| exprStmt
	| varDefStmt
	| emptyStmt;

varDefStmt: varDef;

blockStmt: '{' stmt* '}';
ifStmt: If '(' cond = expr ')' then = stmt (Else else = stmt)?;
forStmt:
	For '(' init = stmt cond = expr? ';' step = expr? ')' body = stmt;
whileStmt: While '(' cond = expr ')' body = stmt;
emptyStmt: ';';
jumpStmt: (Continue | Break | (Return expr?)) ';';

arrayUnit: '[' ']';
arrayNewUnit: ('[' good+=expr ']')* ('[' ']')* ('[' bad+=expr ']')*;
arrayFuck: type arrayNewUnit;
