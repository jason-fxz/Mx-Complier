
// Generated from Calc.g4 by ANTLR 4.13.1


#include "CalcLexer.h"


using namespace antlr4;



using namespace antlr4;

namespace {

struct CalcLexerStaticData final {
  CalcLexerStaticData(std::vector<std::string> ruleNames,
                          std::vector<std::string> channelNames,
                          std::vector<std::string> modeNames,
                          std::vector<std::string> literalNames,
                          std::vector<std::string> symbolicNames)
      : ruleNames(std::move(ruleNames)), channelNames(std::move(channelNames)),
        modeNames(std::move(modeNames)), literalNames(std::move(literalNames)),
        symbolicNames(std::move(symbolicNames)),
        vocabulary(this->literalNames, this->symbolicNames) {}

  CalcLexerStaticData(const CalcLexerStaticData&) = delete;
  CalcLexerStaticData(CalcLexerStaticData&&) = delete;
  CalcLexerStaticData& operator=(const CalcLexerStaticData&) = delete;
  CalcLexerStaticData& operator=(CalcLexerStaticData&&) = delete;

  std::vector<antlr4::dfa::DFA> decisionToDFA;
  antlr4::atn::PredictionContextCache sharedContextCache;
  const std::vector<std::string> ruleNames;
  const std::vector<std::string> channelNames;
  const std::vector<std::string> modeNames;
  const std::vector<std::string> literalNames;
  const std::vector<std::string> symbolicNames;
  const antlr4::dfa::Vocabulary vocabulary;
  antlr4::atn::SerializedATNView serializedATN;
  std::unique_ptr<antlr4::atn::ATN> atn;
};

::antlr4::internal::OnceFlag calclexerLexerOnceFlag;
#if ANTLR4_USE_THREAD_LOCAL_CACHE
static thread_local
#endif
CalcLexerStaticData *calclexerLexerStaticData = nullptr;

void calclexerLexerInitialize() {
#if ANTLR4_USE_THREAD_LOCAL_CACHE
  if (calclexerLexerStaticData != nullptr) {
    return;
  }
#else
  assert(calclexerLexerStaticData == nullptr);
#endif
  auto staticData = std::make_unique<CalcLexerStaticData>(
    std::vector<std::string>{
      "T__0", "T__1", "ID", "INT", "NEWLINE", "WS", "ADD", "SUB", "MUL", 
      "DIV", "ASSIGN"
    },
    std::vector<std::string>{
      "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    },
    std::vector<std::string>{
      "DEFAULT_MODE"
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
  	4,0,11,59,6,-1,2,0,7,0,2,1,7,1,2,2,7,2,2,3,7,3,2,4,7,4,2,5,7,5,2,6,7,
  	6,2,7,7,7,2,8,7,8,2,9,7,9,2,10,7,10,1,0,1,0,1,1,1,1,1,2,4,2,29,8,2,11,
  	2,12,2,30,1,3,4,3,34,8,3,11,3,12,3,35,1,4,4,4,39,8,4,11,4,12,4,40,1,5,
  	4,5,44,8,5,11,5,12,5,45,1,5,1,5,1,6,1,6,1,7,1,7,1,8,1,8,1,9,1,9,1,10,
  	1,10,0,0,11,1,1,3,2,5,3,7,4,9,5,11,6,13,7,15,8,17,9,19,10,21,11,1,0,4,
  	2,0,65,90,97,122,1,0,48,57,2,0,10,10,13,13,2,0,9,9,32,32,62,0,1,1,0,0,
  	0,0,3,1,0,0,0,0,5,1,0,0,0,0,7,1,0,0,0,0,9,1,0,0,0,0,11,1,0,0,0,0,13,1,
  	0,0,0,0,15,1,0,0,0,0,17,1,0,0,0,0,19,1,0,0,0,0,21,1,0,0,0,1,23,1,0,0,
  	0,3,25,1,0,0,0,5,28,1,0,0,0,7,33,1,0,0,0,9,38,1,0,0,0,11,43,1,0,0,0,13,
  	49,1,0,0,0,15,51,1,0,0,0,17,53,1,0,0,0,19,55,1,0,0,0,21,57,1,0,0,0,23,
  	24,5,40,0,0,24,2,1,0,0,0,25,26,5,41,0,0,26,4,1,0,0,0,27,29,7,0,0,0,28,
  	27,1,0,0,0,29,30,1,0,0,0,30,28,1,0,0,0,30,31,1,0,0,0,31,6,1,0,0,0,32,
  	34,7,1,0,0,33,32,1,0,0,0,34,35,1,0,0,0,35,33,1,0,0,0,35,36,1,0,0,0,36,
  	8,1,0,0,0,37,39,7,2,0,0,38,37,1,0,0,0,39,40,1,0,0,0,40,38,1,0,0,0,40,
  	41,1,0,0,0,41,10,1,0,0,0,42,44,7,3,0,0,43,42,1,0,0,0,44,45,1,0,0,0,45,
  	43,1,0,0,0,45,46,1,0,0,0,46,47,1,0,0,0,47,48,6,5,0,0,48,12,1,0,0,0,49,
  	50,5,43,0,0,50,14,1,0,0,0,51,52,5,45,0,0,52,16,1,0,0,0,53,54,5,42,0,0,
  	54,18,1,0,0,0,55,56,5,47,0,0,56,20,1,0,0,0,57,58,5,61,0,0,58,22,1,0,0,
  	0,5,0,30,35,40,45,1,6,0,0
  };
  staticData->serializedATN = antlr4::atn::SerializedATNView(serializedATNSegment, sizeof(serializedATNSegment) / sizeof(serializedATNSegment[0]));

  antlr4::atn::ATNDeserializer deserializer;
  staticData->atn = deserializer.deserialize(staticData->serializedATN);

  const size_t count = staticData->atn->getNumberOfDecisions();
  staticData->decisionToDFA.reserve(count);
  for (size_t i = 0; i < count; i++) { 
    staticData->decisionToDFA.emplace_back(staticData->atn->getDecisionState(i), i);
  }
  calclexerLexerStaticData = staticData.release();
}

}

CalcLexer::CalcLexer(CharStream *input) : Lexer(input) {
  CalcLexer::initialize();
  _interpreter = new atn::LexerATNSimulator(this, *calclexerLexerStaticData->atn, calclexerLexerStaticData->decisionToDFA, calclexerLexerStaticData->sharedContextCache);
}

CalcLexer::~CalcLexer() {
  delete _interpreter;
}

std::string CalcLexer::getGrammarFileName() const {
  return "Calc.g4";
}

const std::vector<std::string>& CalcLexer::getRuleNames() const {
  return calclexerLexerStaticData->ruleNames;
}

const std::vector<std::string>& CalcLexer::getChannelNames() const {
  return calclexerLexerStaticData->channelNames;
}

const std::vector<std::string>& CalcLexer::getModeNames() const {
  return calclexerLexerStaticData->modeNames;
}

const dfa::Vocabulary& CalcLexer::getVocabulary() const {
  return calclexerLexerStaticData->vocabulary;
}

antlr4::atn::SerializedATNView CalcLexer::getSerializedATN() const {
  return calclexerLexerStaticData->serializedATN;
}

const atn::ATN& CalcLexer::getATN() const {
  return *calclexerLexerStaticData->atn;
}




void CalcLexer::initialize() {
#if ANTLR4_USE_THREAD_LOCAL_CACHE
  calclexerLexerInitialize();
#else
  ::antlr4::internal::call_once(calclexerLexerOnceFlag, calclexerLexerInitialize);
#endif
}
