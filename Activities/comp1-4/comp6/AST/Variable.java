package AST;

public class Variable{
	private VariableExpr letra;
	private NumberExpr num;

	public Variable(VariableExpr letra, NumberExpr num){
		this.letra = letra;
		this.num = num;
	}

	public void genC(){
		System.out.print("\t int ");
		letra.genC();
		System.out.print(" = "); 
		num.genC();
		System.out.println(";");
	}
}