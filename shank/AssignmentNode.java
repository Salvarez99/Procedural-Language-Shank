package shank;

public class AssignmentNode extends StatementNode {

	private VariableReferenceNode target;
	private Node expression;
	
	public AssignmentNode() {
		super();
		this.target = null;
		this.expression = null;
	}

	public AssignmentNode(VariableReferenceNode target) {
		super();
		this.target = target;
	}

	public AssignmentNode(Node expression) {
		super();
		this.expression = expression;
	}

	public AssignmentNode(VariableReferenceNode target, Node expression) {
		super();
		this.target = target;
		this.expression = expression;
	}

	public VariableReferenceNode getTarget() {
		return target;
	}

	public void setTarget(VariableReferenceNode target) {
		this.target = target;
	}

	public Node getExpression() {
		return expression;
	}

	public void setExpression(Node expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getTarget().toString() + ":= " + getExpression().toString();
	}

}
