package shank;

public class FloatDataType extends InterpreterDataType{
	private float value;
	
	public FloatDataType() {
		super();
	}
	
	public FloatDataType(Token token) {
		super();
		this.value = Float.parseFloat(token.getData());
	}

	public FloatDataType(float value) {
		super();
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Float.toString(getValue());
	}

	@Override
	public void fromString(String input) {
		setValue(Float.parseFloat(input));
	}

}
