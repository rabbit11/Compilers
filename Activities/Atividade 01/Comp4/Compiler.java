/*

Gramática

E = TE'
E' = + T E' |
T = F T'
T' = * F T' |
F = Numero | (E)
*/

public class Compiler {

	public void compile(char[] p_input) {
		input = p_input;
		tokenPos = 0;
		nextToken();
		E();

		if (token != '\0')
			error();
	}

	public void E() {
		char esq = T();
		char op = El();
		char dir = T();

		// T();
		if (op != '\0') {
			dir = esq + dir;
			// System.out.println("POP R1");
			// System.out.println("POP R0");
			// System.out.println("ADD R0, R1");
		}
		System.out.println(dir);
		// System.out.println("PUSH R0");
	}

	public char El() {
		if (token == '+') {
			char op;
			op = token;
			System.out.print(token);
			nextToken();
			T();
			El();
			return op;
		}
		return '\0';
	}

	public int T() {
		int esq = F();

		if (esq != -1) {
			// System.out.println("mov R0, " + num);
			// System.out.println("push R0");
			return esq;
		}
		char op = Tl();

		if (op != '\0') {
			int dir = F();
			esq = esq * dir;
			
			// System.out.println("POP R1");
			// System.out.println("POP R0");
			// System.out.println("MULTIPLY R0, R1");
			
		}
		return esq;
	}

	public char Tl() {
		if (token == '*') {
			char op;
			op = token;
			System.out.print(token);
			nextToken();
			F();
			Tl();
			return op;
		}
		return '\0';
	}

	public int F() {
		int valor = -1;
		if (token >= '0' && token <= '9') {
			valor = token - '0';
			System.out.println(token);
			nextToken();
		} else if (token == '(') {
			nextToken();
			E();

			if (token == ')') {
				nextToken();
			} else
				error();
		} else {
			error();
		}
		return valor;
	}

	private void nextToken() {
		while (tokenPos < input.length && input[tokenPos] == ' ') {
			tokenPos++;
		}

		if (tokenPos >= input.length)
			token = '\0';
		else {
			token = input[tokenPos];
			// System.out.print(token);
			tokenPos++;
		}
	}

	private void error() {
		if (tokenPos == 0)
			tokenPos = 1;
		else if (tokenPos >= input.length)
			tokenPos = input.length;

		String strInput = new String(input, tokenPos - 1, input.length - tokenPos + 1);
		String strError = "Error at \"" + strInput + "\"";
		System.out.println(strError);
		throw new RuntimeException(strError);
	}

	private char token;
	private int tokenPos;
	private char[] input;
}
