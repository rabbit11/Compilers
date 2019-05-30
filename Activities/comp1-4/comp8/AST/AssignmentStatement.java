package AST;

//AssignmentStatement ::= Variable "=" Expr
public class AssignmentStatement extends Statement {
	private VariableExpr var;
	private Expr expr;
	public AssignmentStatement(VariableExpr var, Expr expr) {
		this.var = var;
		this.expr = expr;
	}
	@Override
	public void genC() {
		var.genC(); // nome da variavel
		System.out.print("=");
		expr.genC();
		System.out.println(";");
	}
}
