/*
Expr ::= '(' oper  Expr Expr ')' | number
oper ::= '+' | '-'
number ::= '0' | ... | '9'	
*/

// comp5

import AST.*;

public class Main {
    public static void main( String []args ) {
        char []input = "(-     (+ 54)1)".toCharArray();
        Program program = null;
        
        Compiler compiler = new Compiler();
        program = compiler.compile(input);
        
        program.genC();        
    }
}
        
