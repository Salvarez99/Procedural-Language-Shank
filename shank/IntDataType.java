package shank;

public class IntDataType extends InterpreterDataType {


	private int value;
	
	
	public IntDataType() {
		super();
	}
	
	public IntDataType(Token token) {
		super();
		this.value = Integer.parseInt(token.getData());
	}
	
	public IntDataType(int value) {
		super();
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(getValue());
	}

	@Override
	public void fromString(String input) {
		// TODO Auto-generated method stub
		setValue(Integer.parseInt(input));

	}

}
