/*
    comp6
    
    
    We added to the grammar more operators and a declaration of variables so that 
    the grammar now accepts a program like 
          a = 1  b = 3  : (- (+ a 2) 3)
    The result of the evaluation would be 0. Another program would be
          g = 3  t = 9  : (* (- t 7) g)

    Of course, the AST was extended to cope with the new rules. New classes
    were created: 
         Program - represents the program
         Variable - a variable in the declaration
         VariableExpr - a variable inside an expression
         
    Grammar:
       Program ::= VarDecList ':' Expr   
       VarDecList ::=  | VarDec VarDecList 
       VarDec ::= Letter '=' Number
       Expr::=  '(' oper Expr  Expr ')' | Number | Letter
       Oper ::= '+' | '-' | '*' | '/'
       Number ::= '0'| '1' | ... | '9'
       Letter ::= 'A' | 'B'| ... | 'Z'| 'a'| 'b' | ... | 'z'
       
     
*/
 
import AST.*;
import java.util.ArrayList;

public class Compiler {

    public Program compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        nextToken();
        
        
        Program e = program();
        if (tokenPos != input.length)
          error();
          
        return e;
    }

	//Program ::= VarDecList ':' Expr   
    private Program program(){

      ArrayList<Variable> esq = varDecList();
      Expr dir = null;

      if(token == ':'){
        nextToken();
        dir = expr();
      }
      else
        error();

      return new Program(esq, dir);
    }
    
    
    //VarDecList ::=  | VarDec VarDecList 
    private ArrayList<Variable> varDecList(){
      ArrayList<Variable> retorno = new ArrayList<Variable>();
     
      while(token != ':'){
          retorno.add(varDec());
      }
      return retorno;
    }
    
    
    // VarDec ::= Letter '=' Number
	private Variable varDec(){
	  VariableExpr letra = letter();
    NumberExpr num = null;
   
     if(token == '='){
      nextToken();
      num = number();
     }
     return new Variable(letra, num);
	}

    //Expr::=  '(' oper Expr  Expr ')' | number | Letter
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
      else if(Character.isLetter(token))
              res = letter();
           else
              res = number();

      return res;
    }
    
    
    
    
    //oper ::= '+' | '-' | '*' | '/'
    private char oper() {
      char token1 = ' ';
      if(token == '+' || token == '-' || token == '*' || token == '/'){
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
    
    private VariableExpr letter(){
      VariableExpr letra = null;
      
      if(Character.isLetter(token)){
        letra = new VariableExpr(token);
        nextToken();
      }
      else
        error();
      
      return letra;
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
