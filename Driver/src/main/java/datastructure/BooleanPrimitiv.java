package datastructure;

import java.util.List;

import org.json.JSONObject;

import database.queryObject.Operators;

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
	
	@Override
	public boolean operate(Instance otherOperande, Operators operator) {
		if(otherOperande instanceof BooleanPrimitiv){
			if(operator == Operators.EQ){
				return this.value == otherOperande.getValue();
			}else if(operator == Operators.NE){
				return this.value != otherOperande.getValue();
			}
		}
		
		return false;
	}
	
	@Override
	public boolean operate(Boolean otherOperande, Operators operator) {
		if(operator == Operators.EQ){
			return this.value == otherOperande;
		}else if(operator == Operators.NE){
			return this.value != otherOperande;
		}
		
		/*különben hibát kellene dobnia*/
		return false;
	}
	
	public void setAttribute(Boolean value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		return super.toString() + "value: " + value;
	}
}
