package AST;

public class CompositeExpr extends Expr {
	
	private char op;
	private Expr esq, dir;

	public CompositeExpr(char o, Expr e, Expr d){
		this.op = o;
		this.esq = e;
		this.dir = d; 		
	}
	
	public void genC(){
		System.out.print('(');
		esq.genC();
		System.out.print(op);
		dir.genC();
		System.out.print(')');
	}
	
	public int eval() {
		int valor = esq.eval();
		switch(op){
			case '+':
				valor += dir.eval();
				break;
			case '-':
				valor -= dir.eval();
				break;
			case '*':
				valor *= dir.eval();
				break;
			case '/':
				if(dir.eval() == 0)
					System.out.println("Divisao por zero!");
				else
					valor /= dir.eval();
				break;
		}	
		return valor;
	}
	
	
}
