package shank;

import java.util.ArrayList;

public class Parser {

	private ArrayList<Token> tokenList;

	public Parser() {
		tokenList = new ArrayList<>();
	}

	public Parser(ArrayList<Token> tokenList) {
		this.tokenList = tokenList;
	}

	public Node parse() {
//		System.out.println("parse");
		//Removes end of line tokens 
		removeEOL();

		//Test expressions
		//		Node expression = expression();
		//		return expression;

		//Test functionDefinitions
		Node functionNode = functionDefinition();
		
		while(functionNode != null) {
			
			String functionName = ((FunctionASTNode)functionNode).getFunctionName();
			CallableNode functionCall = (CallableNode)functionNode;
			
			Interpreter.allFunctions.put(functionName, functionCall);

			functionNode = functionDefinition();
		}
		
		functionNode = Interpreter.allFunctions.get("start");
		
		return functionNode;
	}

	private Node expression() {
//		System.out.println("expression");
		removeEOL();

		MathOpNode expression = new MathOpNode();
		Node left = term();
		Node right = null;

		Token plusToken = matchAndRemove(Token_enum.PLUS);
		Token minusToken = matchAndRemove(Token_enum.MINUS);

		if (plusToken != null) {

			right = term();
			expression = new MathOpNode(left, plusToken, right);

			/*
			 * peek to see if there is more in the equation
			 */
			if (peekEnum() == Token_enum.PLUS) {

				left = expression;
				plusToken = matchAndRemove(Token_enum.PLUS);
				right = expression();
				expression = new MathOpNode(left, plusToken, right);

			}else if (peekEnum() == Token_enum.MINUS) {

				left = expression;
				plusToken = matchAndRemove(Token_enum.MINUS);
				right = expression();
				expression = new MathOpNode(left, minusToken, right);

			}
		}else if (minusToken != null) {

			right = term();
			expression = new MathOpNode(left, minusToken, right);

			/*
			 * peek to see if there is more in the equation
			 */
			if (peekEnum() == Token_enum.PLUS) {

				left = expression;
				plusToken = matchAndRemove(Token_enum.PLUS);
				right = expression();
				expression = new MathOpNode(left, plusToken, right);


			}else if (peekEnum() == Token_enum.MINUS) {

				left = expression;
				plusToken = matchAndRemove(Token_enum.MINUS);
				right = expression();
				expression = new MathOpNode(left, minusToken, right);

			}
		}else 
			return left;

		removeEOL();
		return expression;
	}

