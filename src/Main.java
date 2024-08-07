import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import Grammar.MxLexer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Mx world!");

        String filename = "test.mx";
        InputStream input = new FileInputStream(filename);

        try {
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));

        } catch (error err) {
            System.err.println(err.toString());
            throw new RuntimeException();
        }
    }
}
