
// Generated from Calc.g4 by ANTLR 4.13.1

#pragma once


#include "antlr4-runtime.h"
#include "CalcVisitor.h"


/**
 * This class provides an empty implementation of CalcVisitor, which can be
 * extended to create a visitor which only needs to handle a subset of the available methods.
 */
class  CalcBaseVisitor : public CalcVisitor {
public:

  virtual std::any visitProg(CalcParser::ProgContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitStat(CalcParser::StatContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExpr(CalcParser::ExprContext *ctx) override {
    return visitChildren(ctx);
  }


};

