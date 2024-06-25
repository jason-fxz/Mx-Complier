#include <iostream>
#include <fstream>
#include <string>
#include <antlr4-runtime.h>
#include "CalcLexer.h"
#include "CalcParser.h"
#include "CalcBaseVisitor.h"

using namespace antlr4;

class myCalcVisitor : public CalcBaseVisitor {
private:
    std::map<std::string, int> IdMap; // ID -> Value
public:

    antlrcpp::Any visitProg(CalcParser::ProgContext *ctx) override {
        for (auto expr : ctx->stat()) {
            visit(expr);
        }
        return 0;
    }

    antlrcpp::Any visitExpr(CalcParser::ExprContext *ctx) override {
        // std::cerr << "expr: " << ctx->getText() << " :size = " << ctx->children.size() << std::endl;
        if (ctx->children.size() == 3) {
            int left = std::any_cast<int>(visit(ctx->expr(0)));
            int right = std::any_cast<int>(visit(ctx->expr(1)));
            // std::cerr << "left: " << left << " right: " << right << std::endl;
            if (ctx->ADD()) {
                return left + right;
            } else if (ctx->SUB()) {
                return left - right;
            } else if (ctx->MUL()) {
                return left * right;
            } else if (ctx->DIV()) {
                return left / right;
            }
        } else if (ctx->INT()) {
            // std::cerr << " INT: " << ctx->INT()->getText() << std::endl;
            return std::stoi(ctx->INT()->getText());
        } else if (ctx->ID()) {
            return IdMap[ctx->ID()->getText()];
        }
        return 0; // 默认返回值
    }

    antlrcpp::Any visitStat(CalcParser::StatContext *ctx) override {
        if (ctx->ID()) {
            std::string id = ctx->ID()->getText();
            int value = std::any_cast<int>(visit(ctx->expr()));
            IdMap[id] = value;
            std::cout << id << " = " << value;
        } else {
            std::cout << std::any_cast<int>(visit(ctx->expr()));
        }
        std::cout << " : " << ctx->getText();
        return nullptr;
    }
};

int main(int argc, const char* argv[]) {
    std::ifstream stream;
    stream.open("input.txt");

    ANTLRInputStream input(stream);
    CalcLexer lexer(&input);
    CommonTokenStream tokens(&lexer);
    CalcParser parser(&tokens);

    tree::ParseTree* tree = parser.prog();

    myCalcVisitor visitor;
    visitor.visit(tree);

    return 0;
}
