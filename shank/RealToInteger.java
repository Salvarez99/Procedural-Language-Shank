package shank;

import java.util.ArrayList;

public class RealToInteger extends BuiltInFunctionNode {

	public RealToInteger() {
		super();
	}

	public RealToInteger(boolean isVar) {
		super(isVar);
	}

	@Override
	public void execute(ArrayList<InterpreterDataType> IDT_List) {
		if (IDT_List.size() == 2) {
		if (IDT_List.get(0) instanceof FloatDataType) {
			if (IDT_List.get(1) instanceof IntDataType) {

				int value = (int)((FloatDataType) IDT_List.get(0)).getValue();
				((IntDataType) IDT_List.get(1)).setValue(value);
				

			}else
				throw new ShankException("RealToInteger.execute(): 2nd parameter is not a IntDataType");
		}else
				throw new ShankException("RealToInteger.execute(): 1nd parameter is not an FloatDataType");
		}else
			throw new ShankException("RealToInteger.execute(): Invalid amount of parameters given. Valid amount is 2");

	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();

		output.append(super.getFunctionName() + " ");

		for (ParameterNode parameter : super.getParameterList()) {
			System.out.println(parameter + " ");
		}

		return output.toString();
	}

}
