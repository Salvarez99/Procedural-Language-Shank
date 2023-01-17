package shank;

import java.util.ArrayList;

public class IntegerToReal extends BuiltInFunctionNode {

	public IntegerToReal() {
		super();
	}

	public IntegerToReal(boolean isVar) {
		super(isVar);
	}

	@Override
	public void execute(ArrayList<InterpreterDataType> IDT_List) {
		if (IDT_List.size() == 2) {
			if (IDT_List.get(0) instanceof IntDataType) {
				if (IDT_List.get(1) instanceof FloatDataType) {

					float value = (float)((IntDataType) IDT_List.get(0)).getValue();
					((FloatDataType) IDT_List.get(1)).setValue(value);


				}else
					throw new ShankException("IntegerToReal.execute(): 2nd parameter is not a FloatDataType");
			}else
				throw new ShankException("IntegerToReal.execute(): 1nd parameter is not an IntDataType");
		}else
			throw new ShankException("IntegerToReal.execute(): Invalid amount of parameters given. Valid amount is 2");
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
