	/*
Expr ::= '(' oper  Expr Expr ')' | number
oper ::= '+' | '-'
number ::= '0' | ... | '9'	
*/

// comp1

public class Main {
    public static void main( String []args ) {
        char []input = "(-  (+ 5 4) 1)".toCharArray();
        
        Compiler compiler = new Compiler();
        compiler.compile(input);
        //System.out.println(compiler.compile(input));        
    }
}
        
