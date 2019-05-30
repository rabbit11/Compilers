package AST;

public class ReadStatement extends Statement{

	VariableExpr v;
	
	public ReadStatement(VariableExpr v) {
		// TODO Stub de construtor gerado automaticamente
		this.v = v;
	}

	public void genC(){
		System.out.print("scanf(\"%d\", &");
		v.genC();
		System.out.println(");");
	}
}
