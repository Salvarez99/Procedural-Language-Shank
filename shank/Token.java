package shank;

public class Token {

	private String data;
	private Token_enum token_enum;



	public Token() {
		this.data = null;
		this.token_enum = null;
	}

	public Token(String data, Token_enum token_enum) {
		this.data = data;
		this.token_enum = token_enum;
	}

	public Token(String data) {
		this.data = data;
	}

	public Token(Token_enum token_enum) {
		this.token_enum = token_enum;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Token_enum getToken_enum() {
		return token_enum;
	}

	public void setToken_enum(Token_enum token_enum) {
		this.token_enum = token_enum;
	}

	@Override
	public String toString() {
		if(this.token_enum.equals(Token_enum.PLUS)) {
			return Token_enum.PLUS + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.MINUS)) {
			return Token_enum.MINUS + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.TIMES)) {
			return Token_enum.TIMES  + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.DIVIDE)) {
			return Token_enum.DIVIDE + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.MOD)) {
			return Token_enum.MOD + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.EOL)) {
			return Token_enum.EOL.toString();
		}else if(this.token_enum.equals(Token_enum.LPAREN)) {
			return Token_enum.LPAREN.toString() + "(";
		}else if(this.token_enum.equals(Token_enum.RPAREN)) {
			return ")" + Token_enum.RPAREN.toString();
		}else if(this.token_enum.equals(Token_enum.IDENTIFIER)) {
			return Token_enum.IDENTIFIER + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.DEFINE)) {
			return Token_enum.DEFINE + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.INTEGER)) {
			return Token_enum.INTEGER + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.REAL)) {
			return Token_enum.REAL + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.BEGIN)) {
			return Token_enum.BEGIN + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.END)) {
			return Token_enum.END + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.VARIABLES)) {
			return Token_enum.VARIABLES + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.CONSTANTS)) {
			return Token_enum.CONSTANTS + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.SEMICOLON)) {
			return Token_enum.SEMICOLON + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.COLON)) {
			return Token_enum.COLON + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.EQUAL)) {
			return Token_enum.EQUAL + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.COMMA)) {
			return Token_enum.COMMA + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.ASSIGNMENT)) {
			return Token_enum.ASSIGNMENT + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.IF)) {
			return Token_enum.IF + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.THEN)) {
			return Token_enum.THEN + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.ELSE)) {
			return Token_enum.ELSE + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.ELSIF)) {
			return Token_enum.ELSIF + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.FOR)) {
			return Token_enum.FOR + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.FROM)) {
			return Token_enum.FROM + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.TO)) {
			return Token_enum.TO + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.WHILE)) {
			return Token_enum.WHILE + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.REPEAT)) {
			return Token_enum.REPEAT + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.UNTIL)) {
			return Token_enum.UNTIL + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.GREATERTHAN)) {
			return Token_enum.GREATERTHAN + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.GREATERTHANOREQUAL)) {
			return Token_enum.GREATERTHANOREQUAL + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.LESSTHAN)) {
			return Token_enum.LESSTHAN + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.LESSTHANOREQUAL)) {
			return Token_enum.LESSTHANOREQUAL + "(" + data + ")";
		}else if(this.token_enum.equals(Token_enum.NOTEQUAL)) {
			return Token_enum.NOTEQUAL + "(" + data + ")";
		}
		

		return Token_enum.NUMBER + "(" + data + ")";
	}

}
