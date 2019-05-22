/*
    comp7

private void error(String errorMsg) => Error Message

Class Hashtable has methods
    Object put(Object key, Object value)
    Object get(Object key)
    
Method put inserts value at the table using key as its name (or key!). It returns the table object that had key key. If there were none, it returns null:

  Hashtable h = new Hashtable();
  h.put( "one", new Integer(1) );
  h.put( "two", new Integer(2) );
  Object obj = h.put("one", "I am the one");
  System.out.println(obj);
  // prints 1
  obj = h.get("two");
  System.out.println(obj);
  // prints 2
  System.out.println( h.get("one") ); // prints "I am the one"
  char name = ’a’;
  h.put( name + "", "This is an a");
  System.out.println( h.get("a") );

In the last call to put, we used name + "" as the key. We could not have used just name because name is not an object.
    
    This compiler uses the same grammar as compiler 6:
       Program ::= VarDecList ':' Expr   
       VarDecList ::=  | VarDec VarDecList 
       VarDec ::= Letter '=' Number
       Expr::=  '(' oper Expr  Expr ')' | Number | Letter
       Oper ::= '+' | '-' | '*' | '/'
       Number ::= '0'| '1' | ... | '9'
       Letter ::= 'A' | 'B'| ... | 'Z'| 'a'| 'b' | ... | 'z'
       
     The compiler evaluate the expression
     
*/
 
import AST.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {

    public Program compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        
        symbolTable = new Hashtable();
        
        nextToken();
        
        
        Program e = program();
        if (tokenPos != input.length)
          error("Fim de codigo esperado!");
          
        return e;
    }

	//Program ::= VarDecList ':' Expr   
    private Program program(){
		ArrayList<Variable> esq = varDecList();
		Expr dir = null;
		if(token == ':'){
			nextToken();
			dir = expr();
		}else
			error(": esperado");
	
		return new Program(esq,dir);
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
		char aux = letter();
		Variable aux1;
		NumberExpr num = null;
		if(token == '='){
			nextToken();
			 num = number();
		}else
			error("= esperado!");
			
		aux1 = new Variable(aux, num);
		
		if(symbolTable.put(aux, aux1) != null){
			error("Variavel ja declarada: " + aux);
		}
		
		return aux1;
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
				error(") esperado");						
		}
		else 
			if(token >= '0' && token <= '9')
				res = number();		
			else{
				char letter1 = letter();
				Variable aux = symbolTable.get(letter1);
				if(aux != null){
					res = new VariableExpr(aux);
				}
				else
					error("Variavel nao declarada: " + letter1);
			}

		return res;	
    }
    
    
    //oper ::= '+' | '-'
    private char oper() {
		char token1 = ' ';	
		if(token == '+' || token == '-' || 
			token == '*' || token == '/'){
			token1 = token;			
			nextToken();
		}
		else 
			error("Operador nao reconhecido!");

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
			error("Digito incorreto");	
		
		return number;
	}
    
    private char letter(){
		char aux = ' ';
		if(token >= 'a' && token <= 'z'){
			aux = token;
			nextToken();
		}else
			error("Letra esperada");

		return aux;
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
    
    private void error(String errorMsg) {
        if ( tokenPos == 0 ) 
          tokenPos = 1; 
        else 
          if ( tokenPos >= input.length )
            tokenPos = input.length;
        
        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println( strError );
        System.out.println( errorMsg );
        throw new RuntimeException(strError);
    }
    
    private char token;
    private int  tokenPos;
    private char []input;
    private Hashtable<Character, Variable> symbolTable;     
}
