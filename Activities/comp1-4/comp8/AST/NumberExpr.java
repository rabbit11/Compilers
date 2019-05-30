package AST;

public class NumberExpr extends Expr {
	
	private int number;

	public NumberExpr(int number){
		
		this.number = number;
	
	}
	
	public void genC(){
		System.out.print(number); // imprime umero inteiro
	}
}
