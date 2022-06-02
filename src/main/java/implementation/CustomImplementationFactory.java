package implementation;

import edu.austral.ingisis.g3.cli.PrintScript;
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
}
