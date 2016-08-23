package datastructure;

import java.util.List;

import org.json.JSONObject;

public class StringPrimitiv extends Instance{

	public String value;
	
	public StringPrimitiv(String className, List<ClassDefinition> classes, List<Instance> terkep) {
		super(className, classes, terkep);
		
	}
	
	@Override
	public JSONObject getJSONRepresentation() {
		JSONObject ob = new JSONObject();
		ob.put("className", "String");
		ob.put("value", value);
		return ob;
	}
	
	@Override
	public <T> T getValue() {
		return (T) value;
	}
	
	public void setAttribute(String value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "value: " + value;
	}

}
