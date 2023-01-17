package shank;

import java.util.ArrayList;

public class SquareRoot extends BuiltInFunctionNode {

	
	
	public SquareRoot() {
		super();
	}

	public SquareRoot(boolean isVar) {
		super(isVar);
	}

	@Override
	public void execute(ArrayList<InterpreterDataType> IDT_List) {

		if (IDT_List.size() == 2) {
			
			if (IDT_List.get(0) instanceof IntDataType) {
				if (IDT_List.get(1) instanceof FloatDataType) {

					float value = ((IntDataType)IDT_List.get(0)).getValue();

					float sqrt = (float) Math.sqrt(value);	

					((FloatDataType)IDT_List.get(1)).setValue(sqrt);	
				}else
					throw new ShankException("SquareRoot.execute(): 2nd IDT is not a FloatDataType");
			}else if (IDT_List.get(0) instanceof FloatDataType) {
				if (IDT_List.get(1) instanceof FloatDataType) {


					float value = ((FloatDataType)IDT_List.get(0)).getValue();

					float sqrt = (float) Math.sqrt(value);	

					((FloatDataType)IDT_List.get(1)).setValue(sqrt);	
				}else
					throw new ShankException("SquareRoot.execute(): 2nd parameter is not a FloatDataType");
			}else
				throw new ShankException("SquareRoot.execute(): 1st parameter is not a FloatDataType");
		}else
			throw new ShankException("SquareRoot.execute(): Invalid amount of parameters given. Valid amount is 2");
		
		
		


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