	private Node term() {

		//remember to change return later
		MathOpNode term = null;
		Node left = factor();
		Node right = null;

		Token timeToken = matchAndRemove(Token_enum.TIMES);
		Token divideToken = matchAndRemove(Token_enum.DIVIDE);
		Token modToken = matchAndRemove(Token_enum.MOD);

		if (timeToken != null) {

			right = term();
			term = new MathOpNode(left, timeToken, right);

			/*
			 * peek to see if there is more in the equation
			 */
			if (peekEnum() == Token_enum.TIMES) {

				left = term;
				timeToken = matchAndRemove(Token_enum.TIMES);
				right = expression();
				term = new MathOpNode(left, timeToken, right);


			}else if (peekEnum() == Token_enum.DIVIDE) {

				left = term;
				//changed timesToken -> divideToken (should check if math is still right
				divideToken = matchAndRemove(Token_enum.DIVIDE);
				right = expression();
				term = new MathOpNode(left, divideToken, right);

			}else if (peekEnum() == Token_enum.MOD) {

				left = term;
				modToken = matchAndRemove(Token_enum.MOD);
				right = expression();
				term = new MathOpNode(left, modToken, right);

			}


		}else if(divideToken != null) {

			right = term();
			term = new MathOpNode(left, divideToken, right);

			/*
			 * peek to see if there is more in the equation
			 */
			if (peekEnum() == Token_enum.TIMES) {
				
				left = term;
				timeToken = matchAndRemove(Token_enum.TIMES);
				right = expression();
				term = new MathOpNode(left, timeToken, right);


			}else if (peekEnum() == Token_enum.DIVIDE) {

				left = term;
				//changed timesToken -> divideToken (should check if math is still right
				divideToken = matchAndRemove(Token_enum.DIVIDE);
				right = expression();
				term = new MathOpNode(left, divideToken, right);

			}else if (peekEnum() == Token_enum.MOD) {

				left = term;
				modToken = matchAndRemove(Token_enum.MOD);
				right = expression();
				term = new MathOpNode(left, modToken, right);

			}


		}else if(modToken != null) {
			
			right = term();
			term = new MathOpNode(left, modToken, right);

			/*
			 * peek to see if there is more in the equation
			 */
			if (peekEnum() == Token_enum.TIMES) {

				left = term;
				timeToken = matchAndRemove(Token_enum.TIMES);
				right = expression();
				term = new MathOpNode(left, timeToken, right);


			}else if (peekEnum() == Token_enum.DIVIDE) {

				left = term;
				//changed timesToken -> divideToken (should check if math is still right
				divideToken = matchAndRemove(Token_enum.DIVIDE);
				right = expression();
				term = new MathOpNode(left, divideToken, right);

			}else if (peekEnum() == Token_enum.MOD) {

				left = term;
				modToken = matchAndRemove(Token_enum.MOD);
				right = expression();
				term = new MathOpNode(left, modToken, right);

			}
		}else 
			return left;

		return term;
	}

	private Node factor() {

		Node factor = null;
		Token numToken = matchAndRemove(Token_enum.NUMBER);
		Token identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
		
		if (identifierToken != null) {
			VariableReferenceNode varReference = new VariableReferenceNode(identifierToken);
			return varReference;
		}

		if (numToken != null) {

			if (isFloat(numToken)) {
				FloatNode floatNode = new FloatNode(numToken);
				return floatNode;

			}else if (!isFloat(numToken)) {
				IntegerNode integerNode = new IntegerNode(numToken);
				return integerNode;

			}
		}else{
			Token openToken = matchAndRemove(Token_enum.LPAREN);
			if (openToken != null) {
				factor = expression();
				Token closeToken = matchAndRemove(Token_enum.RPAREN);
				return factor;
			}

		}
		return null;
	}
	
	private FunctionCallNode functionCall() {
		FunctionCallNode functionCall = null;
		String functionName;
		ArrayList<ParameterNode> parameterList = new ArrayList<>();
		
		//Parameters are either a variableReferenceNode or a Node
		
		/*
		 * FunctionCall
		 * IDENTIFIER(functName) IDENTIFIER
		 * IDENTIFIER(functName) NUMBER
		 * IDENTIFIER(functName) VAR IDENTIFIER
		 */
		
		Token functionNameToken = matchAndRemove(Token_enum.IDENTIFIER);
		
		if (functionNameToken != null) {
			
			functionName = functionNameToken.getData();
			Token commaToken = matchAndRemove(Token_enum.COMMA);
			
			do {
				Token identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
				
				if (identifierToken != null) {
					parameterList.add(new VariableReferenceNode(identifierToken, false));
					
				}else if (peekEnum() == Token_enum.NUMBER) {
					Token numberToken = matchAndRemove(Token_enum.NUMBER);
					
					if (isFloat(numberToken)) {
						parameterList.add(new FloatNode(numberToken, false));
						
					}else if (!isFloat(numberToken)) {
						parameterList.add(new IntegerNode(numberToken, false));
						
					}
				}else if (peekEnum() == Token_enum.VAR) {
					matchAndRemove(Token_enum.VAR);
					identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
					
					if (identifierToken != null) {
						parameterList.add(new VariableReferenceNode(identifierToken, true));
						
					}else
						throw new ShankException("functionCall(): No IDENTIFER token after VAR token");
				}
				commaToken = matchAndRemove(Token_enum.COMMA);
		
			} while (commaToken != null);
			
			functionCall = new FunctionCallNode(functionName, parameterList);
			
		}
		
		return functionCall;
	}

