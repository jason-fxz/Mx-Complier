
// Generated from Calc.g4 by ANTLR 4.13.1


#include "CalcListener.h"
#include "CalcVisitor.h"

#include "CalcParser.h"


using namespace antlrcpp;

using namespace antlr4;

namespace {

struct CalcParserStaticData final {
  CalcParserStaticData(std::vector<std::string> ruleNames,
                        std::vector<std::string> literalNames,
                        std::vector<std::string> symbolicNames)
      : ruleNames(std::move(ruleNames)), literalNames(std::move(literalNames)),
        symbolicNames(std::move(symbolicNames)),
        vocabulary(this->literalNames, this->symbolicNames) {}

  CalcParserStaticData(const CalcParserStaticData&) = delete;
  CalcParserStaticData(CalcParserStaticData&&) = delete;
  CalcParserStaticData& operator=(const CalcParserStaticData&) = delete;
  CalcParserStaticData& operator=(CalcParserStaticData&&) = delete;

  std::vector<antlr4::dfa::DFA> decisionToDFA;
  antlr4::atn::PredictionContextCache sharedContextCache;
  const std::vector<std::string> ruleNames;
  const std::vector<std::string> literalNames;
  const std::vector<std::string> symbolicNames;
  const antlr4::dfa::Vocabulary vocabulary;
  antlr4::atn::SerializedATNView serializedATN;
  std::unique_ptr<antlr4::atn::ATN> atn;
};

::antlr4::internal::OnceFlag calcParserOnceFlag;
#if ANTLR4_USE_THREAD_LOCAL_CACHE
static thread_local
#endif
CalcParserStaticData *calcParserStaticData = nullptr;

void calcParserInitialize() {
#if ANTLR4_USE_THREAD_LOCAL_CACHE
  if (calcParserStaticData != nullptr) {
    return;
  }
#else
  assert(calcParserStaticData == nullptr);
#endif
  auto staticData = std::make_unique<CalcParserStaticData>(
    std::vector<std::string>{
      "prog", "stat", "expr"
    },
    std::vector<std::string>{
      "", "'('", "')'", "", "", "", "", "'+'", "'-'", "'*'", "'/'", "'='"
    },
    std::vector<std::string>{
      "", "", "", "ID", "INT", "NEWLINE", "WS", "ADD", "SUB", "MUL", "DIV", 
      "ASSIGN"
    }
  );
  static const int32_t serializedATNSegment[] = {
  	4,1,11,43,2,0,7,0,2,1,7,1,2,2,7,2,1,0,4,0,8,8,0,11,0,12,0,9,1,1,1,1,1,
  	1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,21,8,1,1,2,1,2,1,2,1,2,1,2,1,2,1,2,3,2,
  	30,8,2,1,2,1,2,1,2,1,2,1,2,1,2,5,2,38,8,2,10,2,12,2,41,9,2,1,2,0,1,4,
  	3,0,2,4,0,2,1,0,9,10,1,0,7,8,46,0,7,1,0,0,0,2,20,1,0,0,0,4,29,1,0,0,0,
  	6,8,3,2,1,0,7,6,1,0,0,0,8,9,1,0,0,0,9,7,1,0,0,0,9,10,1,0,0,0,10,1,1,0,
  	0,0,11,12,3,4,2,0,12,13,5,5,0,0,13,21,1,0,0,0,14,15,5,3,0,0,15,16,5,11,
  	0,0,16,17,3,4,2,0,17,18,5,5,0,0,18,21,1,0,0,0,19,21,5,5,0,0,20,11,1,0,
  	0,0,20,14,1,0,0,0,20,19,1,0,0,0,21,3,1,0,0,0,22,23,6,2,-1,0,23,30,5,4,
  	0,0,24,30,5,3,0,0,25,26,5,1,0,0,26,27,3,4,2,0,27,28,5,2,0,0,28,30,1,0,
  	0,0,29,22,1,0,0,0,29,24,1,0,0,0,29,25,1,0,0,0,30,39,1,0,0,0,31,32,10,
  	5,0,0,32,33,7,0,0,0,33,38,3,4,2,6,34,35,10,4,0,0,35,36,7,1,0,0,36,38,
  	3,4,2,5,37,31,1,0,0,0,37,34,1,0,0,0,38,41,1,0,0,0,39,37,1,0,0,0,39,40,
  	1,0,0,0,40,5,1,0,0,0,41,39,1,0,0,0,5,9,20,29,37,39
  };
  staticData->serializedATN = antlr4::atn::SerializedATNView(serializedATNSegment, sizeof(serializedATNSegment) / sizeof(serializedATNSegment[0]));

  antlr4::atn::ATNDeserializer deserializer;
  staticData->atn = deserializer.deserialize(staticData->serializedATN);

  const size_t count = staticData->atn->getNumberOfDecisions();
  staticData->decisionToDFA.reserve(count);
  for (size_t i = 0; i < count; i++) { 
    staticData->decisionToDFA.emplace_back(staticData->atn->getDecisionState(i), i);
  }
  calcParserStaticData = staticData.release();
}

}

