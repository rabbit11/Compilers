/*
    comp8
    
    Variables now can have any number of characters and numbers any number 
    of digits. There are new keywords and new non-terminals. The operator
    set includes the comparison operators. There are a few statements.
    Anything after // till the end of the line is a comment. 
    Note that VarDecList was modified.
    
    The input is now taken from a file.
    Method error now prints the line in which the error occurred.
    
    
    Grammar:
       Program ::= [ "var" VarDecList ";" ] CompositeStatement
       CompositeStatement ::= "begin" StatementList "end"
       StatementList ::= | Statement ";" StatementList
       Statement ::= AssignmentStatement | IfStatement | ReadStatement |
          WriteStatement
       AssignmentStatement ::= Variable "="  Expr  
       IfStatement ::= "if" Expr "then" StatementList [ "else" StatementList ] "endif" 
       ReadStatement ::= "read" "(" Variable ")"
       WriteStatement ::= "write" "(" Expr ")"
          
       Variable ::= Letter { Letter } 
       VarDecList ::= Variable | Variable "," VarDecList 
       Expr::=  '(' oper Expr  Expr ')' | Number | Variable
       Oper ::= '+' | '-' | '*' | '/' | '<' | '<=' | '>' | '>='| '==' | '<>'
       Number ::= Digit { Digit }
       Digit ::= '0'| '1' | ... | '9'
       Letter ::= 'A' | 'B'| ... | 'Z'| 'a'| 'b' | ... | 'z'
       
   Anything between [] is optional. Anything between { e } can be 
   repeated zero or more times.
*/

import AST.*;
import Lexer.*;

import java.util.ArrayList;
import java.util.Hashtable;

 

public class Compiler {

      // contains the keywords
    static private Hashtable<String, Symbol> keywordsTable;

     // this code will be executed only once for each program execution
     static {
        keywordsTable = new Hashtable<String, Symbol>();
        keywordsTable.put( "var", Symbol.VAR );
        keywordsTable.put( "if", Symbol.IF );
        keywordsTable.put( "else", Symbol.ELSE );
        keywordsTable.put( "then", Symbol.THEN );
        keywordsTable.put( "endif", Symbol.ENDIF );
        keywordsTable.put( "read", Symbol.READ );
        keywordsTable.put( "write", Symbol.WRITE );
        keywordsTable.put( "begin", Symbol.BEGIN );
        keywordsTable.put( "end", Symbol.END );
     }

    public Program compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        lineNumber = 1;
        nextToken();
        
        symbolTable = new Hashtable<String, Variable>();
        Program e = program();
        if (token != Symbol.EOF)
          error("End program expected");
          
