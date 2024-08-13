import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import AST.Node.RootNode;
import Frontend.ASTBuilder;
import Frontend.SemanticChecker;
import Frontend.SemanticCollector;
import Grammar.MxLexer;
import Grammar.MxParser;
import Grammar.MxParser.ProgramContext;
import Util.error.error;
import Util.scope.globalScope;
import Util.MxErrorListener;
import Util.position;

public class Main {
    public static void main(String[] args) throws Exception {
        String filename;
        InputStream input;

        if (args.length > 0) {
            filename = args[0];
            input = new FileInputStream(filename);
            System.err.println("file: " +  filename);
            position.filename = filename;
        } else {
            input = System.in;
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
            
            // System.out.println(ASTRoot.toString());
            
            // Collector
            new SemanticCollector(gScope).visit(ASTRoot);
            // Checker
            new SemanticChecker(gScope).visit(ASTRoot);

        } catch (error err) {
            System.err.println(err.toString());
            System.out.println(err.getErrorType());
            System.exit(127);
        }

        System.exit(0);
    }
}
