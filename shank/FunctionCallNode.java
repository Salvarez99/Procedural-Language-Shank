package shank;

import java.util.ArrayList;

public class FunctionCallNode extends StatementNode {
	private String functionName;
	private ArrayList<ParameterNode> parameterList;

	public FunctionCallNode() {
		super();
		this.functionName = "";
		this.parameterList = new ArrayList<>();
	}

	public FunctionCallNode(String functionName) {
		super();
		this.functionName = functionName;
	}

	public FunctionCallNode(ArrayList<ParameterNode> parameterList) {
		super();
		this.parameterList = parameterList;
	}

	public FunctionCallNode(String functionName, ArrayList<ParameterNode> parameterList) {
		super();
		this.functionName = functionName;
		this.parameterList = parameterList;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public ArrayList<ParameterNode> getParameterList() {
		return parameterList;
	}

	public void setParameterList(ArrayList<ParameterNode> parameterList) {
		this.parameterList = parameterList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		StringBuilder output = new StringBuilder();

		output.append(getFunctionName() + " ");

		for (int i = 0; i < getParameterList().size(); i++) {

			if (getParameterList().get(i) instanceof VariableReferenceNode) {
				if (i != getParameterList().size() - 1 && getParameterList().get(i).isVar()) {
					output.append("var " + getParameterList().get(i) + ", ");
					
				}else if(i != getParameterList().size() - 1 && !getParameterList().get(i).isVar()){
					output.append(getParameterList().get(i) + ", ");
					
				}else if (i == getParameterList().size() - 1 && getParameterList().get(i).isVar()) {
					output.append("var " + getParameterList().get(i) + "\n");
				}else 
					output.append(getParameterList().get(i) + "\n");
			}else {
				if (i != getParameterList().size() - 1) {
					output.append(getParameterList().get(i) + ", ");
				}else 
					output.append(getParameterList().get(i) + "\n");
				
			}

		}

		return output.toString();
	}

}
