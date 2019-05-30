package AST;
import java.util.ArrayList;

public class Program{

	private StatementList sl;
	private ArrayList<Variable> alv;
	
	public Program(ArrayList<Variable> alv, StatementList sl){
		
		this.alv = alv;
		this.sl = sl;
	}
	
	public void genC(){
	
		System.out.println("#include<stdio.h>");
		System.out.println();
		System.out.println("int main(){");
		
		for(Variable v: alv)
			v.genC();
		
		sl.genC();
		System.out.println("return 0;");
		System.out.println("}");
	}
}