CalcParser::CalcParser(TokenStream *input) : CalcParser(input, antlr4::atn::ParserATNSimulatorOptions()) {}

CalcParser::CalcParser(TokenStream *input, const antlr4::atn::ParserATNSimulatorOptions &options) : Parser(input) {
  CalcParser::initialize();
  _interpreter = new atn::ParserATNSimulator(this, *calcParserStaticData->atn, calcParserStaticData->decisionToDFA, calcParserStaticData->sharedContextCache, options);
}

CalcParser::~CalcParser() {
  delete _interpreter;
}

const atn::ATN& CalcParser::getATN() const {
  return *calcParserStaticData->atn;
}

std::string CalcParser::getGrammarFileName() const {
  return "Calc.g4";
}

const std::vector<std::string>& CalcParser::getRuleNames() const {
  return calcParserStaticData->ruleNames;
}

const dfa::Vocabulary& CalcParser::getVocabulary() const {
  return calcParserStaticData->vocabulary;
}

antlr4::atn::SerializedATNView CalcParser::getSerializedATN() const {
  return calcParserStaticData->serializedATN;
}


//----------------- ProgContext ------------------------------------------------------------------

CalcParser::ProgContext::ProgContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

std::vector<CalcParser::StatContext *> CalcParser::ProgContext::stat() {
  return getRuleContexts<CalcParser::StatContext>();
}

CalcParser::StatContext* CalcParser::ProgContext::stat(size_t i) {
  return getRuleContext<CalcParser::StatContext>(i);
}


size_t CalcParser::ProgContext::getRuleIndex() const {
  return CalcParser::RuleProg;
}

void CalcParser::ProgContext::enterRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<CalcListener *>(listener);
  if (parserListener != nullptr)
    parserListener->enterProg(this);
}

void CalcParser::ProgContext::exitRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<CalcListener *>(listener);
  if (parserListener != nullptr)
    parserListener->exitProg(this);
}


std::any CalcParser::ProgContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<CalcVisitor*>(visitor))
    return parserVisitor->visitProg(this);
  else
    return visitor->visitChildren(this);
}