        return e;
    }

	// Program ::= [ "var" VarDecList ";" ] CompositeStatement
    private Program program(){
    	
    	ArrayList<Variable> alv = null; 
    	StatementList sl;
    	
    	if(token == Symbol.VAR ){
    		nextToken();
    		alv = varDecList();
    		if(token==Symbol.SEMICOLON){
    			nextToken();
    		}else{
    			error("missing semicolon");
    		}
    	}
    	sl = compositeStatement();
    	
    	return new Program(alv, sl);
    
    }

          //  CompositeStatement ::= "begin" StatementList "end"    
    private StatementList compositeStatement() {
   	     
   				StatementList sm = null;
   		
   			if(token == Symbol.BEGIN){
   				nextToken();
   				sm = statementList();
   				if(token== Symbol.END){
   					nextToken();
   				}else{
   					error("missing end");
   				}
   				
   				
   			}else{
   				error("missing begin");
   			} 						
   
   			return sm;
    }
    
    
    
    
    

          //  StatementList ::= | Statement ";" StatementList
    private StatementList statementList() {
    		ArrayList<Statement> sl = new ArrayList<Statement>();
    		Statement st; 
    		
    	
    		while(true){
    			
    			st = statement();		
    			
    			if(st==null){
    				break;	
    			}else{
    				sl.add(st);
    				if(token==Symbol.SEMICOLON){
    					nextToken();
    				}else{
    					error("missing semicolon");
    					
    				}
    			}	
    		}	
    		
    		return new StatementList(sl);
   	}



        /*  Statement ::= AssignmentStatement | IfStatement | ReadStatement |                          WriteStatement
        */            
    private Statement statement() {
        
        	Statement s = null;
        	
        	if(Symbol.IDENT == token) {
        		s = assignmentStatement();
        	} else if(Symbol.IF == token) {
        		s = ifStatement();
        	} else if(Symbol.WRITE == token) {
        		s = writeStatement();
        	} else if(Symbol.READ == token) {
        		s = readStatement();
        	}
        
        return s;
        
        
    }
    //AssignmentStatement ::= Variable "="  Expr
    private AssignmentStatement assignmentStatement() {
        
        VariableExpr var = null;
        Expr expr = null;
        
        if(token==Symbol.IDENT){
        
        	// the current token is Symbol.IDENT and 		stringValue
          // contains the identifier
        String name = stringValue;
        
        	nextToken();         
        
          // is the variable in the symbol table ? Variables are inserted in the
          // symbol table when they are declared. It the variable is not there, it has
          // not been declared.
          Variable v = symbolTable.get(name);
          if(v==null){
          	error("Variavel nao declarada");
          }else{
          	var = new VariableExpr(v);
          	if(token==Symbol.ASSIGN){
          		nextToken();
          		expr = expr();
          	}
          	else{
          	error("Falta =");
          	}
          }
        }  
    
    	return new AssignmentStatement(var,expr);
    
    }
    
    
     //  IfStatement ::= "if" Expr "then" StatementList [ "else" StatementList ] "endif"   
    private IfStatement ifStatement() {
    
    Expr e = null;
	StatementList thenPart = null;
	StatementList elsePart = null;
	
	if(token==Symbol.IF){
		nextToken();
		e = expr();
		if(token==Symbol.THEN){
			nextToken();
			thenPart = statementList();
			if(token==Symbol.ELSE){
				nextToken();
				elsePart = statementList();
			}
			if(token==Symbol.ENDIF){
				nextToken();
			}else{
			error("falta endif");
			}
		}else{
		error("falta then");
		}
	}else{
	error("Falta if");
	}
    
    return new IfStatement(e,thenPart,elsePart);
    	
    }
            
          
     //  ReadStatement ::= "read" "(" Variable ")"
    private ReadStatement readStatement() {
    	VariableExpr var = null;
        
		if(token == Symbol.READ) {
			nextToken();
			if(token == Symbol.LEFTPAR) {
				nextToken();
				// check if the variable was declared
				if(token==Symbol.IDENT){
        
					// the current token is Symbol.IDENT and 		stringValue
				  	// contains the identifier
		    		String name = stringValue;
		    
		    		nextToken();         
		    
		      		// is the variable in the symbol table ? Variables are inserted in the
		      		// symbol table when they are declared. It the variable is not there, it has
		      		// not been declared.
		      		Variable v = symbolTable.get(name);
				  	if(v==null){
				  		error("Variavel nao declarada");
				  	}else{
				  		var = new VariableExpr(v);
				  		if(token == Symbol.RIGHTPAR) {
						nextToken();
						} else
							error(") esperado");
				  	}
				}
			} else
				error("( esperado");	
		} else
			error("read esperado");
        return new ReadStatement(var);
        
    }
    

     // WriteStatement ::= "write" "(" Expr ")"    
    private WriteStatement writeStatement() {
    	Expr expr = null;
    	
    	if(token == Symbol.WRITE) {
    		nextToken();
    		if(token == Symbol.LEFTPAR) {
    			nextToken();
    			expr = expr();
    			if(token == Symbol.RIGHTPAR) {
    				nextToken();
    			} else
    				error(") esperado");
    		} else
    			error("( esperado");
    	} else
    		error("write esperado");
    	return new WriteStatement(expr);
    }
        
        
            
    //   VarDecList ::= Variable | Variable "," VarDecList            
    private ArrayList<Variable> varDecList() {
		ArrayList<Variable> retorno = new ArrayList<Variable>();
        Variable variable = varDec();
        retorno.add(variable);
		while(token == Symbol.COMMA){
            nextToken();
			retorno.add(varDec());
		}
		

		return retorno;
    }

    private Variable varDec() {
        Variable v = null;
        
          // semantic analysis
          // if the name is in the symbol table, the variable has been declared twice.
          if(token == Symbol.IDENT) {
          	String name = stringValue;
          	v = new Variable(stringValue);
          	nextToken();
          	// inserts the variable in the symbol table. The name is the key and an
         	// object of class Variable is the value. Hash tables store a pair (key, value)
          	// retrieved by the key.
          	if(symbolTable.put(name, v) != null) {
          		error("variavel declarada");
          	}
          }
          return v;          
    }


    //Expr::=  '(' oper Expr  Expr ')' | number | Letter
    private Expr expr() {
		
		Expr res = null;		

		if(token == Symbol.LEFTPAR){
			nextToken();
			Symbol op;
			op = oper();						
			Expr ExprE, ExprD;
			ExprE = expr();
			ExprD = expr();

			if(token == Symbol.RIGHTPAR){
				nextToken();
			
				res = new CompositeExpr(op, ExprE, ExprD);

			}
			else
				error(") esperado");						
		} else 
			if(token == Symbol.NUMBER) {
				res = new NumberExpr(numberValue);
				nextToken();		
			} else {
				if(token == Symbol.IDENT){
					String name = stringValue;
					nextToken();
					Variable aux = symbolTable.get(name);
					if(aux != null){
						res = new VariableExpr(aux);
					} else
						error("Variavel nao declarada: "+ name);
				} else
					error("simbolo não esperado");
				
			}

		return res;	
    }
        
    
	private Symbol oper() {
		Symbol aux = null;
		if (token == Symbol.PLUS || token == Symbol.MINUS || token == Symbol.MULT || token == Symbol.DIV || token == Symbol.LE || token == Symbol.LT || token == Symbol.GE || token == Symbol.GT || token == Symbol.EQ || token == Symbol.NEQ) {
			aux = token;
			nextToken();
		}
		else {
			error("Operador inválido.");
		}
		return aux;
	}  

    private void nextToken() {
    	while(tokenPos < input.length && 
    		(input[tokenPos] == ' ' || 
    		input[tokenPos] == '\n' ||
    		input[tokenPos] == '\t' ||
    		input[tokenPos] == '\r')) {
    		if(input[tokenPos] == '\n')
    			lineNumber++;
    		tokenPos++;
    	} 
    	
    	if(tokenPos >= input.length - 1) {
    		token = Symbol.EOF;
    		return;
    	}
    	
    	//ignorando comentario //
    	if(input[tokenPos] == '/' &&
    		input[tokenPos+1] == '/') {
    		while(tokenPos < input.length && 
    			input[tokenPos] != '\n')
    			tokenPos++;
	    	if(tokenPos == input.length) {
	    		token = Symbol.EOF;
	    		return;
    		}
    		
    		lineNumber++;	
    		nextToken();	
    	}
    	
    	String buffer = "";
    	if(Character.isLetter(input[tokenPos])) {
			while(Character.isLetter(input[tokenPos])) {
				buffer += input[tokenPos++];
			}
			
			if(buffer != "") {
				token = keywordsTable.get(buffer);
				if(token == null) {
					token = Symbol.IDENT;
					stringValue = buffer;
				}
			}
    	} else if(Character.isDigit(input[tokenPos])) {
    		while(Character.isDigit(input[tokenPos])) {
    			buffer += input[tokenPos++];
    		}	
    		numberValue = Integer.parseInt(buffer);
    		if(numberValue > MaxValueInteger) {
    			error("overflow int");
    		}
    		token = Symbol.NUMBER;
    	} else {
    		switch(input[tokenPos]) {
    			case '+':
    				token = Symbol.PLUS;
    				break;
    			case '-':
    				token = Symbol.MINUS;
    				break;	
    			case '*':
    				token = Symbol.MULT;
    				break;
    			case '/':
    				token = Symbol.DIV;
    				break;
    			case '<':
    				if(input[tokenPos+1] == '=') {
    					tokenPos++;
    					token = Symbol.LE;
    				} else
    					token = Symbol.LT;
    				break;
    			case '>':
    				if(input[tokenPos+1] == '=') {
    					tokenPos++;
    					token = Symbol.GE;
    				} else
    					token = Symbol.GT;
    				break;	
    			case '!':
    				if(input[tokenPos+1] == '=') {
    					tokenPos++;
    					token = Symbol.NEQ;
    				} else
    					error("= esperado");
    				break;
    			case '=':	
    				if(input[tokenPos+1] == '=') {
    					tokenPos++;
    					token = Symbol.EQ;
    				} else {
    					token = Symbol.ASSIGN;
    				}
    				break;
    			case '(':
    				token = Symbol.LEFTPAR;
    				break;
    			case ')':
    				token = Symbol.RIGHTPAR;
    				break;
    			case ';':
    				token = Symbol.SEMICOLON;
    				break;
    			case ',':
    				token = Symbol.COMMA;
    				break;
    			default :
    				error("token nao identificado");
    		}
    		tokenPos++;    		
    	}
    	System.out.println(token.toString());
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
    
    private int  tokenPos;
    private char []input;
 	private Hashtable<String, Variable> symbolTable;   
 	
 	private Symbol token; // notem a mudanca aqui!
    private String stringValue;
    private int numberValue;
    // number of current line. Starts with 1
    private int lineNumber;
    private static final int MaxValueInteger = 32768; // 2 ^ 15

 	  
}
