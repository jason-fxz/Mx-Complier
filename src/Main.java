import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import AST.Node.RootNode;
import Allocator.SSALiveness;
import Allocator.SSAalloctor;
import Backend.ASMBuilder;
import Frontend.ASTBuilder;
import Frontend.IRBuilder;
import Frontend.SemanticChecker;
import Frontend.SemanticCollector;
import Grammar.MxLexer;
import Grammar.MxParser;
import Grammar.MxParser.ProgramContext;
import IR.node.IRRoot;
import Util.error.error;
import Util.scope.globalScope;
import Util.ArgumentParser;
import Util.ExecutionTimer;
import Util.MxErrorListener;
import Util.position;


public class Main {
    
    public static void main(String[] args) throws Exception {
        ArgumentParser ArgP = new ArgumentParser(args);
        
        String filename;
        InputStream input;
        PrintStream output;

        if (ArgP.hasArgument("-f")) {
            filename = ArgP.getArgument("-f");
            input = new FileInputStream(filename);
            System.err.println("file: " +  filename);
            position.filename = filename;
        } else {
            input = System.in;
        }

        if (ArgP.hasArgument("-o")) {
            String outputfile = ArgP.getArgument("-o");
            System.err.println("output: " + outputfile);
            output = new PrintStream(outputfile);
        } else {
            output = System.out;
        }
        
        ExecutionTimer timer = ExecutionTimer.timer;
        
        Compiling: try {
            
            RootNode ASTRoot;
            globalScope gScope = new globalScope();
            
            // Antlr: Lexer & Parser
            timer.start("Lexer & Parser");
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ProgramContext parseTreeRoot = parser.program();
            timer.stop("Lexer & Parser");
            
            // Build AST
            timer.start("ASTBuilder");
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot = (RootNode) astBuilder.visit(parseTreeRoot);
            timer.stop("ASTBuilder");
            
            // print AST
            if (ArgP.hasArgument("-debug-ast")) {
                System.err.println(ASTRoot.toString());
            }
            
            timer.start("Semantic");
            // Collector
            new SemanticCollector(gScope).visit(ASTRoot);
            // Checker
            new SemanticChecker(gScope).visit(ASTRoot);
            timer.stop("Semantic");

            if (ArgP.hasArgument("-fsyntax-only")) {
                break Compiling;
            }

            // IRBuilder
            timer.start("IRBuilder");
            IRBuilder irBuilder = new IRBuilder();
            irBuilder.visit(ASTRoot);
            IRRoot irRoot = irBuilder.getRoot();
            timer.stop("IRBuilder");

            // Optimize

            new Optimize.GlobalLocalization(irRoot).run();
            // output.println(irRoot.toString());


            new Optimize.Mem2Reg(irRoot).run();
            

            new Optimize.DCE(irRoot).run();     // Dead Code Elimination
            System.err.println(irRoot.toString());
            new Optimize.Inline(irRoot).run();  // Inline
            System.err.println(irRoot.toString());

            new Optimize.SCCP(irRoot).run();    // Sparse Conditional Constant Propagation
            new Optimize.DCE(irRoot).run();
            new Optimize.ArithmeticSimplification(irRoot).run();
            new Optimize.DCE(irRoot).run();
            
            new Optimize.LoopAnalysis(irRoot).run(); // Loop Analysis : calculate loop depth 
            
            // print IR
            if (ArgP.hasArgument("-emit-llvm")) {
                output.println(irRoot.toString());
                break Compiling;
            }
            if (ArgP.hasArgument("-debug-ir")) {
                System.err.println(irRoot.toString());
            }

            // Allocator
            new SSALiveness(irRoot).run();
            new SSAalloctor(irRoot).run();
            new Optimize.DCE(irRoot).runJumpElimination();

            // ASMBuilder
            timer.start("ASMBuilder");
            ASMBuilder asmBuilder = new ASMBuilder();
            asmBuilder.visit(irRoot);
            timer.stop("ASMBuilder");

            // print ASM
            if (ArgP.hasArgument("-S")) {
                output.println(asmBuilder.getRoot());
                break Compiling;
            }
            
            
            
        } catch (error err) {
            System.err.println(err.toString());
            System.out.println(err.getErrorType());
            System.exit(127);
        }
        if (ArgP.hasArgument("-log-time")) {
            timer.printTimeLog();
        }


        output.flush();
        output.close();
        System.exit(0);
    }
}
