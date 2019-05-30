package AST;

public class WriteStatement extends Statement{
	Expr e;
	
	public WriteStatement (Expr e){
		this.e = e;
	}
	
	public void genC(){
		System.out.print("printf(\"%d\", ");
		e.genC();
		System.out.println(");");
	}

}
