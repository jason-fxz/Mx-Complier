
// Generated from Calc.g4 by ANTLR 4.13.1

#pragma once


#include "antlr4-runtime.h"
#include "CalcParser.h"



/**
 * This class defines an abstract visitor for a parse tree
 * produced by CalcParser.
 */
class  CalcVisitor : public antlr4::tree::AbstractParseTreeVisitor {
public:

  /**
   * Visit parse trees produced by CalcParser.
   */
    virtual std::any visitProg(CalcParser::ProgContext *context) = 0;

    virtual std::any visitStat(CalcParser::StatContext *context) = 0;

    virtual std::any visitExpr(CalcParser::ExprContext *context) = 0;


};

