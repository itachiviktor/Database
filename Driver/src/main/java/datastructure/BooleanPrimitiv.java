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
	public <T> void setValue(T value) {
		if(value instanceof Boolean){
			this.value = (Boolean)value;
		}
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
	
	
	public boolean hasThisChild(int id) {
		return false;
	}
	
	public void delete(List<Instance> map){
		boolean isItDeletable = true; /*ez a változó azt tárolja, hogy törölhető-e az elem, azaz senki nem hivatkozik rá
		csak a szőlöje, aki továbbhívta ezt a metódust.*/
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).hasThisChild(this.id)){
				isItDeletable = false;
			}
		}
		
		/*csak akkor törlöm az elemet, és annak gyerekeit, ha senki sem mutat rá*/
		if(isItDeletable){
			
			/*Itt saját magát törli ki.*/
			for(int i=0;i<map.size();i++){
				if(map.get(i).id == this.id){
					map.remove(i);
				}
			}
		}
	}
	
	public void delete(List<Instance> map, int parentId){
		boolean isItDeletable = true; /*ez a változó azt tárolja, hogy törölhető-e az elem, azaz senki nem hivatkozik rá
		csak a szőlöje, aki továbbhívta ezt a metódust.*/
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).hasThisChild(this.id) && map.get(i).id != parentId){
				isItDeletable = false;
			}
		}
		
		/*csak akkor törlöm az elemet, és annak gyerekeit, ha senki sem mutat rá*/
		if(isItDeletable){
			
			/*Itt saját magát törli ki.*/
			for(int i=0;i<map.size();i++){
				if(map.get(i).id == this.id){
					map.remove(i);
				}
			}
		}
	}
}
