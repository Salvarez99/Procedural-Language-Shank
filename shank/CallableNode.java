package shank;

import java.util.ArrayList;

public abstract class CallableNode extends Node {

	private String functionName;
	private ArrayList<VariableNode> parameterList;
	
	public CallableNode() {
		super();
		this.parameterList = new ArrayList<>();
	}

	public CallableNode(String functionName) {
		super();
		this.functionName = functionName;
	}

	public CallableNode(ArrayList<VariableNode> parameterList) {
		super();
		this.parameterList = parameterList;
	}
	
	public CallableNode(String functionName, ArrayList<VariableNode> parameterList) {
		super();
		this.functionName = functionName;
		this.parameterList = parameterList;
	}

	public ArrayList<VariableNode> getParameterList() {
		return parameterList;
	}
	
	public void setParameterList(ArrayList<VariableNode> parameterList) {
		this.parameterList = parameterList;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public void setFunctionName(Token token) {
		this.functionName = token.getData();
	}
	
	@Override
	public abstract String toString();

}
