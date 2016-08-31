package datastructure;

import java.util.List;

import org.json.JSONObject;

public class BooleanPrimitiv extends Instance{
	public Boolean value;
	
	public BooleanPrimitiv(String className, List<ClassDefinition> classes, TileMap map) {
		super(className, classes, map);
	}
	
	@Override
	public JSONObject getJSONRepresentation() {
		JSONObject ob = new JSONObject();
		ob.put("className", "Boolean");
		ob.put("value", value);
		ob.put("zindex", zindex);
		ob.put("zlayer", zlayer);
		return ob;
	}
	
	@Override
	public <T> T getValue() {
		return (T) value;
	}
	
	public void setAttribute(Boolean value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		return super.toString() + "value: " + value;
	}
}
