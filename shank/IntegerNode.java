package shank;

public class IntegerNode extends Node {

	private int integer;
	
	public IntegerNode() {
		this.integer = 0;
	}
	
	public IntegerNode(Token token) {
		this.integer = Integer.parseInt(token.getData());
	}
	
	public IntegerNode(Token token, boolean isVar) {
		// TODO Auto-generated constructor stub
		this.integer =  Integer.parseInt(token.getData());
		super.setVar(isVar);
	}

	public int getInteger() {
		return integer;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(getInteger());
	}

}