	private Node functionDefinition() {
		
		/*
		 * 
		 * 
		 */
		
		
		
		
		if (!tokenList.isEmpty()) {
			
		removeEOL();
		FunctionASTNode functionNode = new FunctionASTNode();
		ArrayList<VariableNode> parameterList = new ArrayList<>();
		ArrayList<VariableNode> constantList = new ArrayList<>();
		ArrayList<VariableNode> variableList = new ArrayList<>();
		ArrayList<StatementNode> statementList = new ArrayList<>();


		Token defineToken = matchAndRemove(Token_enum.DEFINE);

		if (defineToken != null) {

			//Function Name
			Token identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
			if (identifierToken != null) {

				//setting function name for now
				functionNode.setFunctionName(identifierToken);
				functionNode.setToken(identifierToken);

				Token lParenToken = matchAndRemove(Token_enum.LPAREN);
				if (lParenToken != null) {

					//call variables() on parameterList
					parameterList = variables();


					//identifier colon integer semicolon
					/*
					 * do while EOLToken is equal to null
					 * -if IDENT
					 * --if COL
					 * ---if DATATYPE
					 * ----if SEMI != null
					 * ----else if RPAREN != null
					 */

					//					Token eolToken = matchAndRemove(Token_enum.EOL);
					//					do {
					//						//Variable Name
					//						identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
					//						if (identifierToken != null) {
					//
					//							Token colonToken = matchAndRemove(Token_enum.COLON);
					//							if (colonToken != null) {
					//
					//								//DataTypes: Integer || Real
					//								Token integerToken = matchAndRemove(Token_enum.INTEGER);
					//								Token realToken = matchAndRemove(Token_enum.REAL);
					//								if (integerToken != null) {
					//
					//									parameterNode = new VariableNode(identifierToken, Token_enum.INTEGER, false);
					//									parameterList.add(parameterNode);
					//
					//
					//									Token semiToken = matchAndRemove(Token_enum.SEMICOLON);
					//									Token rParenToken = matchAndRemove(Token_enum.RPAREN);
					//									if (semiToken != null) {
					//
					//										eolToken = matchAndRemove(Token_enum.EOL);
					//
					//									}else if(rParenToken != null) {
					//
					//										eolToken = matchAndRemove(Token_enum.EOL);
					//
					//									}else
					//										throw new ShankException("functionDefinition(): No SEMICOLON or RPAREN token found");
					//
					//								}else if(realToken != null){
					//
					//									parameterNode = new VariableNode(identifierToken, Token_enum.REAL, false);
					//									parameterList.add(parameterNode);
					//
					//									Token semiToken = matchAndRemove(Token_enum.SEMICOLON);
					//									Token rParenToken = matchAndRemove(Token_enum.RPAREN);
					//									if (semiToken != null) {
					//
					//										eolToken = matchAndRemove(Token_enum.EOL);
					//
					//									}else if(rParenToken != null) {
					//
					//										eolToken = matchAndRemove(Token_enum.EOL);
					//
					//									}else
					//										throw new ShankException("functionDefinition(): No SEMICOLON or RPAREN token found");
					//
					//
					//								}else
					//									throw new ShankException("functionDefinition(): No INTEGER or REAL token found");
					//
					//							}else
					//								throw new ShankException("functionDefinition(): No COLON token found");
					//						}else
					//							throw new ShankException("functionDefinition(): No IDENTIFIER token found");
					//					}while(eolToken == null);

				}else
					throw new ShankException("functionDefinition(): No LPAREN token found");				

			}else
				throw new ShankException("functionDefinition(): No IDENTIFIER token found");

		}else
			throw new ShankException("functionDefinition(): No DEFINE token found");

		removeEOL();
		constantList = constants();
		removeEOL();

		if (matchAndRemove(Token_enum.VARIABLES) != null) {
			variableList = variables();
			removeEOL();
		}

		if (peekEnum() == Token_enum.BEGIN) {
			statementList = statements();

		}
		functionNode.setLocalVariableList(constantList);
		functionNode.getLocalVariableList().addAll(variableList);
		functionNode.setParameterList(parameterList);
		functionNode.setStatementList(statementList);
		//not needed anymore; localVariableList now consist of constants and variables
//		functionNode.setConstantsList(constantList);
//		functionNode.setLocalVariableList(variableList);


		return functionNode;
		}else 
			return null;
	}

