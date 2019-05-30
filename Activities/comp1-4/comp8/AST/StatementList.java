package AST;

import java.util.ArrayList;

public class StatementList extends Statement{
	ArrayList<Statement> sl;
	
	public StatementList(ArrayList<Statement> s){
		this.sl = s;
	}
	public void genC(){ // para cada elemento da lista chama sl
		for(Statement s: sl)
			s.genC();
		
	}

}
