import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import AST.Node.RootNode;
import Grammar.MxLexer;
import Grammar.MxParser;
import Grammar.MxParser.ProgramContext;
import Util.error.error;
import Util.MxErrorListener;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Mx world!");

        String filename = "test.mx";
        InputStream input = new FileInputStream(filename);

        try {
            RootNode ASTRoot;
            // Lexer
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            // Parse
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());

            ProgramContext parseTreeRoot = parser.program();
            
            
        } catch (error err) {
            System.err.println(err.toString());
            throw new RuntimeException();
        }
    }
}
