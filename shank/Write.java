package shank;

import java.util.ArrayList;

public class Write extends BuiltInFunctionNode {

	public Write() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Write(boolean isVar) {
		super(isVar);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ArrayList<InterpreterDataType> IDT_List) {
		System.out.println("Write.execute(): Outputting...");
		if (!IDT_List.isEmpty()) {
			
			for (InterpreterDataType interpreterDataType : IDT_List) {
				System.out.println(interpreterDataType.toString());
			}
		}else
			throw new ShankException("Write.execute: no valid parameters given");
		
//		for (int i = 0; i < IDT_List.size(); i++) {
//			System.out.println(IDT_List.get(i));
//		}
//		System.out.println(IDT_List);
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
