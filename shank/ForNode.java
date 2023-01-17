package shank;

import java.util.ArrayList;

public class ForNode extends StatementNode {

	private Node start;
	private Node end;
	private VariableReferenceNode iterator;
	private ArrayList<StatementNode> statementList;

	public ForNode(Node start, Node end, VariableReferenceNode iterator, ArrayList<StatementNode> statementList) {
		super();
		this.start = start;
		this.end = end;
		this.iterator = iterator;
		this.statementList = statementList;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public VariableReferenceNode getIterator() {
		return iterator;
	}

	public void setIterator(VariableReferenceNode iterator) {
		this.iterator = iterator;
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

		output.append("\t" +"for " + getIterator() + " from " + getStart() + " to " + getEnd() + "\n\tbegin\n");

		for (int i = 0; i < getStatementList().size(); i++) {

			output.append("\t\t" + getStatementList().get(i).toString() + "\n");
		}
		output.append("\t" + "end");

		return output.toString();
	}

}
