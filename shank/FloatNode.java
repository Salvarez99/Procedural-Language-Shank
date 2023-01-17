package shank;

public class FloatNode extends Node {

	private float floatnum;
	
	public FloatNode() {
		this.floatnum = 0.f;
	}
	
	public FloatNode(Token token) {
		this.floatnum = Float.parseFloat(token.getData());
	}
	
	public FloatNode(Token token, boolean isVar) {
		// TODO Auto-generated constructor stub
		this.floatnum =  Float.parseFloat(token.getData());
		super.setVar(isVar);
	}

	public float getFloat() {
		return floatnum;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Float.toString(getFloat());
	}

}
