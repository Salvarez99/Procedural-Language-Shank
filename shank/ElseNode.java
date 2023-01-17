package shank;

import java.util.ArrayList;

public class ElseNode extends IfNode {

	public ElseNode(ArrayList<StatementNode> statementList) {
		super(statementList);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();

		output.append("\n\telse" + "\n\tbegin\n");

		for (int i = 0; i < getStatementList().size(); i++) {

			output.append("\t\t" + getStatementList().get(i).toString() + "\n");
		}
		output.append("\t" + "end");

		return output.toString();
	}
}
