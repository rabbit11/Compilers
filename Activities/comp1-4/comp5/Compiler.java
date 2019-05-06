//    comp5

/*

This compiler builds an Abstract Syntax Tree (AST) for the input expression and generates code through it.



Expr ::= '(' oper  Expr Expr ')' | number
oper ::= '+' | '-'
number ::= '0' | ... | '9'	
*/

import AST.*;

public class Compiler {

    public Program compile( char []p_input ) {
    	Expr res = null;
        input = p_input;
        tokenPos = 0;
        nextToken();
        res = expr();
        if ( token != '\0' )
          error();
          
        return new Program(res);
    }

    //Expr::=  '(' oper Expr  Expr ')' | number
    private Expr expr() {
		
		Expr res = null;		

		if(token == '('){
			nextToken();
			char op;
			op = oper();						
			Expr ExprE, ExprD;
			ExprE = expr();
			ExprD = expr();

			if(token == ')'){
				nextToken();
			
				res = new CompositeExpr(op, ExprE, ExprD);

			}
			else
				error();						
		}
		else 
			res = number();		

		return res;	
    }
    
    
    //oper ::= '+' | '-'
    private char oper() {
		char token1 = ' ';	
		if(token == '+' || token == '-'){
			token1 = token;			
			nextToken();
		}
		else 
			error();

		return token1;
    }

    //number ::= '0'| '1' | ... | '9'
    private NumberExpr number() {
		NumberExpr number = null;     
		
		if(token >= '0' && token <= '9'){
			number = new NumberExpr(token);			
			nextToken();		
		}
		else
			error();	
		
		return number;
	}

    private void nextToken() {
		
		while(tokenPos < input.length && input[tokenPos] == ' '){
			tokenPos++;
		}

		if(tokenPos >= input.length)
			token = '\0';
		else {
			token = input[tokenPos];
			tokenPos++;
		}
			    	
    }
    
    private void error() {
        if ( tokenPos == 0 ) 
          tokenPos = 1; 
        else 
          if ( tokenPos >= input.length )
            tokenPos = input.length;
        
        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println( strError );
        throw new RuntimeException(strError);
    }
    
    private char token;
    private int  tokenPos;
    private char []input;
      
}
