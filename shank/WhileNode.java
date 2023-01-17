package shank;

import java.util.ArrayList;

public class WhileNode extends StatementNode {

	private BooleanExpressionNode boolExpression;
	private ArrayList<StatementNode> statementList;
	
	public WhileNode(BooleanExpressionNode boolExpression, ArrayList<StatementNode> statementList) {
		super();
		this.boolExpression = boolExpression;
		this.statementList = statementList;
	}

	public BooleanExpressionNode getBoolExpression() {
		return boolExpression;
	}

	public void setBoolExpression(BooleanExpressionNode boolExpression) {
		this.boolExpression = boolExpression;
	}

	public ArrayList<StatementNode> getStatementList() {
		return statementList;
	}

	public void setStatementList(ArrayList<StatementNode> statementList) {
		this.statementList = statementList;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		output.append("\t" +"while " + getBoolExpression().toString() + "\n\tbegin\n");
		
		for (int i = 0; i < getStatementList().size(); i++) {
			
				output.append("\t\t" + getStatementList().get(i).toString() + "\n");
		}
		output.append("\t" + "end");
		
		return output.toString();
	}

}
