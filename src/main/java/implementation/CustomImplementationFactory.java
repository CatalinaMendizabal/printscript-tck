/*package implementation;

import edu.austral.ingisis.g2.cli.PrintScript;
import interpreter.PrintScriptInterpreter;

public class CustomImplementationFactory implements InterpreterFactory {

    @Override
    public PrintScriptInterpreter interpreter() {
        // your PrintScript implementation should be returned here.
        // make sure to ADAPT your implementation to PrintScriptInterpreter interface.
       // throw new NotImplementedException("Needs implementation"); // TODO: implement

        return (src, version, emitter, handler) -> {
            PrintScript printScript = new PrintScript(src, version);
            try {
                printScript.run();
            } catch (Throwable e) {
                handler.reportError(e.getMessage());
            }
        };
    }
}*/
package implementation;

import ContentProvider.ContentProvider;

import ContentProvider.FileContent;
import Interpreter.*;
import Lexer.*;
import Parser.ProgramParserV1;
import Parser.ProgramParserV2;
import interpreter.PrintScriptInterpreter;
import org.austral.ingsis.printscript.parser.TokenIterator;

public class CustomImplementationFactory implements InterpreterFactory {

    @Override
    public PrintScriptInterpreter interpreter() {
        // your PrintScript implementation should be returned here.
        // make sure to ADAPT your implementation to PrintScriptInterpreter interface.
        // throw new NotImplementedException("Needs implementation"); // TODO: implement

        return (src, version, emitter, handler, provider) -> {

            ContentProvider contentProvider = new FileContent(src);
            Interpreter interpreter = new Interpreter();

            try {
                if (version.equals("1.0")) {
                    RegexLexer lexerV1 = new RegexLexer(new MatcherProviderV1());
                    ProgramParserV1 parserV1 = new ProgramParserV1(TokenIterator.create(contentProvider.getContent(), lexerV1.lex(contentProvider)));
                    emitter.print(interpreter.run(parserV1.parse()).read());
                } else if (version.equals("1.1")) {
                    RegexLexer lexerV2 = new RegexLexer(new MatcherProviderV2());
                    ProgramParserV2 parserV2 = new ProgramParserV2(TokenIterator.create(contentProvider.getContent(), lexerV2.lex(contentProvider)));
                    interpreter.run(parserV2.parse(), provider::input, emitter::print).read();
                }
            } catch (Throwable e) {
                handler.reportError(e.getMessage());
            }
        };
    }
}
