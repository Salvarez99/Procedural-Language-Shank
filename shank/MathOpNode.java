package shank;

public class MathOpNode extends Node {

	private Token_enum mathOp;
	private Node left;
	private Node right;
	
	public MathOpNode() {
		this.mathOp = null;
		this.left = null;
		this.right = null;
	}
	
	public MathOpNode(Token token) {
		this.mathOp = token.getToken_enum();
	}
	
	public MathOpNode(Node left, Token mathOp,  Node right) {
		this.mathOp = mathOp.getToken_enum();
		this.left = left;
		this.right = right;
	}
	
	public void setMathOp(Token mathOp) {
		this.mathOp = mathOp.getToken_enum();
	}

	public Token_enum getMathOp() {
		return mathOp;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
//		return this.left.toString() + " " + this.getMathOp() + " " + this.right.toString();
		
		switch(this.getMathOp()) {
		case PLUS:
			return this.left.toString() + " + " + this.right.toString(); 
		case MINUS:
			return this.left.toString() + " - " + this.right.toString(); 
		case TIMES:
			return this.left.toString() + " * " + this.right.toString(); 
		case DIVIDE:
			return this.left.toString() + " / " + this.right.toString();
		default:
			break; 
			
		}
		
		return null;
	}

}
