// comp 7

import AST.*;

public class Main {
    public static void main( String []args ) {
        char []input = "a = 5  b = 3 : (- (+ a 2) b)".toCharArray();
        
        Compiler compiler = new Compiler();
        
        Program program  = compiler.compile(input);
        program.genC();
        System.out.println("Resultado da expressao " +  program.eval());
    }
}
        