	private ArrayList<StatementNode> statements() {
		ArrayList<StatementNode> statementList = new ArrayList<>();
		StatementNode statementNode;

		removeEOL();
		if (matchAndRemove(Token_enum.BEGIN) != null) {
			removeEOL();

			//remember to remove spaces before and after in statement();
			statementNode = statement();
			while (statementNode != null) {

				statementList.add(statementNode);
				statementNode = statement();

			}
			removeEOL();

			if (matchAndRemove(Token_enum.END) != null) {
				removeEOL();
				return statementList;
			}else
				throw new ShankException("statements(): No END token found");

		}else {
			System.out.println("statements(): No BEGIN token found");
			//throw new ShankException("statements(): No BEGIN token found");
		}
		return null;
	}

	private StatementNode statement() {
		removeEOL();
		StatementNode statement = assignment();
		
		if (statement == null) {
			if ((statement = whileStatement()) == null) {

				if ((statement = forStatement()) == null) {

					if ((statement = ifStatement()) == null) {

						if ((statement = repeatStatement()) == null) {

							if ((statement = functionCall()) == null) {
								return null;
							}

						}
					}
				}
			}
		}
		removeEOL();
		
		return statement;
	}

	private AssignmentNode assignment() {

//		System.out.println("assignment");
		Node expression;
		AssignmentNode assignmentNode;
		removeEOL();

		//peek instead of matching
		if (peekEnum() == Token_enum.IDENTIFIER) {

			Token identfierToken = matchAndRemove(Token_enum.IDENTIFIER);
			if (matchAndRemove(Token_enum.ASSIGNMENT) != null) {

				expression = expression();

				if (expression != null) {
					assignmentNode = new AssignmentNode(new VariableReferenceNode(identfierToken), expression);
					removeEOL();
					return assignmentNode;					
				}else 
					throw new ShankException("assignment(): No ASSIGNMENT token found");
			}else
				tokenList.add(0, identfierToken);
		}
		return null;
	}

	private ArrayList<VariableNode> constants() {
		ArrayList<VariableNode> constantList = new ArrayList<>();
		Token constantToken = matchAndRemove(Token_enum.CONSTANTS);
		if (constantToken != null) {
			removeEOL();
			constantList = processConstants();
		}


		return constantList;
	}

	private ArrayList<VariableNode> processConstants(){
		ArrayList<VariableNode> constantList = new ArrayList<>();
		VariableNode varNode = new VariableNode();

		Token identifierToken = matchAndRemove(Token_enum.IDENTIFIER);

		do {
			if (identifierToken != null) {

				Token equalToken = matchAndRemove(Token_enum.EQUAL);
				if (equalToken != null) {

					Token numberToken = matchAndRemove(Token_enum.NUMBER);
					if (numberToken != null) {

						if (isFloat(numberToken)) {
							FloatNode floatNode = new FloatNode(numberToken);
							varNode = new VariableNode(identifierToken, floatNode, Token_enum.REAL, true);
						}else {
							IntegerNode integerNode = new IntegerNode(numberToken);
							varNode = new VariableNode(identifierToken, integerNode, Token_enum.INTEGER, true);

						}

						Token eolToken = removeEOL();
						if (eolToken != null) {
							constantList.add(varNode);
						}

					}else
						throw new ShankException("processConstants(): No NUMBER token found");

				}else
					throw new ShankException("processConstants(): No EQUAL token found");

			}else
				throw new ShankException("processConstants(): No IDENTIFIER token found");

			removeEOL();
			constantList.add(varNode);
			identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
		}while(identifierToken != null);

		return constantList;
	}

