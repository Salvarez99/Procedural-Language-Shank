package shank;

import java.util.ArrayList;

public class FunctionASTNode extends CallableNode{

	private ArrayList<VariableNode> localVariableList;

	//	private ArrayList<VariableNode> constantsList;

	private ArrayList<StatementNode> statementList;

	public FunctionASTNode() {
		super();
		this.localVariableList = new ArrayList<>();
		//		this.constantsList = new ArrayList<>();
		this.statementList = new ArrayList<>();

	}

	public FunctionASTNode(Token token, ArrayList<VariableNode> parameterList, ArrayList<VariableNode> constantsList, ArrayList<VariableNode> localVariableList) {
		super.setToken(token);
		super.setParameterList(parameterList);
		this.localVariableList = localVariableList;
		//		this.constantsList = constantsList;
	}

	public ArrayList<StatementNode> getStatementList() {
		return statementList;
	}

	public void setStatementList(ArrayList<StatementNode> statementList) {
		this.statementList = statementList;
	}

	//	public ArrayList<VariableNode> getConstantsList() {
	//		return constantsList;
	//	}

	public void setConstantsList(ArrayList<VariableNode> constantsList) {
		//		this.constantsList = constantsList;
	}

	public ArrayList<VariableNode> getParameterList() {
		return super.getParameterList();
	}

	public void setParameterList(ArrayList<VariableNode> parameterList) {
		super.setParameterList(parameterList);
	}

	public ArrayList<VariableNode> getLocalVariableList() {
		return localVariableList;


	}

	public void setLocalVariableList(ArrayList<VariableNode> localVariableList) {
		this.localVariableList = localVariableList;
	}

	@Override
	public String toString() {

		StringBuilder function = new StringBuilder();
		function.append("define ");
		function.append(super.getToken().getData());
		function.append(" (");

		if(!getParameterList().isEmpty()) {

			for (int i = 0; i < this.getParameterList().size(); i++) {

				if (i != this.getParameterList().size() - 1) {

					function.append(this.getParameterList().get(i).toString() + ", ");
				}else 
					function.append(this.getParameterList().get(i).toString() + ")");

			}
		}else 
			function.append(" )");

		//		if (!(getConstantsList().isEmpty())) {
		//
		//			function.append("\nconstants\n");
		//
		//			for (int i = 0; i < getConstantsList().size(); i++) {
		//				function.append(getConstantsList().get(i).toString() + ": " + getConstantsList().get(i).getAST_Node() + "\n");
		//			}
		//
		//
		//		}

		if(!(getLocalVariableList().isEmpty())) {

			function.append("\nconstants\n");

			for (int i = 0; i < getLocalVariableList().size(); i++) {

				if (getLocalVariableList().get(i).isConstant()) {

					function.append(getLocalVariableList().get(i).toString() + "\n");
				}
			}



			function.append("\nvariables\n");

			for (int i = 0; i < getLocalVariableList().size(); i++) {
				if (!getLocalVariableList().get(i).isConstant()) {

					function.append(getLocalVariableList().get(i).toString() + "\n");
				}
			}

		}

		function.append("\nbegin\n");
		if (!(statementList.isEmpty())) {
			for (int i = 0; i < getStatementList().size(); i++) {
				function.append(getStatementList().get(i).toString() + "\n");
			}
		}
		function.append("end\n");

		return function.toString();
	}
}
