package datastructure;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import database.queryObject.Operators;

public class Instance implements Comparable<Instance>{
	public ClassDefinition classType;
	/*Ez tartalmazni fogly az összes, attribútumát ennek a példányak, azaz az osztályleíró.*/
	
	public Map<String, Integer> attributes;
	/*attrubútumnév, érték*/
	public String className;
	/*classname attribútum értékét itt tároljuk*/
	
	public Integer id;
	
	public Integer zindex;
	public Integer zlayer;
	
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
		
		/*Itt beállítjuk neki a classname típusát is.*/
		this.className = classType.className;
		
	}
	
	public Instance(String className, List<ClassDefinition> classes){
		this.classes = classes;
		
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
		
		/*Itt beállítjuk neki a classname típusát is.*/
		this.className = classType.className;
		
		/*Ez a konstruktor azért kell, hogy lehessen úgy instancet létrehozni, hogy az
		 semmilyen mapbe ne legyen benne, ez csak a memóriába jön létre.*/
	}
	
	public boolean hasThisAttribute(String attributeName){
		for(String x : attributes.keySet()){
			if(x.equals(attributeName)){
				return true;
			}
		}
		
		if(attributeName.equals("id") || attributeName.equals("zindex") || attributeName.equals("zlayer")){
			return true;
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
	
	public <T> void setValue(T value){
		
	}
	
	public boolean operate(Instance otherOperande, Operators operator){
		if(operator == Operators.CLOSEST){
			
		}else if(operator == Operators.COLLIDE){

			if(this.attributes.containsKey("x") && this.attributes.containsKey("y") &&
					this.attributes.containsKey("width") && this.attributes.containsKey("height")){
				
				if(otherOperande.className.equals("Rectangle")){
					Rectangle a = new Rectangle((Integer)getAttribute("x").getValue(), (Integer)getAttribute("y").getValue(),
							(Integer)getAttribute("width").getValue(), (Integer)getAttribute("height").getValue());
					
					Rectangle b = new Rectangle((Integer)otherOperande.getAttribute("location").getAttribute("x").getValue(),
							(Integer)otherOperande.getAttribute("location").getAttribute("y").getValue(),
							(Integer)otherOperande.getAttribute("size").getAttribute("width").getValue(),
							(Integer)otherOperande.getAttribute("size").getAttribute("height").getValue());
					
					return a.intersects(b);
				}else if(otherOperande.className.equals("Point")){
					Rectangle a = new Rectangle((Integer)getAttribute("x").getValue(), (Integer)getAttribute("y").getValue(),
							(Integer)getAttribute("width").getValue(), (Integer)getAttribute("height").getValue());
					
					Point b = new Point((Integer)otherOperande.getAttribute("x").getValue(), (Integer)otherOperande.getAttribute("y").getValue());
					
					return a.contains(b);
					
				}else{
					return false;
				}
				
			}else{
				return false;
			}
		}else{
			/*dobni kell valami exceptiont*/
		}
		
		
		return false;
	}
	
	public boolean operate(Boolean otherOperande, Operators operator){
		/*itt az alap definícióban hibát kell dobnia*/
		return false;
	}
	
	public boolean operate(Number otherOperande, Operators operator){
		/*itt az alap definícióban hibát kell dobnia*/
		return false;
	}
	
	public boolean isOperator(String className){
		if(this.className.equals(className)){
			return true;
		}else{
			return false;
		}
	}
	
	public Number distanceFrom(double a, double b){
		double returnValue;
		if(attributes.containsKey("x") && attributes.containsKey("y")){
			Integer x = (Integer)getAttribute("x").getValue();
			Integer y = (Integer)getAttribute("y").getValue();
			double doublex = x.doubleValue();
			double doubley = y.doubleValue();
			returnValue = Math.sqrt( ((doublex - a) * (doublex - a)) + ( (doubley - b) * (doubley - b)) );
		}else{
			returnValue = -1;
		}
		//System.out.println(returnValue);
		return returnValue;
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

	public int compareTo(Instance o) {
		return 0;
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
			for(String x : attributes.keySet()){
				int childId = attributes.get(x);
				
				for(int i=0;i<map.size();i++){
					if(map.get(i).id == childId){
						map.get(i).delete(map, this.id);
					}
				}
			}
			
			/*Itt saját magát törli ki.*/
			for(int i=0;i<map.size();i++){
				if(map.get(i).id == this.id){
					map.remove(i);
				}
			}
		}
	}
	
	/**
	 * This method gives that has this object tha parameter id child.
	 * Az a lényeg, hogy a paraméterben kapott id-jű gyereke van-e ennek az attribútumban.
	 * @param id
	 * @return
	 */
	public boolean hasThisChild(int id){
		for(String x : attributes.keySet()){
			if(attributes.get(x) == id){
				return true;
			}
		}
		return false;
	}
	
	public void delete(List<Instance> map, int parentId){
		boolean isItDeletable = true; /*ez a változó azt tárolja, hogy törölhető-e az elem, azaz senki nem hivatkozik rá
		csak a szőlöje, aki továbbhívta ezt a metódust.*/
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).hasThisChild(this.id) && map.get(i).id != parentId){
				isItDeletable = false;
				break;
			}
		}
		
		/*csak akkor törlöm az elemet, és annak gyerekeit, ha senki sem mutat rá*/
		if(isItDeletable){
			
			/*itt a gyerekeknek szólok, ha ő rájuk sem hivatkozik senki, akkor töröljék magukat.*/
			for(String x : attributes.keySet()){
				int childId = attributes.get(x);
				
				for(int i=0;i<map.size();i++){
					if(map.get(i).id == childId){
						map.get(i).delete(map, this.id);
					}
				}
			}
			
			/*Itt saját magát törli ki.*/
			for(int i=0;i<map.size();i++){
				if(map.get(i).id == this.id){
					map.remove(i);
				}
			}
		}
	}
}