	private ArrayList<VariableNode> variables(){

		ArrayList<VariableNode> variableList = new ArrayList<>();
		int varCounter = 0;
		removeEOL();

		while(matchAndRemove(Token_enum.EOL) == null && matchAndRemove(Token_enum.RPAREN) == null && !tokenList.isEmpty() && peekEnum() != Token_enum.BEGIN) {

			while(matchAndRemove(Token_enum.COLON) == null) {
				
				if (matchAndRemove(Token_enum.VAR) != null) {
					Token identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
					if (identifierToken != null) {
						variableList.add(new VariableNode(identifierToken, true));
						varCounter++;
						matchAndRemove(Token_enum.COMMA);
					}else
						throw new ShankException("variables(): no IDENTIFIER token found");
				}else {
					Token identifierToken = matchAndRemove(Token_enum.IDENTIFIER);
					if (identifierToken != null) {
						variableList.add(new VariableNode(identifierToken));
						varCounter++;
						matchAndRemove(Token_enum.COMMA);
					}else
						throw new ShankException("variables(): no IDENTIFIER token found");
				}
			}

			if (matchAndRemove(Token_enum.REAL) != null) {

				for (int i = variableList.size() - 1; i >= variableList.size() - varCounter; i--) {
					variableList.get(i).setDataType(Token_enum.REAL);
				}
				varCounter = 0;
			}else if (matchAndRemove(Token_enum.INTEGER) != null) {

				for (int i = variableList.size() - 1; i >= variableList.size() - varCounter; i--) {
					variableList.get(i).setDataType(Token_enum.INTEGER);
				}
				varCounter = 0;
			}else 
				throw new ShankException("variables(): no valid DATATYPE token found");

			matchAndRemove(Token_enum.SEMICOLON);
			removeEOL();

		}

		return variableList;
	}

	private BooleanExpressionNode booleanExpression() {
		/*
		 * EXPRESSION COMPARISON EXPRESSION
		 * IDENTIFIER COMPARISON IDENTIFIER 
		 * left and right is handled by expression()
		 */
		ArrayList<Token_enum> comparisonOperators = new ArrayList<>();
		comparisonOperators.add(Token_enum.GREATERTHAN);
		comparisonOperators.add(Token_enum.LESSTHAN);
		comparisonOperators.add(Token_enum.GREATERTHANOREQUAL);
		comparisonOperators.add(Token_enum.LESSTHANOREQUAL);
		comparisonOperators.add(Token_enum.EQUAL);
		comparisonOperators.add(Token_enum.NOTEQUAL);
		comparisonOperators.add(Token_enum.MOD);


		Node leftExpression;
		Node rightExpression;
		BooleanExpressionNode boolExpression;
		Token comparisonToken;
		Token_enum peekCompareToken;
		
		leftExpression = expression();
		peekCompareToken = peekEnum();
		
		if (leftExpression != null) {
			for (int i = 0; i < comparisonOperators.size(); i++) {
				
				comparisonToken = matchAndRemove(comparisonOperators.get(i));

				if (comparisonToken != null && comparisonToken.getToken_enum() == peekCompareToken) {
					rightExpression = expression();

					if (rightExpression != null) {
						boolExpression = new BooleanExpressionNode(leftExpression, comparisonToken, rightExpression);
						return boolExpression;

					}else
						throw new ShankException("booleanExpression(): No right expression found");
				}
			}
		}else
			throw new ShankException("booleanExpression(): No left expression found");

		return null;
	}

