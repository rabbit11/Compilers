package AST;

public class VariableExpr extends Expr {
	
	private char letra;	

	public VariableExpr(char n){
		this.letra = n;
	}
	
	public void genC(){
		System.out.print(letra);
	}
}
