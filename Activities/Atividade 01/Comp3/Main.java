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
		char []input = "5*2+3".toCharArray();//expressao correta 
		// char []input = "5*(2*1+3*5".toCharArray();//expressao incorreta

		Compiler compiler = new Compiler();
		compiler.compile(input);
	}

}
