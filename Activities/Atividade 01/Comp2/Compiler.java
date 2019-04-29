/*

Gramática

E = TE'
E' = + T E' |
T = F T'
T' = * F T' |
F = Numero | (E)
*/

public class Compiler {

	public void compile(char []p_input){
		input = p_input;
		tokenPos = 0;
		nextToken();
		E();

		if(token != '\0')
			error();
	}

	public void E(){
		T();
		El();
	}

	public void El(){
		if(token == '+'){
			nextToken();
			T();
			El();
		}else{
			T();
		}
	}

	public void T(){
		F();
		Tl();
	}

	public void Tl() {
		if(token == "*"){
			nextToken();
			F();
			Tl();
		}else{
			F();
		}
	}

	public void F() {
		if(!(token.isNumber())){
			nextToken();
			E();
		}else{
			nextToken();
		}
	}

	private void expr(){
		if(token == '('){
			char op;
			nextToken();
			op = oper;
			expr();
			
			if(token == ')'){
				nextToken();
			}else{
				error();
			}
		}else{
			number();
		}
	}

	private char oper(){
		if(token == '+' || token == '-'){
			char op token;
			nextToken();
			return op;
		}else{
			error();
		}
		return '\0';
	}

	private char number(){
		if(token >= '0' && token <= '9'){
			nextToken();
		}else{
			error();
		}
	}
	
	private void nextToken(){
		while(tokenPos < input.length && input[tokenPos] == ' '){
			tokenPos++;
		}

		if(tokenPos >= input.length)
			token = '\0';
		else{
			token = input[tokenPos];
			//System.out.print(token);
			tokenPos++;
		}
	}

	private void error(){
		if(tokenPos == 0)
			tokenPos = 1;
		else
			if (tokenPos >= input.length)
				tokenPos = input.length;

		String strInput = new String(input, tokenPos -1, input.length - tokenPos +1);
		String strError = "Error at \""+ strInput + "\"";
		System.out.println(strError);
		throw new RuntimeException(strError);
	}

	private char token;
	private int tokenPos;
	private char []input;
}
