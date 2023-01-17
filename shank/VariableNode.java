package shank;

public class VariableNode extends Node {

	private String varName;
	private boolean isConstant;
	private Token_enum dataType;
	private Node AST_Node;


	public VariableNode() {
		this.varName = "";
		this.isConstant = false;
		this.dataType = null;
		this.AST_Node = null;
	}


	public VariableNode(Token token) {
		this.varName = token.getData();
	}

	public VariableNode(Token token, Token_enum dataType, boolean isConstant) {
		this.varName = token.getData();
		this.dataType = dataType;
		this.isConstant = isConstant;
	}


	public VariableNode(Token token, Node aST_Node , Token_enum dataType, boolean isConstant) {
		this.varName = token.getData();
		this.isConstant = isConstant;
		this.dataType = dataType;
		this.AST_Node = aST_Node;
	}

	public VariableNode(Node aST_Node, Token_enum token_enum, boolean isConstant) {
		this.AST_Node = aST_Node;
		this.dataType = token_enum;
		this.isConstant = isConstant;
	}


	public VariableNode(Token token, boolean isVar) {
		// TODO Auto-generated constructor stub.
		this.varName = token.getData();
		super.setVar(isVar);
	}


	public String getVarName() {
		return varName;
	}


	public boolean isConstant() {
		return isConstant;
	}


	public Token_enum getDataType() {
		return dataType;
	}


	public Node getAST_Node() {
		return AST_Node;
	}


	public void setVarName(String varName) {
		this.varName = varName;
	}


	public void setConstant(boolean isConstant) {
		this.isConstant = isConstant;
	}


	public void setDataType(Token_enum dataType) {
		this.dataType = dataType;
	}


	public void setAST_Node(Node aST_Node) {
		AST_Node = aST_Node;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub

		if (super.isVar()) {
			return "var " + getVarName() + ": " + getDataType().toString().toLowerCase();
		}else
			return getVarName() + ": " + getDataType().toString().toLowerCase();
	}

}
