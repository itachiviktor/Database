package datastructure;

import java.util.List;

import org.json.JSONObject;

import database.queryObject.Operators;

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
	
	@Override
	public boolean isOperator(String className) {
		return value.equals(className);
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