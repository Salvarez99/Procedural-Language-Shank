package shank;

import java.util.ArrayList;
import java.util.Scanner;

public class Read extends BuiltInFunctionNode {

	public Read() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Read(boolean isVar) {
		super(isVar);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ArrayList<InterpreterDataType> IDT_List) {

		System.out.println("Read.execute(): Looking for input");
		Scanner scanner = new Scanner(System.in);

//		String value;



		for (int i = 0; i < IDT_List.size(); i++) {



			if (IDT_List.get(i) instanceof FloatDataType) {
				((FloatDataType)IDT_List.get(i)).fromString(scanner.next());

			}else if (IDT_List.get(i) instanceof IntDataType) {
				((IntDataType)IDT_List.get(i)).fromString(scanner.next());

			}else
				throw new ShankException("Read.execute(): No valid dataType");
		}

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
