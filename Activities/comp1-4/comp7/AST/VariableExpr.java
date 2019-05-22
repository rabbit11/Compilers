package AST;

public class VariableExpr extends Expr {
	
	private Variable letter;	

	public VariableExpr(Variable n){
		this.letter = n;
	}
	
	public void genC(){
		System.out.print(letter.getLetra());
	}
	
	public int eval(){
		return letter.getNumberExpr().eval();
	}
}
