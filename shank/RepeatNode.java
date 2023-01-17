package shank;

import java.util.ArrayList;

public class RepeatNode extends StatementNode {

	private BooleanExpressionNode boolExpression;
	private ArrayList<StatementNode> statementList;

	public RepeatNode(BooleanExpressionNode boolExpression, ArrayList<StatementNode> statementList) {
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

		output.append("\t" +"repeat " + "\n\tbegin\n");

		for (int i = 0; i < getStatementList().size(); i++) {

			output.append("\t\t" + getStatementList().get(i).toString() + "\n");
		}
		output.append("\t" + "end\n");
		output.append("\t" + "until " + getBoolExpression().toString());

		return output.toString();


	}

}
