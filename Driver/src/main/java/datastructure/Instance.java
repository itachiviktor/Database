package datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import database.queryObject.Operators;
import driver.Result;

public class Instance {
	public ClassDefinition classType;
	/*Ez tartalmazni fogly az összes, attribútumát ennek a példányak, azaz az osztályleíró.*/
	
	public Map<String, Integer> attributes;
	/*attrubútumnév, érték*/
	public String className;
	/*classname attribútum értékét itt tároljuk*/
	
	public int id;
	
	public int zindex;
	public int zlayer;
	
	public InstanceProviderFromMap provider;
	
	public TileMap map;
	public List<ClassDefinition> classes;
	
	public Instance(String className, List<ClassDefinition> classes, TileMap map) {
	
		this.map = map;
		this.classes = classes;
		
		this.provider = new InstanceProviderFromMap(map);
		this.attributes = new HashMap<String, Integer>();
		
		for(ClassDefinition x : classes){
			if(x.className.equals(className)){
				this.classType = x;
				break;
			}
		}
		
		/*Itt definiálom a példánynak az attribútumokat, viszont itt még mindegyik nullra mutat.*/
		
		for(String x : classType.getAttributes().keySet()){
			this.attributes.put(x, null);
		}
		
		/*Itt beállítjuk neki a clanname típusát is.*/
		this.className = classType.className;
		
	}
	
	public boolean hasThisAttribute(String attributeName){
		for(String x : attributes.keySet()){
			if(x.equals(attributeName)){
				return true;
			}
		}
		return false;
	}
	
	public void setAttribute(String attributeName, Integer value){
		if(attributeName.equals("id")){
			this.id = value;
		}else if(attributeName.equals("zindex")){
			this.zindex = value;
		}else if(attributeName.equals("zlayer")){
			this.zlayer = value;
		}else{
			for(String x : attributes.keySet()){
				if(x.equals(attributeName)){
					//attributes.replace(x, null, value);
					
					attributes.replace(x, value);
				}
			}
		}
	}
	
	public Instance getAttribute(String attributeName){
		for(String x : attributes.keySet()){
			if(x.equals(attributeName)){
				return provider.provide(attributes.get(x));
			}
		}
		return null;
	}
	
	public int getID(){
		return this.id;
	}
	
	public JSONObject getJSONRepresentation(){
	
		JSONObject inner = new JSONObject();
		
		inner.put("className", className);
		inner.put("zindex", zindex);
		inner.put("zlayer", zlayer);
		for(String x : attributes.keySet()){
			inner.put("@" + x, attributes.get(x));
		}
	
		return inner;
	}
	

	public <T> T getValue() {
		return null;
	}
	
	public boolean operate(Instance otherOperande, Operators operator){
		if(operator == Operators.CLOSEST){
			
		}else if(operator == Operators.COLLIDE){
			
		}
		
		return true;
	}
	
	public boolean isOperator(String className){
		if(this.className.equals(className)){
			return true;
		}else{
			return false;
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("id: " + id);
		sb.append(" className: " + className + " ");
		sb.append("zindex: " + zindex + " ");
		sb.append("zlayer: " + zlayer + " ");
		for(String x : attributes.keySet()){
			sb.append(x + ": " + attributes.get(x) + " ");
		}
		
		return sb.toString();
	}
	
}