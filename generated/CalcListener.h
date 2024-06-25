
// Generated from Calc.g4 by ANTLR 4.13.1

#pragma once


#include "antlr4-runtime.h"
#include "CalcParser.h"


/**
 * This interface defines an abstract listener for a parse tree produced by CalcParser.
 */
class  CalcListener : public antlr4::tree::ParseTreeListener {
public:

  virtual void enterProg(CalcParser::ProgContext *ctx) = 0;
  virtual void exitProg(CalcParser::ProgContext *ctx) = 0;

  virtual void enterStat(CalcParser::StatContext *ctx) = 0;
  virtual void exitStat(CalcParser::StatContext *ctx) = 0;

  virtual void enterExpr(CalcParser::ExprContext *ctx) = 0;
  virtual void exitExpr(CalcParser::ExprContext *ctx) = 0;


};

