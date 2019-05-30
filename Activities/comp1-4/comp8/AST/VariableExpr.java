package AST;

public class VariableExpr extends Expr {
	
	private Variable variable;

	public VariableExpr(Variable variable){
		
		this.variable = variable;
	
	}
	
	public void genC(){
	
		System.out.print(variable.getC()); // retorna a string
	}
		
}
