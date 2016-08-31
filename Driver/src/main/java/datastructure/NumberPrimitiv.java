package datastructure;

import java.util.List;

import org.json.JSONObject;

public class NumberPrimitiv extends Instance{
	public Number value;
	
	public NumberPrimitiv(String className, List<ClassDefinition> classes, TileMap map) {
		super(className, classes, map);
		
	}
	
	@Override
	public JSONObject getJSONRepresentation() {
		JSONObject ob = new JSONObject();
		ob.put("className", "Number");
		ob.put("value", value);
		ob.put("zindex", zindex);
		ob.put("zlayer", zlayer);
		return ob;
	}
	
	@Override
	public <T> T getValue() {
		return (T) value;
	}
	
	public void setAttribute(Number value){
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "value: " + value;
	}	
}