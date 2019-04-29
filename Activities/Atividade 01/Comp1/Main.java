/*

Gramática

E = TE'
E' = + T E' |
T = F T'
T' = * F T' |
F = Numero | (E)
*/

public class Main{
	public static void main( String []args){
		char []input = "(1 * 2)".toCharArray();//Colocar expressao correta no ""

		Compiler compiler = new Compiler();
		compiler.compile(input);
	}

}
