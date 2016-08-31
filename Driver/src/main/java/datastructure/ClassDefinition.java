package datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class ClassDefinition {
	public Map<String, String> attributes;
	/*attribute neve, attribute típusa*/
	
	public String className;
	
	public Map<String, String> defaultValues;
	/*attrubutename- defaultavalue.  Ha valamelyik attribútumnak nincs default értéke, akkor az benne
	 sincs ebben a mapben.Az értéket minden esetben stringben tároljuk, viszont az attribútum
	 típusából kitudjuk konvertálni.*/
	public Map<String, Instance> defaultValuesInMap;/*ebben a fenti mintájára tároljuk a már létrehozott isnatcneokat,
	ezeke a defaultinstanceok.*/
	
	private List<TileMap> maps;/*Megkapja a classdefiníció az összes térképet, és mindegyikbe belerakja
	a default értékeit, hisz egy classdefinition az használható az összes mapen.*/
	
	private List<ClassDefinition> classes;
	
	public ClassDefinition(String className, List<TileMap> maps, List<ClassDefinition> classes) {
		attributes = new HashMap<String, String>();
		defaultValues = new HashMap<String, String>();
		defaultValuesInMap = new HashMap<String, Instance>();
		this.className = className;
		
		this.maps = maps;
		
		this.classes = classes;
		
		/*Végigmegyünk az összes mapen, és hozzáadjuk ennek az classnak a default valuejait, hogy meglegyen 
		 centralizáltan.*/
		
		/*Ha törlünk egy osztálydefiníciót, akkor ki kell törölni az összes mapből a már létrehozott default
		 értékeket, mert azokra nem lesz szükség soha.*/
		
		
	}
	
	public ClassDefinition(String className) {
		/*Ezt a másik konstruktort a driver használja*/
		attributes = new HashMap<String, String>();
		defaultValues = new HashMap<String, String>();
		this.className = className;
		
	}
	

	
	/**
	 * Definiate an existing attribute default value.The attributeName must be existing when this method called.
	 * 
	 * */
	public void setAttributeDefaultValue(String attributeName, String attributeValue){
		/*Kell egy metódus amin keresztül tudja a default értéket állítani, és ott betesszük a világokba mapekbe is.*/
		defaultValues.put(attributeName, attributeValue);
		
		/*Itt kell még a db mapekbe belepakolni a default értékekekt.*/
		
		String attributeType = null;/*Ebbe tároljuk le, hogy mi a típusa(Point, Line, Number, stb) a most beállított 
		attribútumnak*/
		
		for(String x : attributes.keySet()){
			if(x.equals(attributeName)){
				attributeType = attributes.get(x);
				break;
			}
		}
		
		
		/*Itt beállítja minden mapre ezt a default valuet.*/
		for(int i=0;i<maps.size();i++){
			if(attributeType.equals("String")){
				StringPrimitiv prim = maps.get(i).getMaker().makeString(attributeValue, classes, maps.get(i));
				if(i==0){
					defaultValuesInMap.put(attributeName, prim);
				}
			}else if(attributeType.equals("Number")){
				if(attributeValue.contains(".")){
					/*ekkor lebegőpontos számról van szó*/
					Float num = Float.parseFloat(attributeValue);
					NumberPrimitiv prim = maps.get(i).getMaker().makeNumber(num, classes, maps.get(i));
					if(i==0){
						defaultValuesInMap.put(attributeName, prim);
					}
				}else{
					Integer num = Integer.parseInt(attributeValue);
					NumberPrimitiv prim = maps.get(i).getMaker().makeNumber(num, classes, maps.get(i));
					if(i==0){
						defaultValuesInMap.put(attributeName, prim);
					}
				}
			}else if(attributeType.equals("Boolean")){
				BooleanPrimitiv prim = maps.get(i).getMaker().makeBoolean(Boolean.valueOf(attributeValue), classes, maps.get(i));
				if(i==0){
					defaultValuesInMap.put(attributeName, prim);
				}
			}
		}
		
		
		
		
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public void setAttribute(String attrName, String attrType){
		attributes.put(attrName, attrType);
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public JSONObject getJSONrepresentation(){
		JSONObject inner = new JSONObject();
		
		for(String x : attributes.keySet()){
			if(defaultValues.containsKey(x)){
				inner.put(x, attributes.get(x) + " DEFAULT " + defaultValues.get(x));
			}else{
				inner.put(x, attributes.get(x));
			}
		}
		
		return inner;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("className: " + className);
		
		for(String x: attributes.keySet()){
			if(defaultValues.containsKey(x)){
				sb.append(" " + x + " : " + attributes.get(x) + " DEFAULT " + defaultValues.get(x));
			}else{
				sb.append(" " + x + " : " + attributes.get(x));
			}
			
		}
		return sb.toString();
	}
		
}