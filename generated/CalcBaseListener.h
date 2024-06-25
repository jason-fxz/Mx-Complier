
// Generated from Calc.g4 by ANTLR 4.13.1

#pragma once


#include "antlr4-runtime.h"
#include "CalcListener.h"


/**
 * This class provides an empty implementation of CalcListener,
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
class  CalcBaseListener : public CalcListener {
public:

  virtual void enterProg(CalcParser::ProgContext * /*ctx*/) override { }
  virtual void exitProg(CalcParser::ProgContext * /*ctx*/) override { }

  virtual void enterStat(CalcParser::StatContext * /*ctx*/) override { }
  virtual void exitStat(CalcParser::StatContext * /*ctx*/) override { }

  virtual void enterExpr(CalcParser::ExprContext * /*ctx*/) override { }
  virtual void exitExpr(CalcParser::ExprContext * /*ctx*/) override { }


  virtual void enterEveryRule(antlr4::ParserRuleContext * /*ctx*/) override { }
  virtual void exitEveryRule(antlr4::ParserRuleContext * /*ctx*/) override { }
  virtual void visitTerminal(antlr4::tree::TerminalNode * /*node*/) override { }
  virtual void visitErrorNode(antlr4::tree::ErrorNode * /*node*/) override { }

};

