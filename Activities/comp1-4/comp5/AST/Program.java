package AST;

public class Program{

	private Expr expr;
	
	public Program(Expr expr){
		
		this.expr = expr;
	}
	
	public void genC(){
		System.out.println("void main () {");
		System.out.print("\t");
		expr.genC();
		System.out.println("\n }");	
	}

}
