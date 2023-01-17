package shank;

public class BooleanExpressionNode extends StatementNode {
	
	private Node leftExpression;
	private Token condition;
	private Node rightExpression;
	
	
	
	public BooleanExpressionNode() {
		super();
	}

	public BooleanExpressionNode(Node leftExpression, Token condition, Node rightExpression) {
		super();
		this.leftExpression = leftExpression;
		this.condition = condition;
		this.rightExpression = rightExpression;
	}

	public Node getLeftExpression() {
		return leftExpression;
	}

	public void setLeftExpression(Node leftExpression) {
		this.leftExpression = leftExpression;
	}

	public Token getCondition() {
		return condition;
	}

	public void setCondition(Token condition) {
		this.condition = condition;
	}

	public Node getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(Node rightExpression) {
		this.rightExpression = rightExpression;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getLeftExpression().toString() + " " + getCondition().getData() + " " + getRightExpression().toString();
	}

}
