package datastructure;

import java.util.List;

import org.json.JSONObject;

public class StringPrimitiv extends Instance implements Comparable<Instance>{
	public String value;
	
	public StringPrimitiv(String className, List<ClassDefinition> classes, TileMap map) {
		super(className, classes, map);
		
	}
	
	@Override
	public JSONObject getJSONRepresentation() {
		JSONObject ob = new JSONObject();
		ob.put("className", "String");
		ob.put("value", value);
		ob.put("zindex", zindex);
		ob.put("zlayer", zlayer);

		return ob;
	}
	
	@Override
	public <T> T getValue() {
		return (T) value;
	}
	
	@Override
	public <T> void setValue(T value) {
		if(value instanceof String){
			this.value = (String)value;
		}
	}
	
	public void setAttribute(String value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "value: " + value;
	}

	
	public int compareTo(Instance o) {
		if(o.getValue() != null && o.getValue() instanceof StringPrimitiv){
			String thisvalue = value;
			String othervalue = (String)o.getValue();
			return thisvalue.compareTo(othervalue);
		}else{
			return -100;/*ez amolyan hibakód, hibadobással szebb lenne(a metódus dobjon hibát)*/
		}
	}

}