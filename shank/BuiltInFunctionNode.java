package shank;

import java.util.ArrayList;

public abstract class BuiltInFunctionNode extends CallableNode {
	private boolean isVariadic;
	
	public BuiltInFunctionNode() {
		this.isVariadic = false;
	}
	
	public BuiltInFunctionNode(boolean isVariadic) {
		this.isVariadic = isVariadic;
	}
	
	public boolean isVariadic() {
		return isVariadic;
	}

	public void setVariadic(boolean isVariadic) {
		this.isVariadic = isVariadic;
	}

	public abstract void execute(ArrayList<InterpreterDataType> IDT_List);
	
	@Override
	public abstract String toString();
}
