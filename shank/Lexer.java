package shank;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

	private HashMap<String, Token_enum> keywords;
	private ArrayList<Token> tokenList;
	private StringBuilder build_data;
	private String line;
	private Token token;
	private int index;
	private int prev = index - 1;
	private int next = index + 1;
	private static boolean comment = false;

	public Lexer() {
		this.keywords = new HashMap<>();
		this.tokenList = new ArrayList<>();
		this.build_data = new StringBuilder();
		this.token = null;
		this.line = null;
		this.index = 0;
		this.prev = index - 1;
		this.next = index + 1;

	}

	public Lexer(String line) {
		this.keywords = new HashMap<>();
		this.tokenList = new ArrayList<>();
		this.build_data = new StringBuilder();
		this.line = line;
		this.token = null;
		this.index = 0;
		this.prev = index - 1;
		this.next = index + 1;
	}

	public void setLine(String line) {
		this.line = line;
	}

	/* Designates which state is needed to process current sequence of characters
	 * @return tokenList: ArrayList<Token>
	 */
	public ArrayList<Token> lex(){
//		System.out.println("lex");
		keywords.put("integer", Token_enum.INTEGER);
		keywords.put("define", Token_enum.DEFINE);
		keywords.put("real", Token_enum.REAL);
		keywords.put("begin", Token_enum.BEGIN);
		keywords.put("end", Token_enum.END);
		keywords.put("variables", Token_enum.VARIABLES);
		keywords.put("constants", Token_enum.CONSTANTS);
		keywords.put("if", Token_enum.IF);
		keywords.put("then", Token_enum.THEN);
		keywords.put("else", Token_enum.ELSE);
		keywords.put("elsif", Token_enum.ELSIF);
		keywords.put("for", Token_enum.FOR);
		keywords.put("from", Token_enum.FROM);
		keywords.put("to", Token_enum.TO);
		keywords.put("while", Token_enum.WHILE);
		keywords.put("repeat", Token_enum.REPEAT);
		keywords.put("until", Token_enum.UNTIL);
		keywords.put("var", Token_enum.VAR);


		int state = 0;
		resetIndices();
		while(index < line.length()) {


			if(comment == true) {
				state = 3;

			}else if(index == 0 && (line.charAt(index) == '+' || line.charAt(index) == '-')){
				if (isNumber(next) || line.charAt(next) == '.') {
					state = 0;
				}
			}else if(line.charAt(index) == '(' || line.charAt(index) == ')') {

				if (inBounds(next) && line.charAt(index) == '(' && line.charAt(next) == '*') {
					comment = true;
					if (comment) {
						state = 3;
					}
				}else
					state = 1;

			}else if(line.charAt(index) == '+') {

				if (latestToken().getToken_enum() == Token_enum.NUMBER || latestToken().getToken_enum() == Token_enum.RPAREN || latestToken().getToken_enum() == Token_enum.IDENTIFIER) {
					state = 1;
				}else if (inBounds(next) && isNumber(next) && (!isTokenMathOp(latestToken()) || latestToken().getToken_enum() == Token_enum.LPAREN)) {
					state = 0;
				}else if (inBounds(next) && isWhiteSpace(next) && (!isTokenMathOp(latestToken()) || latestToken().getToken_enum() == Token_enum.LPAREN)) {
					state = 1;
				}else if(inBounds(next) && latestToken().getToken_enum() == Token_enum.IDENTIFIER && (isAlphabetic(next) || isNumber(next))) {
					state = 1;
				}


			}else if(line.charAt(index) == '-') {
				
				if (latestToken().getToken_enum() == Token_enum.NUMBER || latestToken().getToken_enum() == Token_enum.RPAREN || latestToken().getToken_enum() == Token_enum.IDENTIFIER) {
					state = 1;
				}else 
					state = 0;


			}else if(isNumber(index) || line.charAt(index) == '.') {
				state = 0;

			}else if(isMathOp(index)) {
				if (inBounds(next) && (line.charAt(next) == '.' || line.charAt(next) == '-' || line.charAt(next) == '(' || isNumber(next) || isWhiteSpace(next))) {
					state = 1;

				}else if (inBounds(next) && (latestToken().getToken_enum() == Token_enum.IDENTIFIER || isAlphabetic(next))) {
					state = 1;
				}
			}else if(isWhiteSpace(index)) {
				incrementAll();
				state = -1;
			}else if(isAlphabetic(index) || isSymbol(index) || line.charAt(index) == '_') {
				state = 2;
			}else {
				throw new ShankException("No valid state available");
			}

			//State Machine
			/*
			 * States:
			 * -1: whitespace && EOL
			 *  0: numberState
			 *  1: mathOpState
			 *  2: wordState
			 *  3: commentState
			 */
			if (state != -1) {
				switch(state) {
				case 0: 
					numberState();
					break;
				case 1:
					mathOpState();
					break;
				case 2:
					wordState();
					break;
				case 3:
					commentState();
					break;
				default:
					throw new ShankException("No valid states for current input");
				}
			}

		}
		token = new Token();
		token.setToken_enum(Token_enum.EOL);
		addToList(token);
		return tokenList;
	}
	/*
	 * State machine used to tokenize the current string into valid number tokens. Once converted
	 * the token will be added to the tokenList
	 */
	private void numberState() {

		this.token = new Token();
		this.build_data = new StringBuilder();

		int decimalCounter = 0;

		if (line.charAt(index) == '+' || line.charAt(index) == '-') {
			appendToBuildData(index);
			incrementAll();

			if(inBounds(index) && isWhiteSpace(index))
				throw new ShankException("numberState(): Invalid number");
		}

		while(inBounds(index) && !isMathOp(index) && !isWhiteSpace(index) && (isNumber(index) || line.charAt(index) == '.')) {

			if (line.charAt(index) == '.') {
				decimalCounter++;

				if (decimalCounter < 2) {

					if(isNumber(next)) {
						appendToBuildData(index);
					}else 
						throw new ShankException("Error in numberSM: Decimal is not followed by a number");
				}else 
					throw new ShankException("Error in numberSM: Too many decimals");


			}else	
				appendToBuildData(index);

			incrementAll();

		}

		token.setData(build_data.toString());
		token.setToken_enum(Token_enum.NUMBER);
		addToList(token);
	}

	/*
	 * State machine used to tokenize the current string into valid math operator tokens. Once converted
	 * the token will be added to the tokenList
	 */
	private void mathOpState() {
		this.token = new Token();
		this.build_data = new StringBuilder();

		switch (line.charAt(index)){
		case '+': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.PLUS);
			addToList(token);
			break;
		}case '-': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.MINUS);
			addToList(token);
			break;
		}case '*': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.TIMES);
			addToList(token);
			break;
		}case '/': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.DIVIDE);
			addToList(token);
			break;
		}case '%': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.MOD);
			addToList(token);
			break;
		}case '(': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.LPAREN);
			addToList(token);
			break;
		}case ')': {
			appendToBuildData(index);
			token.setData(build_data.toString());
			token.setToken_enum(Token_enum.RPAREN);
			addToList(token);
			break;
		}
		default:
			throw new ShankException("mathOpState(): Invalid entry");
		}

		incrementAll();
	}

	/*
	 * State machine used to tokenize the current string into valid keyword tokens. Once converted
	 * the token will be added to the tokenList
	 */
	private void wordState() {

		this.token = new Token();
		this.build_data = new StringBuilder();

		if (isSymbol(index)) {
			switch (line.charAt(index)) {
			case ';': 
				appendToBuildData(index);
				token.setData(build_data.toString());
				token.setToken_enum(Token_enum.SEMICOLON);
				addToList(token);
				break;
			case ':': 

				if (inBounds(next) && line.charAt(next) == '=') {
					appendToBuildData(index);
					incrementAll();
					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.ASSIGNMENT);
					addToList(token);
				}else {
					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.COLON);
					addToList(token);
				}
				break;
			case ',': 
				appendToBuildData(index);
				token.setData(build_data.toString());
				token.setToken_enum(Token_enum.COMMA);
				addToList(token);
				break;
			case '=': 
				appendToBuildData(index);
				token.setData(build_data.toString());
				token.setToken_enum(Token_enum.EQUAL);
				addToList(token);
				break;
			case '>': 

				if (inBounds(next) && line.charAt(next) == '=') {
					appendToBuildData(index);
					incrementAll();
					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.GREATERTHANOREQUAL);
					addToList(token);
				}else {


					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.GREATERTHAN);
					addToList(token);
				}
				break;
			case '<': 
				if (inBounds(next) && line.charAt(next) == '=') {
					appendToBuildData(index);
					incrementAll();
					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.LESSTHANOREQUAL);
					addToList(token);
				}else if (inBounds(next) && line.charAt(next) == '>') {
					appendToBuildData(index);
					incrementAll();
					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.NOTEQUAL);
					addToList(token);
				}else {
					appendToBuildData(index);
					token.setData(build_data.toString());
					token.setToken_enum(Token_enum.LESSTHAN);
					addToList(token);
				}
				break;
			}
			incrementAll();

		}else if(inBounds(index) && (isNumber(index) || isAlphabetic(index) || line.charAt(index) == '_')) {


			while(inBounds(index) && (isNumber(index) || isAlphabetic(index) || line.charAt(index) == '_')) {
				appendToBuildData(index);
				incrementAll();

			}

			if (keywords.containsKey(build_data.toString())) {
				token.setData(build_data.toString());
				token.setToken_enum(keywords.get(build_data.toString()));
				tokenList.add(token);
			}else {
				token.setData(build_data.toString());
				token.setToken_enum(Token_enum.IDENTIFIER);
				tokenList.add(token);
			}
		}

	}

	/*
	 * State machine used to handle single and block comments in shank. Does this by ignoring input until ending condition is found
	 */
	private void commentState() {

		/*
		 * while loop
		 * index cant be *
		 * next cant be )
		 * 
		 * next has to be inbounds
		 */

		/*
		 * comment = false
		 * when
		 * index = *
		 * next = )
		 * 
		 * else true
		 * 
		 * inc
		 * inc
		 * 
		 */



		//		incrementAll();
		do {
			incrementAll();
			if (next == line.length() - 1) {
				break;
			}

		}while(inBounds(next) && (line.charAt(index) != '*' || line.charAt(next) != ')'));
		//T F T

		//if *) was not encountered comment must stay true

		//		 if (!inBounds(index) && !inBounds(next)) {
		//			comment = false;
		//		}else

		if ((inBounds(index) && inBounds(next)) && (line.contains("*") && line.contains(")"))  && line.charAt(index) == '*' && line.charAt(next) == ')') {
			comment = false;
		}

		if ((inBounds(index) && inBounds(prev)) && (line.contains("*") && line.contains(")"))  && line.charAt(prev) == '*' && line.charAt(index) == ')') {
			comment = false;
		}


		incrementAll();
		incrementAll();
	}

	/*
	 * Appends the character at the passed index to the Stringbuilder	
	 */
	private void appendToBuildData(int index) {
		this.build_data.append(line.charAt(index));
	}

	/*
	 * Checks if the character at the passed index is a digit
	 * @param: int index
	 * @return: boolean	
	 */
	private boolean isNumber(int index) {
		if (!inBounds(index)) {
			throw new ShankException("isNumber(): Index out of bounds");
		}

		return Character.isDigit(line.charAt(index));

	}

	/*
	 * Checks if the passed token's enum value is a math operator
	 * @param1: Token token
	 * @return: boolean	
	 */	
	private boolean isTokenMathOp(Token token) {

		switch(token.getToken_enum()) {
		case PLUS:
			return true;
		case MINUS:
			return true;
		case DIVIDE:
			return true;
		case TIMES:
			return true;
		default:
			break;
		}

		return false;
	}

	/*
	 * Checks if the character at the passed index is a math operator
	 * @param: int index
	 * @return: boolean	
	 */
	private boolean isMathOp(int index) {

		if (!inBounds(index)) {
			return false;
		}

		char current = line.charAt(index);
		return current == '+' || current == '-' || current == '*' || current == '/' || current == '(' || current == '%';
	}

	/*
	 * Checks if the character at the passed index is a whitespace
	 * @param: int index
	 * @return: boolean	
	 */
	private boolean isWhiteSpace(int index) {

		if (!inBounds(index)) {
			return false;
		}


		return Character.isWhitespace(line.charAt(index));
	}

	/*
	 * Checks if the character at the passed index is a letter
	 * @param: int index
	 * @return: boolean	
	 */
	private boolean isAlphabetic(int index) {

		if (!inBounds(index)) {
			return false;
		}

		return Character.isAlphabetic(line.charAt(index));
	}

	/*
	 * Checks if the character at the passed index is a symbol (; : = ,)
	 * @param: int index
	 * @return: boolean	
	 */
	private boolean isSymbol(int index) {

		if (!inBounds(index)) {
			return false;
		}

		char current = line.charAt(index);

		return current == ';' || current == ':' || current == '=' || current == ',' || current == '<' || current == '>' || current == ',';
	}

	/*
	 * Increment all global indices
	 */
	private void incrementAll() {
		this.prev++;
		this.index++;
		this.next++;
	}

	/*
	 * Sets all global indices to default values
	 */
	private void resetIndices() {
		this.index = 0;
		this.prev = index - 1;
		this.next = index + 1;
	}

	/*
	 * Adds the passed token to the tokenList
	 */
	private void addToList(Token token) {
		this.tokenList.add(token);
	}

	/*
	 * Checks if the passed index is within the current string's bounds
	 * @param: int index
	 * @return: boolean	
	 */
	private boolean inBounds(int index) {
		return index > -1 && index < line.length();
	}

	/*
	 * Returns the last token added to the tokenList
	 * @return: Token
	 */
	private Token latestToken() {
		return tokenList.get(tokenList.size() - 1);

	}
}