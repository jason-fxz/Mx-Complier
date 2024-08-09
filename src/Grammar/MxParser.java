// Generated from ./src/Grammar/MxParser.g4 by ANTLR 4.13.1
package Grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Add=1, Sub=2, Mul=3, Div=4, Mod=5, Gt=6, Lt=7, Geq=8, Leq=9, Ne=10, Eq=11, 
		And=12, Or=13, Not=14, RightShift=15, LeftShift=16, BitAnd=17, BitOr=18, 
		BitXor=19, BitNot=20, Assign=21, SelfInc=22, Selfdec=23, Dot=24, LeftParen=25, 
		RightParen=26, LeftBrack=27, RightBrack=28, LeftBrace=29, RightBrace=30, 
		Comma=31, SemiColon=32, Ques=33, Colon=34, Void=35, Bool=36, Int=37, String=38, 
		New=39, Class=40, Null=41, True=42, False=43, This=44, If=45, Else=46, 
		For=47, While=48, Break=49, Continue=50, Return=51, Identifier=52, IntegerLiteral=53, 
		StringLiteral=54, FormatStrI=55, FormatStrL=56, FormatStrM=57, FormatStrR=58, 
		LineComment=59, BlockComment=60, NewLine=61, WhiteSpace=62;
	public static final int
		RULE_program = 0, RULE_classDef = 1, RULE_classConstruct = 2, RULE_funcDef = 3, 
		RULE_funcParamList = 4, RULE_funcParam = 5, RULE_varDef = 6, RULE_varConstruct = 7, 
		RULE_exprStmt = 8, RULE_exprList = 9, RULE_typeName = 10, RULE_arrayUnit = 11, 
		RULE_type = 12, RULE_baseType = 13, RULE_expr = 14, RULE_literalExpr = 15, 
		RULE_arrayInitial = 16, RULE_formatStrExpr = 17, RULE_stmt = 18, RULE_varDefStmt = 19, 
		RULE_blockStmt = 20, RULE_ifStmt = 21, RULE_forStmt = 22, RULE_whileStmt = 23, 
		RULE_emptyStmt = 24, RULE_jumpStmt = 25;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "classDef", "classConstruct", "funcDef", "funcParamList", 
			"funcParam", "varDef", "varConstruct", "exprStmt", "exprList", "typeName", 
			"arrayUnit", "type", "baseType", "expr", "literalExpr", "arrayInitial", 
			"formatStrExpr", "stmt", "varDefStmt", "blockStmt", "ifStmt", "forStmt", 
			"whileStmt", "emptyStmt", "jumpStmt"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'<'", "'>='", "'<='", 
			"'!='", "'=='", "'&&'", "'||'", "'!'", "'>>'", "'<<'", "'&'", "'|'", 
			"'^'", "'~'", "'='", "'++'", "'--'", "'.'", "'('", "')'", "'['", "']'", 
			"'{'", "'}'", "','", "';'", "'?'", "':'", "'void'", "'bool'", "'int'", 
			"'string'", "'new'", "'class'", "'null'", "'true'", "'false'", "'this'", 
			"'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Add", "Sub", "Mul", "Div", "Mod", "Gt", "Lt", "Geq", "Leq", "Ne", 
			"Eq", "And", "Or", "Not", "RightShift", "LeftShift", "BitAnd", "BitOr", 
			"BitXor", "BitNot", "Assign", "SelfInc", "Selfdec", "Dot", "LeftParen", 
			"RightParen", "LeftBrack", "RightBrack", "LeftBrace", "RightBrace", "Comma", 
			"SemiColon", "Ques", "Colon", "Void", "Bool", "Int", "String", "New", 
			"Class", "Null", "True", "False", "This", "If", "Else", "For", "While", 
			"Break", "Continue", "Return", "Identifier", "IntegerLiteral", "StringLiteral", 
			"FormatStrI", "FormatStrL", "FormatStrM", "FormatStrR", "LineComment", 
			"BlockComment", "NewLine", "WhiteSpace"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MxParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxParser.EOF, 0); }
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4505214535073792L) != 0)) {
				{
				setState(55);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(52);
					varDef();
					}
					break;
				case 2:
					{
					setState(53);
					classDef();
					}
					break;
				case 3:
					{
					setState(54);
					funcDef();
					}
					break;
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Class() { return getToken(MxParser.Class, 0); }
		public TerminalNode LeftBrace() { return getToken(MxParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(MxParser.RightBrace, 0); }
		public TerminalNode SemiColon() { return getToken(MxParser.SemiColon, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public List<ClassConstructContext> classConstruct() {
			return getRuleContexts(ClassConstructContext.class);
		}
		public ClassConstructContext classConstruct(int i) {
			return getRuleContext(ClassConstructContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(Class);
			setState(63);
			((ClassDefContext)_localctx).name = match(Identifier);
			setState(64);
			match(LeftBrace);
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4504115023446016L) != 0)) {
				{
				setState(68);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(65);
					varDef();
					}
					break;
				case 2:
					{
					setState(66);
					funcDef();
					}
					break;
				case 3:
					{
					setState(67);
					classConstruct();
					}
					break;
				}
				}
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(73);
			match(RightBrace);
			setState(74);
			match(SemiColon);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassConstructContext extends ParserRuleContext {
		public Token name;
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public BlockStmtContext blockStmt() {
			return getRuleContext(BlockStmtContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public ClassConstructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classConstruct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterClassConstruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitClassConstruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitClassConstruct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassConstructContext classConstruct() throws RecognitionException {
		ClassConstructContext _localctx = new ClassConstructContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classConstruct);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			((ClassConstructContext)_localctx).name = match(Identifier);
			setState(77);
			match(LeftParen);
			setState(78);
			match(RightParen);
			setState(79);
			blockStmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncDefContext extends ParserRuleContext {
		public Token name;
		public BlockStmtContext body;
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public BlockStmtContext blockStmt() {
			return getRuleContext(BlockStmtContext.class,0);
		}
		public FuncParamListContext funcParamList() {
			return getRuleContext(FuncParamListContext.class,0);
		}
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_funcDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			typeName();
			setState(82);
			((FuncDefContext)_localctx).name = match(Identifier);
			setState(83);
			match(LeftParen);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4504115023446016L) != 0)) {
				{
				setState(84);
				funcParamList();
				}
			}

			setState(87);
			match(RightParen);
			setState(88);
			((FuncDefContext)_localctx).body = blockStmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncParamListContext extends ParserRuleContext {
		public List<FuncParamContext> funcParam() {
			return getRuleContexts(FuncParamContext.class);
		}
		public FuncParamContext funcParam(int i) {
			return getRuleContext(FuncParamContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public FuncParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParamList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterFuncParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitFuncParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitFuncParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParamListContext funcParamList() throws RecognitionException {
		FuncParamListContext _localctx = new FuncParamListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_funcParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			funcParam();
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(91);
				match(Comma);
				setState(92);
				funcParam();
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncParamContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public VarConstructContext varConstruct() {
			return getRuleContext(VarConstructContext.class,0);
		}
		public FuncParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcParam; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterFuncParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitFuncParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitFuncParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncParamContext funcParam() throws RecognitionException {
		FuncParamContext _localctx = new FuncParamContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_funcParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			typeName();
			setState(99);
			varConstruct();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDefContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public List<VarConstructContext> varConstruct() {
			return getRuleContexts(VarConstructContext.class);
		}
		public VarConstructContext varConstruct(int i) {
			return getRuleContext(VarConstructContext.class,i);
		}
		public TerminalNode SemiColon() { return getToken(MxParser.SemiColon, 0); }
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			typeName();
			setState(102);
			varConstruct();
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(103);
				match(Comma);
				setState(104);
				varConstruct();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(110);
			match(SemiColon);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarConstructContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarConstructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varConstruct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterVarConstruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitVarConstruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitVarConstruct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarConstructContext varConstruct() throws RecognitionException {
		VarConstructContext _localctx = new VarConstructContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_varConstruct);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			((VarConstructContext)_localctx).name = match(Identifier);
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(113);
				match(Assign);
				setState(114);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprStmtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SemiColon() { return getToken(MxParser.SemiColon, 0); }
		public ExprStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprStmtContext exprStmt() throws RecognitionException {
		ExprStmtContext _localctx = new ExprStmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_exprStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			expr(0);
			setState(118);
			match(SemiColon);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterExprList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitExprList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			expr(0);
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(121);
				match(Comma);
				setState(122);
				expr(0);
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeNameContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> LeftBrack() { return getTokens(MxParser.LeftBrack); }
		public TerminalNode LeftBrack(int i) {
			return getToken(MxParser.LeftBrack, i);
		}
		public List<TerminalNode> RightBrack() { return getTokens(MxParser.RightBrack); }
		public TerminalNode RightBrack(int i) {
			return getToken(MxParser.RightBrack, i);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_typeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			type();
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LeftBrack) {
				{
				{
				setState(129);
				match(LeftBrack);
				setState(130);
				match(RightBrack);
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayUnitContext extends ParserRuleContext {
		public TerminalNode LeftBrack() { return getToken(MxParser.LeftBrack, 0); }
		public TerminalNode RightBrack() { return getToken(MxParser.RightBrack, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ArrayUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterArrayUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitArrayUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitArrayUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayUnitContext arrayUnit() throws RecognitionException {
		ArrayUnitContext _localctx = new ArrayUnitContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_arrayUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(LeftBrack);
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 139645124137205764L) != 0)) {
				{
				setState(137);
				expr(0);
				}
			}

			setState(140);
			match(RightBrack);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_type);
		try {
			setState(144);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				match(Identifier);
				}
				break;
			case Void:
			case Bool:
			case Int:
			case String:
				enterOuterAlt(_localctx, 2);
				{
				setState(143);
				baseType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode Bool() { return getToken(MxParser.Bool, 0); }
		public TerminalNode Int() { return getToken(MxParser.Int, 0); }
		public TerminalNode String() { return getToken(MxParser.String, 0); }
		public TerminalNode Void() { return getToken(MxParser.Void, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterBaseType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitBaseType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 515396075520L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayInitExprContext extends ExprContext {
		public ArrayInitialContext arrayInitial() {
			return getRuleContext(ArrayInitialContext.class,0);
		}
		public ArrayInitExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterArrayInitExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitArrayInitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitArrayInitExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryExprContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode Mul() { return getToken(MxParser.Mul, 0); }
		public TerminalNode Div() { return getToken(MxParser.Div, 0); }
		public TerminalNode Mod() { return getToken(MxParser.Mod, 0); }
		public TerminalNode Add() { return getToken(MxParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MxParser.Sub, 0); }
		public TerminalNode RightShift() { return getToken(MxParser.RightShift, 0); }
		public TerminalNode LeftShift() { return getToken(MxParser.LeftShift, 0); }
		public TerminalNode Gt() { return getToken(MxParser.Gt, 0); }
		public TerminalNode Lt() { return getToken(MxParser.Lt, 0); }
		public TerminalNode Geq() { return getToken(MxParser.Geq, 0); }
		public TerminalNode Leq() { return getToken(MxParser.Leq, 0); }
		public TerminalNode Ne() { return getToken(MxParser.Ne, 0); }
		public TerminalNode Eq() { return getToken(MxParser.Eq, 0); }
		public TerminalNode BitAnd() { return getToken(MxParser.BitAnd, 0); }
		public TerminalNode BitXor() { return getToken(MxParser.BitXor, 0); }
		public TerminalNode BitOr() { return getToken(MxParser.BitOr, 0); }
		public TerminalNode And() { return getToken(MxParser.And, 0); }
		public TerminalNode Or() { return getToken(MxParser.Or, 0); }
		public BinaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitBinaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ExprContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode This() { return getToken(MxParser.This, 0); }
		public AtomExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterAtomExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitAtomExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitAtomExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewVarExprContext extends ExprContext {
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public NewVarExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterNewVarExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitNewVarExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitNewVarExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public FuncExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterFuncExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitFuncExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitFuncExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignExprContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public AssignExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterAssignExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitAssignExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitAssignExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayExprContext extends ExprContext {
		public ExprContext array;
		public ExprContext index;
		public TerminalNode LeftBrack() { return getToken(MxParser.LeftBrack, 0); }
		public TerminalNode RightBrack() { return getToken(MxParser.RightBrack, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ArrayExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitArrayExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayExprContext extends ExprContext {
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<ArrayUnitContext> arrayUnit() {
			return getRuleContexts(ArrayUnitContext.class);
		}
		public ArrayUnitContext arrayUnit(int i) {
			return getRuleContext(ArrayUnitContext.class,i);
		}
		public ArrayInitialContext arrayInitial() {
			return getRuleContext(ArrayInitialContext.class,0);
		}
		public NewArrayExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterNewArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitNewArrayExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitNewArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MemberExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Dot() { return getToken(MxParser.Dot, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public MemberExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterMemberExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitMemberExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitMemberExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RightExprContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SelfInc() { return getToken(MxParser.SelfInc, 0); }
		public TerminalNode Selfdec() { return getToken(MxParser.Selfdec, 0); }
		public RightExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterRightExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitRightExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitRightExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FormatStringExprContext extends ExprContext {
		public FormatStrExprContext formatStrExpr() {
			return getRuleContext(FormatStrExprContext.class,0);
		}
		public FormatStringExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterFormatStringExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitFormatStringExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitFormatStringExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConditionExprContext extends ExprContext {
		public ExprContext cond;
		public ExprContext then;
		public ExprContext else_;
		public TerminalNode Ques() { return getToken(MxParser.Ques, 0); }
		public TerminalNode Colon() { return getToken(MxParser.Colon, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ConditionExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterConditionExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitConditionExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitConditionExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiterExprContext extends ExprContext {
		public LiteralExprContext literalExpr() {
			return getRuleContext(LiteralExprContext.class,0);
		}
		public LiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterLiterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitLiterExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LeftExprContext extends ExprContext {
		public Token op;
		public ExprContext rhs;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SelfInc() { return getToken(MxParser.SelfInc, 0); }
		public TerminalNode Selfdec() { return getToken(MxParser.Selfdec, 0); }
		public TerminalNode Not() { return getToken(MxParser.Not, 0); }
		public TerminalNode BitNot() { return getToken(MxParser.BitNot, 0); }
		public TerminalNode Sub() { return getToken(MxParser.Sub, 0); }
		public LeftExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterLeftExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitLeftExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitLeftExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(149);
				match(LeftParen);
				setState(150);
				expr(0);
				setState(151);
				match(RightParen);
				}
				break;
			case 2:
				{
				_localctx = new NewArrayExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(153);
				match(New);
				setState(154);
				type();
				setState(156); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(155);
						arrayUnit();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(158); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(161);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(160);
					arrayInitial();
					}
					break;
				}
				}
				break;
			case 3:
				{
				_localctx = new NewVarExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(163);
				match(New);
				setState(164);
				type();
				setState(167);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(165);
					match(LeftParen);
					setState(166);
					match(RightParen);
					}
					break;
				}
				}
				break;
			case 4:
				{
				_localctx = new LeftExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(169);
				((LeftExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==SelfInc || _la==Selfdec) ) {
					((LeftExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(170);
				((LeftExprContext)_localctx).rhs = expr(18);
				}
				break;
			case 5:
				{
				_localctx = new LeftExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				((LeftExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1064964L) != 0)) ) {
					((LeftExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(172);
				((LeftExprContext)_localctx).rhs = expr(17);
				}
				break;
			case 6:
				{
				_localctx = new ArrayInitExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(173);
				arrayInitial();
				}
				break;
			case 7:
				{
				_localctx = new LiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(174);
				literalExpr();
				}
				break;
			case 8:
				{
				_localctx = new FormatStringExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				formatStrExpr();
				}
				break;
			case 9:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(176);
				_la = _input.LA(1);
				if ( !(_la==This || _la==Identifier) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(236);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(234);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(179);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(180);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(181);
						((BinaryExprContext)_localctx).rhs = expr(17);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(182);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(183);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Add || _la==Sub) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(184);
						((BinaryExprContext)_localctx).rhs = expr(16);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(185);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(186);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==RightShift || _la==LeftShift) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(187);
						((BinaryExprContext)_localctx).rhs = expr(15);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(188);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(189);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 960L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(190);
						((BinaryExprContext)_localctx).rhs = expr(14);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(191);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(192);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Ne || _la==Eq) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(193);
						((BinaryExprContext)_localctx).rhs = expr(13);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(194);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(195);
						((BinaryExprContext)_localctx).op = match(BitAnd);
						setState(196);
						((BinaryExprContext)_localctx).rhs = expr(12);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(197);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(198);
						((BinaryExprContext)_localctx).op = match(BitXor);
						setState(199);
						((BinaryExprContext)_localctx).rhs = expr(11);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(200);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(201);
						((BinaryExprContext)_localctx).op = match(BitOr);
						setState(202);
						((BinaryExprContext)_localctx).rhs = expr(10);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(203);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(204);
						((BinaryExprContext)_localctx).op = match(And);
						setState(205);
						((BinaryExprContext)_localctx).rhs = expr(9);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						((BinaryExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(206);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(207);
						((BinaryExprContext)_localctx).op = match(Or);
						setState(208);
						((BinaryExprContext)_localctx).rhs = expr(8);
						}
						break;
					case 11:
						{
						_localctx = new ConditionExprContext(new ExprContext(_parentctx, _parentState));
						((ConditionExprContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(209);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(210);
						match(Ques);
						setState(211);
						((ConditionExprContext)_localctx).then = expr(0);
						setState(212);
						match(Colon);
						setState(213);
						((ConditionExprContext)_localctx).else_ = expr(6);
						}
						break;
					case 12:
						{
						_localctx = new AssignExprContext(new ExprContext(_parentctx, _parentState));
						((AssignExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(215);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(216);
						((AssignExprContext)_localctx).op = match(Assign);
						setState(217);
						((AssignExprContext)_localctx).rhs = expr(5);
						}
						break;
					case 13:
						{
						_localctx = new FuncExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(218);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(219);
						match(LeftParen);
						setState(221);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 139645124137205764L) != 0)) {
							{
							setState(220);
							exprList();
							}
						}

						setState(223);
						match(RightParen);
						}
						break;
					case 14:
						{
						_localctx = new ArrayExprContext(new ExprContext(_parentctx, _parentState));
						((ArrayExprContext)_localctx).array = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(224);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(225);
						match(LeftBrack);
						setState(226);
						((ArrayExprContext)_localctx).index = expr(0);
						setState(227);
						match(RightBrack);
						}
						break;
					case 15:
						{
						_localctx = new MemberExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(229);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(230);
						match(Dot);
						setState(231);
						match(Identifier);
						}
						break;
					case 16:
						{
						_localctx = new RightExprContext(new ExprContext(_parentctx, _parentState));
						((RightExprContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(232);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(233);
						((RightExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==SelfInc || _la==Selfdec) ) {
							((RightExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(238);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralExprContext extends ParserRuleContext {
		public TerminalNode False() { return getToken(MxParser.False, 0); }
		public TerminalNode True() { return getToken(MxParser.True, 0); }
		public TerminalNode Null() { return getToken(MxParser.Null, 0); }
		public TerminalNode StringLiteral() { return getToken(MxParser.StringLiteral, 0); }
		public TerminalNode IntegerLiteral() { return getToken(MxParser.IntegerLiteral, 0); }
		public LiteralExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterLiteralExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitLiteralExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitLiteralExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralExprContext literalExpr() throws RecognitionException {
		LiteralExprContext _localctx = new LiteralExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_literalExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 27036990927011840L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayInitialContext extends ParserRuleContext {
		public TerminalNode LeftBrace() { return getToken(MxParser.LeftBrace, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public TerminalNode RightBrace() { return getToken(MxParser.RightBrace, 0); }
		public ArrayInitialContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitial; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterArrayInitial(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitArrayInitial(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitArrayInitial(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitialContext arrayInitial() throws RecognitionException {
		ArrayInitialContext _localctx = new ArrayInitialContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_arrayInitial);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(LeftBrace);
			setState(242);
			exprList();
			setState(243);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FormatStrExprContext extends ParserRuleContext {
		public TerminalNode FormatStrI() { return getToken(MxParser.FormatStrI, 0); }
		public TerminalNode FormatStrL() { return getToken(MxParser.FormatStrL, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode FormatStrR() { return getToken(MxParser.FormatStrR, 0); }
		public List<TerminalNode> FormatStrM() { return getTokens(MxParser.FormatStrM); }
		public TerminalNode FormatStrM(int i) {
			return getToken(MxParser.FormatStrM, i);
		}
		public FormatStrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formatStrExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterFormatStrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitFormatStrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitFormatStrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormatStrExprContext formatStrExpr() throws RecognitionException {
		FormatStrExprContext _localctx = new FormatStrExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_formatStrExpr);
		int _la;
		try {
			setState(257);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FormatStrI:
				enterOuterAlt(_localctx, 1);
				{
				setState(245);
				match(FormatStrI);
				}
				break;
			case FormatStrL:
				enterOuterAlt(_localctx, 2);
				{
				setState(246);
				match(FormatStrL);
				setState(247);
				expr(0);
				setState(252);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==FormatStrM) {
					{
					{
					setState(248);
					match(FormatStrM);
					setState(249);
					expr(0);
					}
					}
					setState(254);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(255);
				match(FormatStrR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public BlockStmtContext blockStmt() {
			return getRuleContext(BlockStmtContext.class,0);
		}
		public ForStmtContext forStmt() {
			return getRuleContext(ForStmtContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public JumpStmtContext jumpStmt() {
			return getRuleContext(JumpStmtContext.class,0);
		}
		public ExprStmtContext exprStmt() {
			return getRuleContext(ExprStmtContext.class,0);
		}
		public VarDefStmtContext varDefStmt() {
			return getRuleContext(VarDefStmtContext.class,0);
		}
		public EmptyStmtContext emptyStmt() {
			return getRuleContext(EmptyStmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_stmt);
		try {
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(259);
				blockStmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(260);
				forStmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(261);
				ifStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(262);
				forStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(263);
				whileStmt();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(264);
				jumpStmt();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(265);
				exprStmt();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(266);
				varDefStmt();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(267);
				emptyStmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDefStmtContext extends ParserRuleContext {
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public VarDefStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterVarDefStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitVarDefStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitVarDefStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefStmtContext varDefStmt() throws RecognitionException {
		VarDefStmtContext _localctx = new VarDefStmtContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_varDefStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			varDef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockStmtContext extends ParserRuleContext {
		public TerminalNode LeftBrace() { return getToken(MxParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(MxParser.RightBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterBlockStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitBlockStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStmtContext blockStmt() throws RecognitionException {
		BlockStmtContext _localctx = new BlockStmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_blockStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			match(LeftBrace);
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 144043690339352580L) != 0)) {
				{
				{
				setState(273);
				stmt();
				}
				}
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(279);
			match(RightBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends ParserRuleContext {
		public ExprContext cond;
		public StmtContext then;
		public StmtContext else_;
		public TerminalNode If() { return getToken(MxParser.If, 0); }
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode Else() { return getToken(MxParser.Else, 0); }
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			match(If);
			setState(282);
			match(LeftParen);
			setState(283);
			((IfStmtContext)_localctx).cond = expr(0);
			setState(284);
			match(RightParen);
			setState(285);
			((IfStmtContext)_localctx).then = stmt();
			setState(288);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(286);
				match(Else);
				setState(287);
				((IfStmtContext)_localctx).else_ = stmt();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStmtContext extends ParserRuleContext {
		public StmtContext init;
		public ExprContext cond;
		public ExprContext step;
		public StmtContext body;
		public TerminalNode For() { return getToken(MxParser.For, 0); }
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode SemiColon() { return getToken(MxParser.SemiColon, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ForStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitForStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_forStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(For);
			setState(291);
			match(LeftParen);
			setState(292);
			((ForStmtContext)_localctx).init = stmt();
			setState(294);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 139645124137205764L) != 0)) {
				{
				setState(293);
				((ForStmtContext)_localctx).cond = expr(0);
				}
			}

			setState(296);
			match(SemiColon);
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 139645124137205764L) != 0)) {
				{
				setState(297);
				((ForStmtContext)_localctx).step = expr(0);
				}
			}

			setState(300);
			match(RightParen);
			setState(301);
			((ForStmtContext)_localctx).body = stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStmtContext extends ParserRuleContext {
		public ExprContext cond;
		public StmtContext body;
		public TerminalNode While() { return getToken(MxParser.While, 0); }
		public TerminalNode LeftParen() { return getToken(MxParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(MxParser.RightParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(While);
			setState(304);
			match(LeftParen);
			setState(305);
			((WhileStmtContext)_localctx).cond = expr(0);
			setState(306);
			match(RightParen);
			setState(307);
			((WhileStmtContext)_localctx).body = stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EmptyStmtContext extends ParserRuleContext {
		public TerminalNode SemiColon() { return getToken(MxParser.SemiColon, 0); }
		public EmptyStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterEmptyStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitEmptyStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitEmptyStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyStmtContext emptyStmt() throws RecognitionException {
		EmptyStmtContext _localctx = new EmptyStmtContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_emptyStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			match(SemiColon);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JumpStmtContext extends ParserRuleContext {
		public TerminalNode SemiColon() { return getToken(MxParser.SemiColon, 0); }
		public TerminalNode Continue() { return getToken(MxParser.Continue, 0); }
		public TerminalNode Break() { return getToken(MxParser.Break, 0); }
		public TerminalNode Return() { return getToken(MxParser.Return, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public JumpStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).enterJumpStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxParserListener ) ((MxParserListener)listener).exitJumpStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxParserVisitor ) return ((MxParserVisitor<? extends T>)visitor).visitJumpStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStmtContext jumpStmt() throws RecognitionException {
		JumpStmtContext _localctx = new JumpStmtContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_jumpStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Continue:
				{
				setState(311);
				match(Continue);
				}
				break;
			case Break:
				{
				setState(312);
				match(Break);
				}
				break;
			case Return:
				{
				{
				setState(313);
				match(Return);
				setState(315);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 139645124137205764L) != 0)) {
					{
					setState(314);
					expr(0);
					}
				}

				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(319);
			match(SemiColon);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 14:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 16);
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 14);
		case 3:
			return precpred(_ctx, 13);
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 11);
		case 6:
			return precpred(_ctx, 10);
		case 7:
			return precpred(_ctx, 9);
		case 8:
			return precpred(_ctx, 8);
		case 9:
			return precpred(_ctx, 7);
		case 10:
			return precpred(_ctx, 6);
		case 11:
			return precpred(_ctx, 5);
		case 12:
			return precpred(_ctx, 24);
		case 13:
			return precpred(_ctx, 21);
		case 14:
			return precpred(_ctx, 20);
		case 15:
			return precpred(_ctx, 19);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001>\u0142\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000"+
		"8\b\u0000\n\u0000\f\u0000;\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"E\b\u0001\n\u0001\f\u0001H\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003V\b\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004"+
		"^\b\u0004\n\u0004\f\u0004a\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006j\b\u0006"+
		"\n\u0006\f\u0006m\t\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007t\b\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0005\t|\b\t\n\t\f\t\u007f\t\t\u0001\n\u0001\n\u0001\n\u0005"+
		"\n\u0084\b\n\n\n\f\n\u0087\t\n\u0001\u000b\u0001\u000b\u0003\u000b\u008b"+
		"\b\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0003\f\u0091\b\f\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0004\u000e\u009d\b\u000e\u000b\u000e"+
		"\f\u000e\u009e\u0001\u000e\u0003\u000e\u00a2\b\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00a8\b\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0003\u000e\u00b2\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00de\b\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00eb\b\u000e\n"+
		"\u000e\f\u000e\u00ee\t\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0005\u0011\u00fb\b\u0011\n\u0011\f\u0011\u00fe\t\u0011"+
		"\u0001\u0011\u0001\u0011\u0003\u0011\u0102\b\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0003\u0012\u010d\b\u0012\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0005\u0014\u0113\b\u0014\n\u0014\f\u0014\u0116\t\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0121\b\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0127\b\u0016\u0001\u0016\u0001"+
		"\u0016\u0003\u0016\u012b\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003"+
		"\u0019\u013c\b\u0019\u0003\u0019\u013e\b\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0000\u0001\u001c\u001a\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02\u0000\n\u0001"+
		"\u0000#&\u0001\u0000\u0016\u0017\u0003\u0000\u0002\u0002\u000e\u000e\u0014"+
		"\u0014\u0002\u0000,,44\u0001\u0000\u0003\u0005\u0001\u0000\u0001\u0002"+
		"\u0001\u0000\u000f\u0010\u0001\u0000\u0006\t\u0001\u0000\n\u000b\u0002"+
		"\u0000)+56\u0162\u00009\u0001\u0000\u0000\u0000\u0002>\u0001\u0000\u0000"+
		"\u0000\u0004L\u0001\u0000\u0000\u0000\u0006Q\u0001\u0000\u0000\u0000\b"+
		"Z\u0001\u0000\u0000\u0000\nb\u0001\u0000\u0000\u0000\fe\u0001\u0000\u0000"+
		"\u0000\u000ep\u0001\u0000\u0000\u0000\u0010u\u0001\u0000\u0000\u0000\u0012"+
		"x\u0001\u0000\u0000\u0000\u0014\u0080\u0001\u0000\u0000\u0000\u0016\u0088"+
		"\u0001\u0000\u0000\u0000\u0018\u0090\u0001\u0000\u0000\u0000\u001a\u0092"+
		"\u0001\u0000\u0000\u0000\u001c\u00b1\u0001\u0000\u0000\u0000\u001e\u00ef"+
		"\u0001\u0000\u0000\u0000 \u00f1\u0001\u0000\u0000\u0000\"\u0101\u0001"+
		"\u0000\u0000\u0000$\u010c\u0001\u0000\u0000\u0000&\u010e\u0001\u0000\u0000"+
		"\u0000(\u0110\u0001\u0000\u0000\u0000*\u0119\u0001\u0000\u0000\u0000,"+
		"\u0122\u0001\u0000\u0000\u0000.\u012f\u0001\u0000\u0000\u00000\u0135\u0001"+
		"\u0000\u0000\u00002\u013d\u0001\u0000\u0000\u000048\u0003\f\u0006\u0000"+
		"58\u0003\u0002\u0001\u000068\u0003\u0006\u0003\u000074\u0001\u0000\u0000"+
		"\u000075\u0001\u0000\u0000\u000076\u0001\u0000\u0000\u00008;\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:<\u0001"+
		"\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000<=\u0005\u0000\u0000\u0001"+
		"=\u0001\u0001\u0000\u0000\u0000>?\u0005(\u0000\u0000?@\u00054\u0000\u0000"+
		"@F\u0005\u001d\u0000\u0000AE\u0003\f\u0006\u0000BE\u0003\u0006\u0003\u0000"+
		"CE\u0003\u0004\u0002\u0000DA\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000"+
		"\u0000DC\u0001\u0000\u0000\u0000EH\u0001\u0000\u0000\u0000FD\u0001\u0000"+
		"\u0000\u0000FG\u0001\u0000\u0000\u0000GI\u0001\u0000\u0000\u0000HF\u0001"+
		"\u0000\u0000\u0000IJ\u0005\u001e\u0000\u0000JK\u0005 \u0000\u0000K\u0003"+
		"\u0001\u0000\u0000\u0000LM\u00054\u0000\u0000MN\u0005\u0019\u0000\u0000"+
		"NO\u0005\u001a\u0000\u0000OP\u0003(\u0014\u0000P\u0005\u0001\u0000\u0000"+
		"\u0000QR\u0003\u0014\n\u0000RS\u00054\u0000\u0000SU\u0005\u0019\u0000"+
		"\u0000TV\u0003\b\u0004\u0000UT\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000"+
		"\u0000VW\u0001\u0000\u0000\u0000WX\u0005\u001a\u0000\u0000XY\u0003(\u0014"+
		"\u0000Y\u0007\u0001\u0000\u0000\u0000Z_\u0003\n\u0005\u0000[\\\u0005\u001f"+
		"\u0000\u0000\\^\u0003\n\u0005\u0000][\u0001\u0000\u0000\u0000^a\u0001"+
		"\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000"+
		"`\t\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000bc\u0003\u0014\n"+
		"\u0000cd\u0003\u000e\u0007\u0000d\u000b\u0001\u0000\u0000\u0000ef\u0003"+
		"\u0014\n\u0000fk\u0003\u000e\u0007\u0000gh\u0005\u001f\u0000\u0000hj\u0003"+
		"\u000e\u0007\u0000ig\u0001\u0000\u0000\u0000jm\u0001\u0000\u0000\u0000"+
		"ki\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000ln\u0001\u0000\u0000"+
		"\u0000mk\u0001\u0000\u0000\u0000no\u0005 \u0000\u0000o\r\u0001\u0000\u0000"+
		"\u0000ps\u00054\u0000\u0000qr\u0005\u0015\u0000\u0000rt\u0003\u001c\u000e"+
		"\u0000sq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000t\u000f\u0001"+
		"\u0000\u0000\u0000uv\u0003\u001c\u000e\u0000vw\u0005 \u0000\u0000w\u0011"+
		"\u0001\u0000\u0000\u0000x}\u0003\u001c\u000e\u0000yz\u0005\u001f\u0000"+
		"\u0000z|\u0003\u001c\u000e\u0000{y\u0001\u0000\u0000\u0000|\u007f\u0001"+
		"\u0000\u0000\u0000}{\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000"+
		"~\u0013\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u0080\u0085"+
		"\u0003\u0018\f\u0000\u0081\u0082\u0005\u001b\u0000\u0000\u0082\u0084\u0005"+
		"\u001c\u0000\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0084\u0087\u0001"+
		"\u0000\u0000\u0000\u0085\u0083\u0001\u0000\u0000\u0000\u0085\u0086\u0001"+
		"\u0000\u0000\u0000\u0086\u0015\u0001\u0000\u0000\u0000\u0087\u0085\u0001"+
		"\u0000\u0000\u0000\u0088\u008a\u0005\u001b\u0000\u0000\u0089\u008b\u0003"+
		"\u001c\u000e\u0000\u008a\u0089\u0001\u0000\u0000\u0000\u008a\u008b\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u008d\u0005"+
		"\u001c\u0000\u0000\u008d\u0017\u0001\u0000\u0000\u0000\u008e\u0091\u0005"+
		"4\u0000\u0000\u008f\u0091\u0003\u001a\r\u0000\u0090\u008e\u0001\u0000"+
		"\u0000\u0000\u0090\u008f\u0001\u0000\u0000\u0000\u0091\u0019\u0001\u0000"+
		"\u0000\u0000\u0092\u0093\u0007\u0000\u0000\u0000\u0093\u001b\u0001\u0000"+
		"\u0000\u0000\u0094\u0095\u0006\u000e\uffff\uffff\u0000\u0095\u0096\u0005"+
		"\u0019\u0000\u0000\u0096\u0097\u0003\u001c\u000e\u0000\u0097\u0098\u0005"+
		"\u001a\u0000\u0000\u0098\u00b2\u0001\u0000\u0000\u0000\u0099\u009a\u0005"+
		"\'\u0000\u0000\u009a\u009c\u0003\u0018\f\u0000\u009b\u009d\u0003\u0016"+
		"\u000b\u0000\u009c\u009b\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000"+
		"\u0000\u0000\u009e\u009c\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000"+
		"\u0000\u0000\u009f\u00a1\u0001\u0000\u0000\u0000\u00a0\u00a2\u0003 \u0010"+
		"\u0000\u00a1\u00a0\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000"+
		"\u0000\u00a2\u00b2\u0001\u0000\u0000\u0000\u00a3\u00a4\u0005\'\u0000\u0000"+
		"\u00a4\u00a7\u0003\u0018\f\u0000\u00a5\u00a6\u0005\u0019\u0000\u0000\u00a6"+
		"\u00a8\u0005\u001a\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7"+
		"\u00a8\u0001\u0000\u0000\u0000\u00a8\u00b2\u0001\u0000\u0000\u0000\u00a9"+
		"\u00aa\u0007\u0001\u0000\u0000\u00aa\u00b2\u0003\u001c\u000e\u0012\u00ab"+
		"\u00ac\u0007\u0002\u0000\u0000\u00ac\u00b2\u0003\u001c\u000e\u0011\u00ad"+
		"\u00b2\u0003 \u0010\u0000\u00ae\u00b2\u0003\u001e\u000f\u0000\u00af\u00b2"+
		"\u0003\"\u0011\u0000\u00b0\u00b2\u0007\u0003\u0000\u0000\u00b1\u0094\u0001"+
		"\u0000\u0000\u0000\u00b1\u0099\u0001\u0000\u0000\u0000\u00b1\u00a3\u0001"+
		"\u0000\u0000\u0000\u00b1\u00a9\u0001\u0000\u0000\u0000\u00b1\u00ab\u0001"+
		"\u0000\u0000\u0000\u00b1\u00ad\u0001\u0000\u0000\u0000\u00b1\u00ae\u0001"+
		"\u0000\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b1\u00b0\u0001"+
		"\u0000\u0000\u0000\u00b2\u00ec\u0001\u0000\u0000\u0000\u00b3\u00b4\n\u0010"+
		"\u0000\u0000\u00b4\u00b5\u0007\u0004\u0000\u0000\u00b5\u00eb\u0003\u001c"+
		"\u000e\u0011\u00b6\u00b7\n\u000f\u0000\u0000\u00b7\u00b8\u0007\u0005\u0000"+
		"\u0000\u00b8\u00eb\u0003\u001c\u000e\u0010\u00b9\u00ba\n\u000e\u0000\u0000"+
		"\u00ba\u00bb\u0007\u0006\u0000\u0000\u00bb\u00eb\u0003\u001c\u000e\u000f"+
		"\u00bc\u00bd\n\r\u0000\u0000\u00bd\u00be\u0007\u0007\u0000\u0000\u00be"+
		"\u00eb\u0003\u001c\u000e\u000e\u00bf\u00c0\n\f\u0000\u0000\u00c0\u00c1"+
		"\u0007\b\u0000\u0000\u00c1\u00eb\u0003\u001c\u000e\r\u00c2\u00c3\n\u000b"+
		"\u0000\u0000\u00c3\u00c4\u0005\u0011\u0000\u0000\u00c4\u00eb\u0003\u001c"+
		"\u000e\f\u00c5\u00c6\n\n\u0000\u0000\u00c6\u00c7\u0005\u0013\u0000\u0000"+
		"\u00c7\u00eb\u0003\u001c\u000e\u000b\u00c8\u00c9\n\t\u0000\u0000\u00c9"+
		"\u00ca\u0005\u0012\u0000\u0000\u00ca\u00eb\u0003\u001c\u000e\n\u00cb\u00cc"+
		"\n\b\u0000\u0000\u00cc\u00cd\u0005\f\u0000\u0000\u00cd\u00eb\u0003\u001c"+
		"\u000e\t\u00ce\u00cf\n\u0007\u0000\u0000\u00cf\u00d0\u0005\r\u0000\u0000"+
		"\u00d0\u00eb\u0003\u001c\u000e\b\u00d1\u00d2\n\u0006\u0000\u0000\u00d2"+
		"\u00d3\u0005!\u0000\u0000\u00d3\u00d4\u0003\u001c\u000e\u0000\u00d4\u00d5"+
		"\u0005\"\u0000\u0000\u00d5\u00d6\u0003\u001c\u000e\u0006\u00d6\u00eb\u0001"+
		"\u0000\u0000\u0000\u00d7\u00d8\n\u0005\u0000\u0000\u00d8\u00d9\u0005\u0015"+
		"\u0000\u0000\u00d9\u00eb\u0003\u001c\u000e\u0005\u00da\u00db\n\u0018\u0000"+
		"\u0000\u00db\u00dd\u0005\u0019\u0000\u0000\u00dc\u00de\u0003\u0012\t\u0000"+
		"\u00dd\u00dc\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000"+
		"\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00eb\u0005\u001a\u0000\u0000"+
		"\u00e0\u00e1\n\u0015\u0000\u0000\u00e1\u00e2\u0005\u001b\u0000\u0000\u00e2"+
		"\u00e3\u0003\u001c\u000e\u0000\u00e3\u00e4\u0005\u001c\u0000\u0000\u00e4"+
		"\u00eb\u0001\u0000\u0000\u0000\u00e5\u00e6\n\u0014\u0000\u0000\u00e6\u00e7"+
		"\u0005\u0018\u0000\u0000\u00e7\u00eb\u00054\u0000\u0000\u00e8\u00e9\n"+
		"\u0013\u0000\u0000\u00e9\u00eb\u0007\u0001\u0000\u0000\u00ea\u00b3\u0001"+
		"\u0000\u0000\u0000\u00ea\u00b6\u0001\u0000\u0000\u0000\u00ea\u00b9\u0001"+
		"\u0000\u0000\u0000\u00ea\u00bc\u0001\u0000\u0000\u0000\u00ea\u00bf\u0001"+
		"\u0000\u0000\u0000\u00ea\u00c2\u0001\u0000\u0000\u0000\u00ea\u00c5\u0001"+
		"\u0000\u0000\u0000\u00ea\u00c8\u0001\u0000\u0000\u0000\u00ea\u00cb\u0001"+
		"\u0000\u0000\u0000\u00ea\u00ce\u0001\u0000\u0000\u0000\u00ea\u00d1\u0001"+
		"\u0000\u0000\u0000\u00ea\u00d7\u0001\u0000\u0000\u0000\u00ea\u00da\u0001"+
		"\u0000\u0000\u0000\u00ea\u00e0\u0001\u0000\u0000\u0000\u00ea\u00e5\u0001"+
		"\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00eb\u00ee\u0001"+
		"\u0000\u0000\u0000\u00ec\u00ea\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001"+
		"\u0000\u0000\u0000\u00ed\u001d\u0001\u0000\u0000\u0000\u00ee\u00ec\u0001"+
		"\u0000\u0000\u0000\u00ef\u00f0\u0007\t\u0000\u0000\u00f0\u001f\u0001\u0000"+
		"\u0000\u0000\u00f1\u00f2\u0005\u001d\u0000\u0000\u00f2\u00f3\u0003\u0012"+
		"\t\u0000\u00f3\u00f4\u0005\u001e\u0000\u0000\u00f4!\u0001\u0000\u0000"+
		"\u0000\u00f5\u0102\u00057\u0000\u0000\u00f6\u00f7\u00058\u0000\u0000\u00f7"+
		"\u00fc\u0003\u001c\u000e\u0000\u00f8\u00f9\u00059\u0000\u0000\u00f9\u00fb"+
		"\u0003\u001c\u000e\u0000\u00fa\u00f8\u0001\u0000\u0000\u0000\u00fb\u00fe"+
		"\u0001\u0000\u0000\u0000\u00fc\u00fa\u0001\u0000\u0000\u0000\u00fc\u00fd"+
		"\u0001\u0000\u0000\u0000\u00fd\u00ff\u0001\u0000\u0000\u0000\u00fe\u00fc"+
		"\u0001\u0000\u0000\u0000\u00ff\u0100\u0005:\u0000\u0000\u0100\u0102\u0001"+
		"\u0000\u0000\u0000\u0101\u00f5\u0001\u0000\u0000\u0000\u0101\u00f6\u0001"+
		"\u0000\u0000\u0000\u0102#\u0001\u0000\u0000\u0000\u0103\u010d\u0003(\u0014"+
		"\u0000\u0104\u010d\u0003,\u0016\u0000\u0105\u010d\u0003*\u0015\u0000\u0106"+
		"\u010d\u0003,\u0016\u0000\u0107\u010d\u0003.\u0017\u0000\u0108\u010d\u0003"+
		"2\u0019\u0000\u0109\u010d\u0003\u0010\b\u0000\u010a\u010d\u0003&\u0013"+
		"\u0000\u010b\u010d\u00030\u0018\u0000\u010c\u0103\u0001\u0000\u0000\u0000"+
		"\u010c\u0104\u0001\u0000\u0000\u0000\u010c\u0105\u0001\u0000\u0000\u0000"+
		"\u010c\u0106\u0001\u0000\u0000\u0000\u010c\u0107\u0001\u0000\u0000\u0000"+
		"\u010c\u0108\u0001\u0000\u0000\u0000\u010c\u0109\u0001\u0000\u0000\u0000"+
		"\u010c\u010a\u0001\u0000\u0000\u0000\u010c\u010b\u0001\u0000\u0000\u0000"+
		"\u010d%\u0001\u0000\u0000\u0000\u010e\u010f\u0003\f\u0006\u0000\u010f"+
		"\'\u0001\u0000\u0000\u0000\u0110\u0114\u0005\u001d\u0000\u0000\u0111\u0113"+
		"\u0003$\u0012\u0000\u0112\u0111\u0001\u0000\u0000\u0000\u0113\u0116\u0001"+
		"\u0000\u0000\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0114\u0115\u0001"+
		"\u0000\u0000\u0000\u0115\u0117\u0001\u0000\u0000\u0000\u0116\u0114\u0001"+
		"\u0000\u0000\u0000\u0117\u0118\u0005\u001e\u0000\u0000\u0118)\u0001\u0000"+
		"\u0000\u0000\u0119\u011a\u0005-\u0000\u0000\u011a\u011b\u0005\u0019\u0000"+
		"\u0000\u011b\u011c\u0003\u001c\u000e\u0000\u011c\u011d\u0005\u001a\u0000"+
		"\u0000\u011d\u0120\u0003$\u0012\u0000\u011e\u011f\u0005.\u0000\u0000\u011f"+
		"\u0121\u0003$\u0012\u0000\u0120\u011e\u0001\u0000\u0000\u0000\u0120\u0121"+
		"\u0001\u0000\u0000\u0000\u0121+\u0001\u0000\u0000\u0000\u0122\u0123\u0005"+
		"/\u0000\u0000\u0123\u0124\u0005\u0019\u0000\u0000\u0124\u0126\u0003$\u0012"+
		"\u0000\u0125\u0127\u0003\u001c\u000e\u0000\u0126\u0125\u0001\u0000\u0000"+
		"\u0000\u0126\u0127\u0001\u0000\u0000\u0000\u0127\u0128\u0001\u0000\u0000"+
		"\u0000\u0128\u012a\u0005 \u0000\u0000\u0129\u012b\u0003\u001c\u000e\u0000"+
		"\u012a\u0129\u0001\u0000\u0000\u0000\u012a\u012b\u0001\u0000\u0000\u0000"+
		"\u012b\u012c\u0001\u0000\u0000\u0000\u012c\u012d\u0005\u001a\u0000\u0000"+
		"\u012d\u012e\u0003$\u0012\u0000\u012e-\u0001\u0000\u0000\u0000\u012f\u0130"+
		"\u00050\u0000\u0000\u0130\u0131\u0005\u0019\u0000\u0000\u0131\u0132\u0003"+
		"\u001c\u000e\u0000\u0132\u0133\u0005\u001a\u0000\u0000\u0133\u0134\u0003"+
		"$\u0012\u0000\u0134/\u0001\u0000\u0000\u0000\u0135\u0136\u0005 \u0000"+
		"\u0000\u01361\u0001\u0000\u0000\u0000\u0137\u013e\u00052\u0000\u0000\u0138"+
		"\u013e\u00051\u0000\u0000\u0139\u013b\u00053\u0000\u0000\u013a\u013c\u0003"+
		"\u001c\u000e\u0000\u013b\u013a\u0001\u0000\u0000\u0000\u013b\u013c\u0001"+
		"\u0000\u0000\u0000\u013c\u013e\u0001\u0000\u0000\u0000\u013d\u0137\u0001"+
		"\u0000\u0000\u0000\u013d\u0138\u0001\u0000\u0000\u0000\u013d\u0139\u0001"+
		"\u0000\u0000\u0000\u013e\u013f\u0001\u0000\u0000\u0000\u013f\u0140\u0005"+
		" \u0000\u0000\u01403\u0001\u0000\u0000\u0000\u001c79DFU_ks}\u0085\u008a"+
		"\u0090\u009e\u00a1\u00a7\u00b1\u00dd\u00ea\u00ec\u00fc\u0101\u010c\u0114"+
		"\u0120\u0126\u012a\u013b\u013d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}