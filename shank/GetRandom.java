package shank;

import java.util.ArrayList;
import java.util.Random;

public class GetRandom extends BuiltInFunctionNode {

	public GetRandom() {
		super();
	}

	public GetRandom(boolean isVar) {
		super(isVar);
	}

	@Override
	public void execute(ArrayList<InterpreterDataType> IDT_List) {
		if (IDT_List.size() == 1) {
		Random randomNumber = new Random();
		int randomValue = randomNumber.nextInt();
		
		if(IDT_List.get(0) instanceof IntDataType) {
			
			((IntDataType)IDT_List.get(0)).setValue(randomValue);
		}else if (IDT_List.get(0) instanceof FloatDataType) {
			
			((FloatDataType)IDT_List.get(0)).setValue(randomValue);
		}
		
		
		
		
	}else
		throw new ShankException("GetRandom.execute(): Invalid amount of parameters given. Valid amount is 1");
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
