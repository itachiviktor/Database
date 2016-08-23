package datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import driver.Result;

public class Instance {
	public ClassDefinition classType;
	/*Ez tartalmazni fogly az összes, attribútumát ennek a példányak, azaz az osztályleíró.*/
	
	public Map<String, Integer> attributes;
	/*attrubútumnév, érték*/
	public String className;
	/*classname attribútum értékét itt tároljuk*/
	
	public int id;
	
	public InstanceProviderFromMap provider;
	
	public List<Instance> terkep;
	public List<ClassDefinition> classes;
	
	public Instance(String className, List<ClassDefinition> classes, List<Instance> terkep) {
	
		this.terkep = terkep;
		this.classes = classes;
		
		this.provider = new InstanceProviderFromMap(terkep);
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
	
	public void setAttribute(String attributeName, Integer value){
		if(attributeName.equals("id")){
			this.id = value;
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
		for(String x : attributes.keySet()){
			inner.put("@" + x, attributes.get(x));
		}
	
		return inner;
	}
	

	public <T> T getValue() {
		return null;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		sb.append("id: " + id);
		sb.append(" className: " + className + " ");
		for(String x : attributes.keySet()){
			sb.append(x + ": " + attributes.get(x) + " ");
		}
		
		return sb.toString();
	}
	
}