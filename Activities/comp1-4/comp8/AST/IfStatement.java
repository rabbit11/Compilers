package AST;

public class IfStatement extends Statement{
	
	private Expr e;
	private StatementList thenPart, elsePart;

	public IfStatement(Expr e, StatementList thenPart, StatementList elsePart) {
		// TODO Stub de construtor gerado automaticamente
		this.e = e;
		this.thenPart = thenPart;
		this.elsePart = elsePart;
	}
	
	public void genC(){
		System.out.print("if(");
		e.genC();
		System.out.println("){");
		thenPart.genC();
		System.out.println("}");
		if (elsePart != null){
			System.out.println("else{");
			elsePart.genC();
			System.out.println("}");
		}
	}
}
