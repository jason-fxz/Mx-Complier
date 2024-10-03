import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import AST.Node.RootNode;
import Allocator.SSALiveness;
import Allocator.SSAalloctor;
import Backend.ASMBuilder;
import Backend.NaiveASMBuilder;
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
        
        // InputStream input = System.in;
        try {
            RootNode ASTRoot;
            globalScope gScope = new globalScope();
            // Lexer
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            // Parse
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());

            ProgramContext parseTreeRoot = parser.program();
            
            // Build AST
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot = (RootNode) astBuilder.visit(parseTreeRoot);
            
            // print AST
            if (ArgP.hasArgument("-debug-ast")) {
                System.err.println(ASTRoot.toString());
            }
            
            // Collector
            new SemanticCollector(gScope).visit(ASTRoot);
            // Checker
            new SemanticChecker(gScope).visit(ASTRoot);

            if (ArgP.hasArgument("-fsyntax-only")) {
                System.exit(0);
            }

            // IRBuilder
            IRBuilder irBuilder = new IRBuilder();
            irBuilder.visit(ASTRoot);
            IRRoot irRoot = irBuilder.getRoot();
            

            // Mem2Reg
            new Optimize.Mem2Reg(irRoot).run();
            
            // Dead Code Elimination
            new Optimize.DCE(irRoot).run();
            
            // Loop Analysis : calculate loop depth
            new Optimize.LoopAnalysis(irRoot).run();
            
            // output.println(irRoot.toString());

            // Allocator
            new SSALiveness(irRoot).run();
            new SSAalloctor(irRoot).run();

            // print IR
            if (ArgP.hasArgument("-emit-llvm")) {
                output.println(irRoot.toString());
                System.exit(0);
            }
            if (ArgP.hasArgument("-debug-ir")) {
                System.err.println(irRoot.toString());
            }


            ASMBuilder asmBuilder = new ASMBuilder();
            asmBuilder.visit(irRoot);


            // // Naive ASMBuilder
            // NaiveASMBuilder asmBuilder = new NaiveASMBuilder();
            // asmBuilder.visit(irRoot);

            // print ASM
            if (ArgP.hasArgument("-S")) {
                output.println(asmBuilder.getRoot());
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
