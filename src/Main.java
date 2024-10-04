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
        
        try {
            
            RootNode ASTRoot;
            globalScope gScope = new globalScope();
            // ExecutionTimer.timer 
            // Lexer
            timer.start("Lexer");
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            timer.stop("Lexer");

            // Parse
            timer.start("Parser");
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            timer.stop("Parser");

            ProgramContext parseTreeRoot = parser.program();
            
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
                timer.printTimeLog();
                System.exit(0);
            }

            // IRBuilder
            timer.start("IRBuilder");
            IRBuilder irBuilder = new IRBuilder();
            irBuilder.visit(ASTRoot);
            IRRoot irRoot = irBuilder.getRoot();
            timer.stop("IRBuilder");
            

            // Mem2Reg
            timer.start("Mem2Reg");
            new Optimize.Mem2Reg(irRoot).run();
            timer.stop("Mem2Reg");
            
            // Dead Code Elimination
            timer.start("DCE");
            new Optimize.DCE(irRoot).run();
            timer.stop("DCE");
            
            // Loop Analysis : calculate loop depth
            timer.start("LoopAnalysis");
            new Optimize.LoopAnalysis(irRoot).run();
            timer.stop("LoopAnalysis");
            

            // Allocator
            timer.start("Liveness");
            new SSALiveness(irRoot).run();
            timer.stop("Liveness");
            timer.start("Allocator");
            new SSAalloctor(irRoot).run();
            timer.stop("Allocator");

            // print IR
            if (ArgP.hasArgument("-emit-llvm")) {
                output.println(irRoot.toString());
                timer.printTimeLog();
                System.exit(0);
            }
            if (ArgP.hasArgument("-debug-ir")) {
                System.err.println(irRoot.toString());
            }

            // ASMBuilder
            timer.start("ASMBuilder");
            ASMBuilder asmBuilder = new ASMBuilder();
            asmBuilder.visit(irRoot);
            timer.stop("ASMBuilder");

            // print ASM
            if (ArgP.hasArgument("-S")) {
                output.println(asmBuilder.getRoot());
                timer.printTimeLog();
                System.exit(0);
            }

            
            
        } catch (error err) {
            System.err.println(err.toString());
            System.out.println(err.getErrorType());
            System.exit(127);
        }

        output.flush();
        output.close();
        System.exit(0);
    }
}