CalcParser::ProgContext* CalcParser::prog() {
  ProgContext *_localctx = _tracker.createInstance<ProgContext>(_ctx, getState());
  enterRule(_localctx, 0, CalcParser::RuleProg);
  size_t _la = 0;

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(7); 
    _errHandler->sync(this);
    _la = _input->LA(1);
    do {
      setState(6);
      stat();
      setState(9); 
      _errHandler->sync(this);
      _la = _input->LA(1);
    } while ((((_la & ~ 0x3fULL) == 0) &&
      ((1ULL << _la) & 58) != 0));
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- StatContext ------------------------------------------------------------------

CalcParser::StatContext::StatContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

CalcParser::ExprContext* CalcParser::StatContext::expr() {
  return getRuleContext<CalcParser::ExprContext>(0);
}

tree::TerminalNode* CalcParser::StatContext::NEWLINE() {
  return getToken(CalcParser::NEWLINE, 0);
}

tree::TerminalNode* CalcParser::StatContext::ID() {
  return getToken(CalcParser::ID, 0);
}

tree::TerminalNode* CalcParser::StatContext::ASSIGN() {
  return getToken(CalcParser::ASSIGN, 0);
}


size_t CalcParser::StatContext::getRuleIndex() const {
  return CalcParser::RuleStat;
}

void CalcParser::StatContext::enterRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<CalcListener *>(listener);
  if (parserListener != nullptr)
    parserListener->enterStat(this);
}

void CalcParser::StatContext::exitRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<CalcListener *>(listener);
  if (parserListener != nullptr)
    parserListener->exitStat(this);
}


std::any CalcParser::StatContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<CalcVisitor*>(visitor))
    return parserVisitor->visitStat(this);
  else
    return visitor->visitChildren(this);
}

CalcParser::StatContext* CalcParser::stat() {
  StatContext *_localctx = _tracker.createInstance<StatContext>(_ctx, getState());
  enterRule(_localctx, 2, CalcParser::RuleStat);

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    setState(20);
    _errHandler->sync(this);
    switch (getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 1, _ctx)) {
    case 1: {
      enterOuterAlt(_localctx, 1);
      setState(11);
      expr(0);
      setState(12);
      match(CalcParser::NEWLINE);
      break;
    }

    case 2: {
      enterOuterAlt(_localctx, 2);
      setState(14);
      match(CalcParser::ID);
      setState(15);
      match(CalcParser::ASSIGN);
      setState(16);
      expr(0);
      setState(17);
      match(CalcParser::NEWLINE);
      break;
    }

    case 3: {
      enterOuterAlt(_localctx, 3);
      setState(19);
      match(CalcParser::NEWLINE);
      break;
    }

    default:
      break;
    }
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- ExprContext ------------------------------------------------------------------

CalcParser::ExprContext::ExprContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* CalcParser::ExprContext::INT() {
  return getToken(CalcParser::INT, 0);
}

tree::TerminalNode* CalcParser::ExprContext::ID() {
  return getToken(CalcParser::ID, 0);
}

std::vector<CalcParser::ExprContext *> CalcParser::ExprContext::expr() {
  return getRuleContexts<CalcParser::ExprContext>();
}

CalcParser::ExprContext* CalcParser::ExprContext::expr(size_t i) {
  return getRuleContext<CalcParser::ExprContext>(i);
}

tree::TerminalNode* CalcParser::ExprContext::MUL() {
  return getToken(CalcParser::MUL, 0);
}

tree::TerminalNode* CalcParser::ExprContext::DIV() {
  return getToken(CalcParser::DIV, 0);
}

tree::TerminalNode* CalcParser::ExprContext::ADD() {
  return getToken(CalcParser::ADD, 0);
}

tree::TerminalNode* CalcParser::ExprContext::SUB() {
  return getToken(CalcParser::SUB, 0);
}


size_t CalcParser::ExprContext::getRuleIndex() const {
  return CalcParser::RuleExpr;
}

void CalcParser::ExprContext::enterRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<CalcListener *>(listener);
  if (parserListener != nullptr)
    parserListener->enterExpr(this);
}

void CalcParser::ExprContext::exitRule(tree::ParseTreeListener *listener) {
  auto parserListener = dynamic_cast<CalcListener *>(listener);
  if (parserListener != nullptr)
    parserListener->exitExpr(this);
}


std::any CalcParser::ExprContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<CalcVisitor*>(visitor))
    return parserVisitor->visitExpr(this);
  else
    return visitor->visitChildren(this);
}


