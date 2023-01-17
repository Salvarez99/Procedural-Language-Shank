package shank;

public class VariableReferenceNode extends Node{

	private String variableName;
	
	public VariableReferenceNode() {
		super();
		this.variableName = "";
	}
	
	public VariableReferenceNode(Token token) {
		super();
		this.variableName = token.getData();
	}
	
	public VariableReferenceNode(String variableName) {
		super();
		this.variableName = variableName;
	}
	
	public VariableReferenceNode(Token token, boolean isVar) {
		// TODO Auto-generated constructor stub
		this.variableName = token.getData();
		super.setVar(isVar);
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getVariableName();
	}

}
