package shank;

import java.util.ArrayList;

public class IfNode extends StatementNode {

	private BooleanExpressionNode boolExpression;
	private ArrayList<StatementNode> statementList;
	private IfNode elsIfOrElse;
	
	//constructor for single if statement
	public IfNode(BooleanExpressionNode boolExpression, ArrayList<StatementNode> statementList) {
		super();
		this.boolExpression = boolExpression;
		this.statementList = statementList;
	}

	//constructor for nested if statement
	public IfNode(BooleanExpressionNode boolExpression, ArrayList<StatementNode> statementList, IfNode elsIfOrElse) {
		super();
		this.boolExpression = boolExpression;
		this.statementList = statementList;
		this.elsIfOrElse = elsIfOrElse;
	}
	
	//constructor for an else statement
	public IfNode(ArrayList<StatementNode> statementList) {
		super();
		this.boolExpression = null;
		this.statementList = statementList;
		this.elsIfOrElse = null;
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

	public IfNode getElsIfOrElse() {
		return elsIfOrElse;
	}

	public void setElsIfOrElse(IfNode elsIfOrElse) {
		this.elsIfOrElse = elsIfOrElse;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		IfNode currentIf = this;

		output.append("\tif " + currentIf.getBoolExpression() + " then" +"\n\tbegin\n");

		for (int i = 0; i < currentIf.getStatementList().size(); i++) {

			output.append("\t\t" + currentIf.getStatementList().get(i).toString() + "\n");
		}
		output.append("\t" + "end");
		
		//Loop for elsIf outputs
		currentIf = currentIf.getElsIfOrElse();
		while(currentIf != null) {
			
			if (currentIf instanceof ElseNode) {
				output.append("\n\t" + (ElseNode)currentIf);

				currentIf = currentIf.getElsIfOrElse();

			}else {

				output.append("\n\n\telsif " + currentIf.getBoolExpression() + " then" +"\n\tbegin\n");

				for (int i = 0; i < currentIf.getStatementList().size(); i++) {

					output.append("\t\t" + currentIf.getStatementList().get(i).toString() + "\n");
				}
				output.append("\t" + "end");
				currentIf = currentIf.getElsIfOrElse();
			}
		}
		
		

		return output.toString();
	}

}
