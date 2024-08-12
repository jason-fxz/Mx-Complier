import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import AST.Node.RootNode;
import Frontend.ASTBuilder;
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

        String filename = "test.mx";
        InputStream input = new FileInputStream(filename);
        
        System.err.println("file: " +  filename);
        position.filename = filename;
        
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
            
            // Collector
            new SemanticCollector(gScope).visit(ASTRoot);
            // Checker
            
            
            System.out.println(ASTRoot.toString());
            


        } catch (error err) {
            System.err.println(err.toString());
            throw new RuntimeException();
        }
    }
}
