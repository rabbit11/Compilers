package AST;

public class Variable{
	
	private String c;

	public Variable(String c){
		
		this.c = c;
	
	}
	
	public void genC(){
		System.out.println("int " + c + ";");
	}
	
	public String getC(){
		return c;
	}

	
}
