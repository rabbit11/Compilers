//    comp2

/*
Expr ::= '(' oper  Expr Expr ')' | number
oper ::= '+' | '-'
number ::= '0' | ... | '9'

	 Example of program:
    
         (+ (- 5 4) 2)
*/

/*
 - um metodo para cada nao-terminal -> analisador sintatico (parser)
 - error -- aponta o erro (quando ocorrer)
 - nextToken (analisador lexico) -- posicao do proximo token valido 
 */

public class Compiler {

    public void compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        nextToken();
        expr();
		    
	    if ( token != '\0' )
          error();

	
    }

    //Expr::=  '(' oper Expr  Expr ')' | number
    private void expr() {
		if (token == '('){
			char op;
			System.out.print(token);
			nextToken();
			op = oper();
			expr();
			System.out.print(op);
			expr();
			if(token == ')') {
				System.out.print(token);
				nextToken();
			}
			else
				error();		
		}
		else
			number();
		
    }
    
    
    //oper ::= '+' | '-'
    private char oper() {
		if (token == '+' || token == '-') {
			char op = token;
			nextToken();
			return op;
		} else
			error();    
		return '\0';
	}

    //number ::= '0'| '1' | ... | '9'
    private void number() {
		if(token >= '0' && token <= '9') {
			System.out.print(token);
			nextToken();
		}
		else
			error();    

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
