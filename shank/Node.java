package shank;

public abstract class Node extends ParameterNode {

	private Token token;
	
	public Node() {
		this.token = null;
	}

	public Node(Token token) {
		this.token = token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Token getToken() {
		return token;
	}

	@Override
	public abstract String toString();
}
