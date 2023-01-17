package shank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Interpreter {

	public static HashMap<String, CallableNode> allFunctions = new HashMap<>();


	public static float resolve(Node input, HashMap<String, InterpreterDataType> functionVariables) {
		/*
		 * a := 2
		 * b := 3
		 * c := a + b
		 * allow resolve to account for variableReferences
		 * going to need to pass the hashmap
		 * 
		 * 
		 * cant test repeat until resolve accounts for i := i + 1
		 */
		
//		System.out.println("resolve");
		float leftValue = 0;
		float rightValue = 0;

		if(input instanceof FloatNode) {
			return ((FloatNode) input).getFloat();

		}else if (input instanceof IntegerNode) {
			return ( (float) ((IntegerNode) input).getInteger());
			
		}else if (input instanceof MathOpNode) {
			MathOpNode expression = (MathOpNode) input;
			Node leftExpression = expression.getLeft();
			Node rightExpression = expression.getRight();

			if (leftExpression instanceof VariableReferenceNode && rightExpression instanceof VariableReferenceNode) {
				String leftVarName = ((VariableReferenceNode) leftExpression).getVariableName();
				String rightVarName = ((VariableReferenceNode) rightExpression).getVariableName();

				if (functionVariables.containsKey(leftVarName) && functionVariables.containsKey(rightVarName)) {
					InterpreterDataType leftVariable = functionVariables.get(leftVarName); 
					InterpreterDataType rightVariable = functionVariables.get(rightVarName); 

					if (leftVariable instanceof FloatDataType && rightVariable instanceof FloatDataType) {
						leftValue = ((FloatDataType) leftVariable).getValue();
						rightValue = ((FloatDataType) rightVariable).getValue();

					}else if (leftVariable instanceof IntDataType && rightVariable instanceof IntDataType) {
						leftValue = ((IntDataType) leftVariable).getValue();
						rightValue = ((IntDataType) rightVariable).getValue();

					}
				}
			}else if(leftExpression instanceof VariableReferenceNode && rightExpression instanceof Node){

				String leftVarName = ((VariableReferenceNode) leftExpression).getVariableName();

				if (functionVariables.containsKey(leftVarName)) {
					InterpreterDataType leftVariable = functionVariables.get(leftVarName); 

					if (leftVariable instanceof FloatDataType ) {
						leftValue = ((FloatDataType) leftVariable).getValue();
						rightValue = resolve(rightExpression, functionVariables);

					}else if (leftVariable instanceof IntDataType) {
						leftValue = ((IntDataType) leftVariable).getValue();
						rightValue = (int)resolve(rightExpression, functionVariables);
					}
				}
			}else if(leftExpression instanceof Node && rightExpression instanceof VariableReferenceNode){

				String rightVarName = ((VariableReferenceNode) rightExpression).getVariableName();

				if (functionVariables.containsKey(rightVarName)) {
					InterpreterDataType rightVariable = functionVariables.get(rightVarName); 

					if (rightVariable instanceof FloatDataType ) {
						leftValue = resolve(leftExpression, functionVariables);
						rightValue = ((FloatDataType) rightVariable).getValue();

					}else if (rightVariable instanceof IntDataType) {
						leftValue = (int)resolve(leftExpression, functionVariables);
						rightValue = ((IntDataType) rightVariable).getValue();

					}
				}
			}else {
				leftValue = resolve(expression.getLeft(), functionVariables);
				rightValue = resolve(expression.getRight(), functionVariables);
			}
		}
		switch (((MathOpNode) input).getMathOp()) {
		case PLUS:
			return leftValue + rightValue;

		case MINUS:

			return leftValue - rightValue;
		case TIMES:

			return leftValue * rightValue;
		case DIVIDE:

			return leftValue / rightValue;
		case MOD:

			return leftValue % rightValue;

		default:
			throw new ShankException("resolve(): Problem during method call");
		}
	}

	private static boolean evaluateBooleanExpression(BooleanExpressionNode booleanExpression, HashMap<String, InterpreterDataType> functionVariables) {
		/*
		 * left and right can be var references, equations
		 */
//		System.out.println("evaluateBooleanExpression");
		Node leftExpression = booleanExpression.getLeftExpression();
		Node rightExpression = booleanExpression.getRightExpression();
		Token_enum condition = booleanExpression.getCondition().getToken_enum();

		float leftValue = 0;
		float rightValue = 0;

		if (leftExpression instanceof VariableReferenceNode && rightExpression instanceof VariableReferenceNode) {
			String leftVarName = ((VariableReferenceNode) leftExpression).getVariableName();
			String rightVarName = ((VariableReferenceNode) rightExpression).getVariableName();

			if (functionVariables.containsKey(leftVarName) && functionVariables.containsKey(rightVarName)) {
				InterpreterDataType leftVariable = functionVariables.get(leftVarName); 
				InterpreterDataType rightVariable = functionVariables.get(rightVarName); 

				if (leftVariable instanceof FloatDataType && rightVariable instanceof FloatDataType) {
					leftValue = ((FloatDataType) leftVariable).getValue();
					rightValue = ((FloatDataType) rightVariable).getValue();

				}else if (leftVariable instanceof IntDataType && rightVariable instanceof IntDataType) {
					leftValue = ((IntDataType) leftVariable).getValue();
					rightValue = ((IntDataType) rightVariable).getValue();

				}
			}
		}else if(leftExpression instanceof VariableReferenceNode && rightExpression instanceof Node){

			String leftVarName = ((VariableReferenceNode) leftExpression).getVariableName();

			if (functionVariables.containsKey(leftVarName)) {
				InterpreterDataType leftVariable = functionVariables.get(leftVarName); 

				if (leftVariable instanceof FloatDataType ) {
					leftValue = ((FloatDataType) leftVariable).getValue();
					rightValue = resolve(rightExpression, functionVariables);

				}else if (leftVariable instanceof IntDataType) {
					leftValue = ((IntDataType) leftVariable).getValue();
					rightValue = (int)resolve(rightExpression, functionVariables);
				}
			}
		}else if(leftExpression instanceof Node && rightExpression instanceof VariableReferenceNode){

			String rightVarName = ((VariableReferenceNode) rightExpression).getVariableName();

			if (functionVariables.containsKey(rightVarName)) {
				InterpreterDataType rightVariable = functionVariables.get(rightVarName); 

				if (rightVariable instanceof FloatDataType ) {
					leftValue = resolve(leftExpression, functionVariables);
					rightValue = ((FloatDataType) rightVariable).getValue();

				}else if (rightVariable instanceof IntDataType) {
					leftValue = (int)resolve(leftExpression, functionVariables);
					rightValue = ((IntDataType) rightVariable).getValue();

				}
			}




		}else {
			leftValue = resolve(leftExpression, functionVariables);
			rightValue = resolve(rightExpression, functionVariables);
		}


		switch(condition) {
		case GREATERTHAN:
			return leftValue > rightValue;
		case LESSTHAN:
			return leftValue < rightValue;
		case GREATERTHANOREQUAL:
			return leftValue >= rightValue;
		case LESSTHANOREQUAL:
			return leftValue <= rightValue;
		case EQUAL:
			return leftValue == rightValue;
		case MOD:
			if (leftValue % rightValue == 0) {
				return false;
			}else {
				return true;
			}
		default:
			throw new ShankException("EvaluateBooleanExpression(): no valid case found");

		}
	}

	public static void interpretFunction(FunctionASTNode function, ArrayList<InterpreterDataType> functionParameters) {
//		System.out.println("interpretFunction");
		HashMap<String, InterpreterDataType> functionVariables = new HashMap<>();
		ArrayList<VariableNode> parameters = function.getParameterList();
		ArrayList<VariableNode> localVariables = function.getLocalVariableList();
		ArrayList<StatementNode> functionStatements = function.getStatementList();

		if (!parameters.isEmpty()) {

			for (int i = 0; i < functionParameters.size(); i++) {

				InterpreterDataType functionParameter = functionParameters.get(i);

				if (functionParameters.get(i) instanceof FloatDataType && parameters.get(i).getDataType() == Token_enum.REAL) {

					String parameterName = parameters.get(i).getVarName();
					functionVariables.put(parameterName, functionParameter);

				}else if (functionParameters.get(i) instanceof IntDataType && parameters.get(i).getDataType() == Token_enum.INTEGER) {

					String parameterName = parameters.get(i).getVarName();
					functionVariables.put(parameterName, functionParameter);
				}
			}
		}

		if (!localVariables.isEmpty()) {

			for (int i = 0; i < localVariables.size(); i++) {
				VariableNode localVariable = localVariables.get(i);

				if (localVariable.getDataType() == Token_enum.REAL) {
					String localVariableName = localVariable.getVarName();
					float value = 0.f;

					if (localVariable.getAST_Node() != null) {

						value = ((FloatNode)localVariable.getAST_Node()).getFloat();
					}

					functionVariables.put(localVariableName, new FloatDataType(value));

				}else if (localVariable.getDataType() == Token_enum.INTEGER) {

					String localVariableName = localVariable.getVarName();
					int value = 0;

					if (localVariable.getAST_Node() != null) {

						value = ((IntegerNode)localVariable.getAST_Node()).getInteger();
					}

					functionVariables.put(localVariableName, new IntDataType(value));
				}
			}
		}

		interpretBlock(functionStatements, functionVariables);
	}

	private static void interpretBlock(ArrayList<StatementNode> functionStatements, HashMap<String, InterpreterDataType> functionVariables) {
		/*
		 * Currently I am in a function definition, so between the begin and end. Looking through all the statements in the function definition
		 * Atm I am looking for function calls that are user-defined or builtin right now (CallableNodes)
		 * 
		 * functionStatements:  a list of statements within this function definition
		 * functionVariables: a hashmap of variables available to the function 
		 * 		(consist of parameters, constants, variables)
		 * 		parameters and variables should be defaulted to zero of their respected types (FloatDataType or IntDataType)
		 * 		constants 
		 * 
		 * Steps to identify if a statement is a functionCall
		 * --------------------------------------------------
		 * 1) Loop through all the statements in the functionDefinition
		 * 2) First check if the statement is an instance of a functionCallNode
		 * 3) Access the function name through the functionCallNode (current statement)
		 * 4) Use the name to look up the function in the hashmap of all the functions
		 * 5) Check to see if the function from the hashmap is a functionNode or BuiltInFunctionNode
		 * 6) If it is a BuiltInFunctionNode, check to see if it is variadic
		 * 6a) Find the matching variables from the functionCall's parameters and the currentFunctionDefinition's variables
		 * 6b) Fill the IDTList with the BIF's called variables
		 * 6c) Call execute for the specific functionCall  
		 * 7) If it is a FunctionNode
		 * 7a) Check to see if parameterList's in functionCall and the function are the same size (make sure it is not empty or null first
		 * 7b) 
		 * 
		 */
//		System.out.println("interpetBlock");
		if (!functionStatements.isEmpty()) {

			for (int i = 0; i < functionStatements.size(); i++) {

				StatementNode functionStatement = functionStatements.get(i);
				CallableNode function; //found function from allFunctions

				if (functionStatement != null) {
					ArrayList<InterpreterDataType> IDTList = new ArrayList<>();

					if (functionStatement instanceof FunctionCallNode) {
						FunctionCallNode functionCall = (FunctionCallNode) functionStatement;
						function = allFunctions.get(functionCall.getFunctionName());
						ArrayList<ParameterNode> functionCallParameters = functionCall.getParameterList();
						//						ArrayList<VariableNode> functionParameters = function.getParameterList();

						if (!functionCallParameters.isEmpty()) {
							if (function instanceof BuiltInFunctionNode) {
								//Predefined function
								//Note: add way to set isVariadic to true for specified BIF's
								if (((BuiltInFunctionNode) function).isVariadic()) {
									//Variadic: can have any number of parameters
									//BIF is likely to only be Read or Write
									//These functions do not take parameters, we just send them the IDTList

									IDTList = makeBuiltInFunctionIDT_List(functionCallParameters, functionVariables);

									if (function instanceof Read) {

										((BuiltInFunctionNode) function).execute(IDTList);
									}else if (function instanceof Write) {

										((BuiltInFunctionNode) function).execute(IDTList);
									}
								}else {//!isVariadic: IntegerToReal, RealToInteger, GetRandom, SqaureRoot

									IDTList = makeBuiltInFunctionIDT_List(functionCallParameters, functionVariables);

									if (function instanceof IntegerToReal) {

										((BuiltInFunctionNode) function).execute(IDTList);
									}else if (function instanceof RealToInteger) {

										((BuiltInFunctionNode) function).execute(IDTList);
									}else if (function instanceof GetRandom) {

										((BuiltInFunctionNode) function).execute(IDTList);
									}else if (function instanceof SquareRoot) {

										((BuiltInFunctionNode) function).execute(IDTList);
									}
								}
							}else if (function instanceof FunctionASTNode) {
								//User defined function


								/*
								 * Steps for UserDefined Functions
								 * 1) Make sure parameters in functionCall and function matches
								 * 2) Look up parameters in current function's variableMap (functionVariables)
								 * 3) Call interpretFunction with the functionASTNode and the functionVariable
								 */

								FunctionASTNode userFunction = (FunctionASTNode) function;


								IDTList = makeBuiltInFunctionIDT_List(functionCallParameters, functionVariables);
								interpretFunction(userFunction, IDTList);


							}
						}
					}else if(functionStatement instanceof AssignmentNode){
						AssignmentNode assignment = (AssignmentNode) functionStatement;
						String targetVariableName = assignment.getTarget().getVariableName();
						if (functionVariables.containsKey(targetVariableName)) {
							InterpreterDataType targetVariable = functionVariables.get(targetVariableName);

							if (targetVariable instanceof FloatDataType) {
								float value = resolve(assignment.getExpression(), functionVariables);
								((FloatDataType) targetVariable).setValue(value);
								functionVariables.put(targetVariableName, targetVariable);

							}else if(targetVariable instanceof IntDataType) {
								int value = (int)resolve(assignment.getExpression(), functionVariables);
								((IntDataType) targetVariable).setValue(value);
								functionVariables.put(targetVariableName, targetVariable);

							}
						}



					}else if(functionStatement instanceof WhileNode){
						WhileNode whileNode = (WhileNode) functionStatement;
						BooleanExpressionNode boolExpression = whileNode.getBoolExpression();
						ArrayList<StatementNode> whileStatements = whileNode.getStatementList();

						while (evaluateBooleanExpression(boolExpression, functionVariables)) {
							interpretBlock(whileStatements, functionVariables);
						}

					}else if(functionStatement instanceof RepeatNode){
						RepeatNode repeatNode = (RepeatNode) functionStatement;
						BooleanExpressionNode boolExpression = repeatNode.getBoolExpression();
						ArrayList<StatementNode> repeatStatements = repeatNode.getStatementList();

						do{
							interpretBlock(repeatStatements, functionVariables);
						}while (!evaluateBooleanExpression(boolExpression, functionVariables)) ;


					}else if(functionStatement instanceof IfNode){

						IfNode ifNode = (IfNode) functionStatement;
						ArrayList<StatementNode> ifStatements = ifNode.getStatementList();
						BooleanExpressionNode boolExpression = ifNode.getBoolExpression();
						//						ifNode.getElsIfOrElse();

						do {


							if (boolExpression == null || evaluateBooleanExpression(boolExpression, functionVariables)) {
								interpretBlock(ifStatements, functionVariables);
							}

							ifNode = ifNode.getElsIfOrElse();

							if (ifNode != null) {
								boolExpression = ifNode.getBoolExpression();
								ifStatements = ifNode.getStatementList();
							}


						} while (ifNode != null);

					}else if(functionStatement instanceof ForNode){
						ForNode forNode = (ForNode) functionStatement;
						Node start = forNode.getStart();
						Node end = forNode.getEnd();
						String iterator = forNode.getIterator().getVariableName();
						ArrayList<StatementNode> forStatements = forNode.getStatementList();

						if (functionVariables.containsKey(iterator)) {


							if (start instanceof IntegerNode && end instanceof IntegerNode) {

								int from = ((IntegerNode) start).getInteger();
								int to = ((IntegerNode) end).getInteger();

								if (from < to) {
									for (int j = from; j <= to; j++) {
										functionVariables.put(iterator, new IntDataType(j));
										interpretBlock(forStatements, functionVariables);
									}
								}else if (from > to) {
									for (int j = from; j >= to; j--) {
										functionVariables.put(iterator, new IntDataType(j));
										interpretBlock(forStatements, functionVariables);
									}
								}
							}else 
								throw new ShankException("interpretBlock(): from and to values are not integers");
						}




					}
				}//statement doesn't exist, jump goes to here safely skip over it
			}//for loop for statementList ends here
		}//there are no statements in the functionDefinition
	}

	public static void addBuiltinFunctions() {
		Read readNode = new Read();
		Write writeNode = new Write();
		SquareRoot squareRootnode = new SquareRoot();
		GetRandom getRandomNode = new GetRandom();
		IntegerToReal integerToRealNode = new IntegerToReal();
		RealToInteger realToIntegerNode = new RealToInteger();

		readNode.setVariadic(true);
		writeNode.setVariadic(true);

		allFunctions.put("read", readNode);
		allFunctions.put("write", writeNode);
		allFunctions.put("squareRoot", squareRootnode);
		allFunctions.put("getRandom", getRandomNode);
		allFunctions.put("integerToReal", integerToRealNode);
		allFunctions.put("realToInteger", realToIntegerNode);

	}

	private static ArrayList<InterpreterDataType> makeBuiltInFunctionIDT_List(ArrayList<ParameterNode> functionCallParameters, HashMap<String, InterpreterDataType> functionVariables) {

		ArrayList<InterpreterDataType> IDTList = new ArrayList<>();
		for (int j = 0; j < functionCallParameters.size(); j++) {


			Node functionCallParameter = (Node) functionCallParameters.get(j);
			String varRefName = ((VariableReferenceNode)functionCallParameters.get(j)).getVariableName();

			if (functionCallParameter instanceof FloatNode) {
				IDTList.add(new FloatDataType(functionCallParameter.getToken()));

			}else if (functionCallParameter instanceof IntegerNode) {
				IDTList.add(new IntDataType(functionCallParameter.getToken()));

			}else {//checks to see if it is a variableReference 

				if (functionVariables.containsKey(varRefName)) {
					InterpreterDataType IDT = functionVariables.get(varRefName);

					if (!IDTList.contains(IDT)) {
						IDTList.add(IDT);
					}
				}
			}
		}
		return IDTList;
	}
}