	private WhileNode whileStatement() {
		/*
		 * WHILE BOOLEANEXPRESSION EOL
		 * BEGIN EOL
		 * STATEMENTS EOL
		 * END EOL
		 */
		BooleanExpressionNode boolExpression = new BooleanExpressionNode();
		ArrayList<StatementNode> statementList = new ArrayList<>();
		WhileNode whileNode = null;

		if (matchAndRemove(Token_enum.WHILE) != null) {
			boolExpression = booleanExpression();
			removeEOL();

			if (boolExpression != null) {
				statementList = statements();
				whileNode = new WhileNode(boolExpression, statementList);
				removeEOL();
				return whileNode;

			}else
				throw new ShankException("whileStatement(): No booleanExpression found");

		}
		return whileNode;
	}

	private RepeatNode repeatStatement() {
		/*
		 * REPEAT EOL
		 * BEGIN EOL
		 * STATEMENTS EOL
		 * END EOL
		 * UNTIL BOOLEANEXPRESSION EOL
		 */
		BooleanExpressionNode boolExpression = new BooleanExpressionNode();
		ArrayList<StatementNode> statementList = new ArrayList<>();
		RepeatNode repeatNode = null;

		if (matchAndRemove(Token_enum.REPEAT) != null) {
			removeEOL();
			statementList = statements();

			if (matchAndRemove(Token_enum.UNTIL) != null) {
				boolExpression = booleanExpression();
				if (boolExpression != null) {
					repeatNode = new RepeatNode(boolExpression, statementList);
					removeEOL();
					return repeatNode;

				}else
					throw new ShankException("repeatStatement(): No booleanExpression found");

			}else
				throw new ShankException("repeatStatement(): No UNTIL token found");

		}
		return repeatNode;
	}

	private ForNode forStatement() {
		/*
		 * Number must be an integer, check if number is an integer if not crash
		 * FOR IDENTIFIER FROM NUMBER TO NUMBER EOL
		 * BEGIN EOL 
		 * STATEMENTS EOL
		 * END EOL
		 */
		ArrayList<StatementNode> statementList = new ArrayList<>();
		VariableReferenceNode iterator;
		IntegerNode start;
		IntegerNode end;
		ForNode forNode = null;

		if (matchAndRemove(Token_enum.FOR) != null) {
			Token identiferToken = matchAndRemove(Token_enum.IDENTIFIER);

			if (identiferToken != null) {
				iterator = new VariableReferenceNode(identiferToken);

				if (matchAndRemove(Token_enum.FROM) != null) {
					Token startingIndex = matchAndRemove(Token_enum.NUMBER);

					if (startingIndex != null) {

						if (!isFloat(startingIndex)) {
							start = new IntegerNode(startingIndex);

							if (matchAndRemove(Token_enum.TO) != null) {

								Token endingIndex = matchAndRemove(Token_enum.NUMBER);

								if (endingIndex != null) {
									removeEOL();

									if (!isFloat(endingIndex)) {
										end = new IntegerNode(endingIndex);
										statementList = statements();
										forNode = new ForNode(start, end, iterator, statementList);

										return forNode;
									}else
										throw new ShankException("forStatement(): Ending index is not an integer");

								}else
									throw new ShankException("forStatement(): No ending NUMBER token found");

							}else
								throw new ShankException("forStatement(): No TO token found");

						}else
							throw new ShankException("forStatement(): Starting index is not an integer");

					}else
						throw new ShankException("forStatement(): No starting NUMBER token found");

				}else
					throw new ShankException("forStatement(): No FROM token found");

			}else
				throw new ShankException("forStatement(): No IDENTIFIER token found");

		}
		return forNode;
	}

