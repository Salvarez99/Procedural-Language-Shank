package shank;

public abstract class ParameterNode {
	private boolean isVar;
	
	public ParameterNode() {
		super();
		this.isVar = false;
	}

	public ParameterNode(boolean isVar) {
		super();
		this.isVar = isVar;
	}

	public boolean isVar() {
		return isVar;
	}

	public void setVar(boolean isVar) {
		this.isVar = isVar;
	}

	@Override
	public abstract String toString();
}
