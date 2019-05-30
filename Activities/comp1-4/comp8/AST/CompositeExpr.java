package AST;
import Lexer.*;

public class CompositeExpr extends Expr {
	
	private Symbol op;
	private Expr esq;
	private Expr dir;

	public CompositeExpr(Symbol op, Expr esq, Expr dir){
		
		this.op = op;
		this.esq = esq;
		this.dir = dir;
	
	}
	
	public void genC(){
	
		System.out.print("(");
		esq.genC();
		System.out.print(op);
		dir.genC();
		System.out.print(")");
		
	}
	
}