CalcParser::ExprContext* CalcParser::expr() {
   return expr(0);
}

CalcParser::ExprContext* CalcParser::expr(int precedence) {
  ParserRuleContext *parentContext = _ctx;
  size_t parentState = getState();
  CalcParser::ExprContext *_localctx = _tracker.createInstance<ExprContext>(_ctx, parentState);
  CalcParser::ExprContext *previousContext = _localctx;
  (void)previousContext; // Silence compiler, in case the context is not used by generated code.
  size_t startState = 4;
  enterRecursionRule(_localctx, 4, CalcParser::RuleExpr, precedence);

    size_t _la = 0;

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    unrollRecursionContexts(parentContext);
  });
  try {
    size_t alt;
    enterOuterAlt(_localctx, 1);
    setState(29);
    _errHandler->sync(this);
    switch (_input->LA(1)) {
      case CalcParser::INT: {
        setState(23);
        match(CalcParser::INT);
        break;
      }

      case CalcParser::ID: {
        setState(24);
        match(CalcParser::ID);
        break;
      }

      case CalcParser::T__0: {
        setState(25);
        match(CalcParser::T__0);
        setState(26);
        expr(0);
        setState(27);
        match(CalcParser::T__1);
        break;
      }

    default:
      throw NoViableAltException(this);
    }
    _ctx->stop = _input->LT(-1);
    setState(39);
    _errHandler->sync(this);
    alt = getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 4, _ctx);
    while (alt != 2 && alt != atn::ATN::INVALID_ALT_NUMBER) {
      if (alt == 1) {
        if (!_parseListeners.empty())
          triggerExitRuleEvent();
        previousContext = _localctx;
        setState(37);
        _errHandler->sync(this);
        switch (getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 3, _ctx)) {
        case 1: {
          _localctx = _tracker.createInstance<ExprContext>(parentContext, parentState);
          pushNewRecursionContext(_localctx, startState, RuleExpr);
          setState(31);

          if (!(precpred(_ctx, 5))) throw FailedPredicateException(this, "precpred(_ctx, 5)");
          setState(32);
          _la = _input->LA(1);
          if (!(_la == CalcParser::MUL

          || _la == CalcParser::DIV)) {
          _errHandler->recoverInline(this);
          }
          else {
            _errHandler->reportMatch(this);
            consume();
          }
          setState(33);
          expr(6);
          break;
        }

        case 2: {
          _localctx = _tracker.createInstance<ExprContext>(parentContext, parentState);
          pushNewRecursionContext(_localctx, startState, RuleExpr);
          setState(34);

          if (!(precpred(_ctx, 4))) throw FailedPredicateException(this, "precpred(_ctx, 4)");
          setState(35);
          _la = _input->LA(1);
          if (!(_la == CalcParser::ADD

          || _la == CalcParser::SUB)) {
          _errHandler->recoverInline(this);
          }
          else {
            _errHandler->reportMatch(this);
            consume();
          }
          setState(36);
          expr(5);
          break;
        }

        default:
          break;
        } 
      }
      setState(41);
      _errHandler->sync(this);
      alt = getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 4, _ctx);
    }
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }
  return _localctx;
}

bool CalcParser::sempred(RuleContext *context, size_t ruleIndex, size_t predicateIndex) {
  switch (ruleIndex) {
    case 2: return exprSempred(antlrcpp::downCast<ExprContext *>(context), predicateIndex);

  default:
    break;
  }
  return true;
}

bool CalcParser::exprSempred(ExprContext *_localctx, size_t predicateIndex) {
  switch (predicateIndex) {
    case 0: return precpred(_ctx, 5);
    case 1: return precpred(_ctx, 4);

  default:
    break;
  }
  return true;
}

void CalcParser::initialize() {
#if ANTLR4_USE_THREAD_LOCAL_CACHE
  calcParserInitialize();
#else
  ::antlr4::internal::call_once(calcParserOnceFlag, calcParserInitialize);
#endif
}
