package datastructure;

import java.util.List;

import org.json.JSONObject;

import database.queryObject.NumberCompare;
import database.queryObject.Operators;

public class NumberPrimitiv extends Instance implements Comparable<Instance>{
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
	
	@Override
	public <T> void setValue(T value) {
		if(value instanceof Number){
			this.value = (Number)value;
		}
	}
	
	@Override
	public boolean operate(Number otherOperande, Operators operator) {
		
		return NumberCompare.compare(this.value, otherOperande, operator);
		
	}
	
	@Override
	public boolean operate(Instance otherOperande, Operators operator) {
		if(otherOperande instanceof NumberPrimitiv){
			NumberPrimitiv num = (NumberPrimitiv) otherOperande;
			
			NumberCompare.compare(this.value, (Number)num.getValue(), operator);
		}
		return false;
	}
	
	public void setAttribute(Number value){
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "value: " + value;
	}

	public int compareTo(Instance o) {
		System.out.println("call");
		if(o.getValue() != null && o.getValue() instanceof Number){
			Double thisvalue = value.doubleValue();
			Number othervalue = (Number)o.getValue();
			Double other = othervalue.doubleValue();
			if(thisvalue > other){
				return 1;
			}else if(thisvalue < other){
				return -1;
			}else{
				return 0;
			}
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