	private IfNode ifStatement() {
		/*
		 * Single if statement
		 * IF BOOLEANEXPRESSION THEN EOL
		 * BEGIN EOL
		 * STATEMENTS EOL
		 * END EOL
		 * 
		 * Nested if statement
		 * 	Elsif can repeat many times 
		 * 
		 * IF BOOLEANEXPRESSION THEN EOL
		 * BEGIN EOL
		 * STATEMENTS EOL
		 * END EOL
		 * 
		 * ELSIF BOOLEANEXPRESSION THEN EOL
		 * BEGIN EOL
		 * STATEMENTS EOL
		 * END EOL
		 * 
		 * ELSE EOL
		 * BEGIN EOL
		 * STATEMENTS EOL
		 * END EOL
		 * 
		 * May need to check if the next elsif is not null before moving the current elsif (figure out during debugging
		 * 
		 */
		BooleanExpressionNode boolExpression = new BooleanExpressionNode();
		ArrayList<StatementNode> statementList = new ArrayList<>();
		IfNode ifNode = null;
		IfNode elsIf = null;
		ElseNode elseNode = null;

		if (matchAndRemove(Token_enum.IF) != null) {
			boolExpression = booleanExpression();

			if (boolExpression != null) {
				if (matchAndRemove(Token_enum.THEN) != null) {
					removeEOL();
					statementList = statements();
					//may not need to remove EOLs after statements()
					removeEOL();
					ifNode = new IfNode(boolExpression, statementList);

				}else
					throw new ShankException("ifStatement(): No THEN token found");
			}else
				throw new ShankException("ifStatement(): No booleanExpression found");
		}

		if (ifNode != null) {

			if (matchAndRemove(Token_enum.ELSIF) != null) {
				boolExpression = booleanExpression();

				if (boolExpression != null) {
					if (matchAndRemove(Token_enum.THEN) != null) {
						removeEOL();
						statementList = statements();
						//may not need to remove EOLs after statements()
						removeEOL();

						elsIf = new IfNode(boolExpression, statementList);
						ifNode.setElsIfOrElse(elsIf);

					}else
						throw new ShankException("ifStatement(): No THEN token found");
				}else
					throw new ShankException("ifStatement(): No booleanExpression found");
			}

			while(matchAndRemove(Token_enum.ELSIF) != null) {
				boolExpression = booleanExpression();

				if (boolExpression != null) {
					if (matchAndRemove(Token_enum.THEN) != null) {
						removeEOL();
						statementList = statements();
						//may not need to remove EOLs after statements()
						removeEOL();
						IfNode elsIf2 = new IfNode(boolExpression, statementList);
						elsIf.setElsIfOrElse(elsIf2);
						elsIf = elsIf.getElsIfOrElse();

					}else
						throw new ShankException("ifStatement(): No THEN token found");
				}else
					throw new ShankException("ifStatement(): No booleanExpression found");
			}
		}

		if (ifNode != null && elsIf != null) {
			if (matchAndRemove(Token_enum.ELSE) != null) {
			
					removeEOL();
					statementList = statements();
					//may not need to remove EOLs after statements()
					removeEOL();

					elseNode = new ElseNode(statementList);
					elsIf.setElsIfOrElse(elseNode);
				
			}
		}else if (ifNode != null && ifNode.getElsIfOrElse() == null) {
			//elsIf == null
			if (matchAndRemove(Token_enum.ELSE) != null) {
				
					removeEOL();
					statementList = statements();
					//may not need to remove EOLs after statements()
					removeEOL();

					elseNode = new ElseNode(statementList);
					ifNode.setElsIfOrElse(elseNode);
				}
			}

		return ifNode;
	}

	private Token matchAndRemove(Token_enum expected) {

		if (tokenList.isEmpty()) {
			return null;
		}else if (tokenList.get(0).getToken_enum() == expected) {
			return tokenList.remove(0);
		}

		return null;
	}

	private Token_enum peekEnum(){

		if (tokenList.isEmpty()) {
			return null;
		}

		return tokenList.get(0).getToken_enum();
	}

	private boolean isFloat(Token token) {
		return token.getToken_enum() == Token_enum.NUMBER && token.getData().contains(".");
	}

	private Token removeEOL() {
		Token eolToken = matchAndRemove(Token_enum.EOL);
		while(eolToken != null) {
			eolToken = matchAndRemove(Token_enum.EOL);
		}

		return eolToken;
	}
}