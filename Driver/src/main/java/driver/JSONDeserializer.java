package driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import datastructure.BooleanPrimitiv;
import datastructure.ClassDefinition;
import datastructure.Instance;
import datastructure.NumberPrimitiv;
import datastructure.StringPrimitiv;

public class JSONDeserializer {
	
	public List<Instance> map; 
	public List<ClassDefinition> classes;
	public List<Integer> results;
	
	public JSONDeserializer() {
		map = new ArrayList<Instance>();
		classes = new ArrayList<ClassDefinition>();
		results = new ArrayList<Integer>();
	}
	
	public List<Instance> getInstances(JSONObject databaseAnswer){
		map = new ArrayList<Instance>();
		JSONArray instances = databaseAnswer.getJSONArray("instances");
		
		int idValue = 0;
		
		for(int i=0;i<instances.length();i++){
			JSONObject object = instances.getJSONObject(i);
						
			idValue = Integer.parseInt(object.keys().next()); /*Ebbe az aktuális elem kulcsa van*/
			
			JSONObject objectSon = instances.getJSONObject(i).getJSONObject(object.keys().next()); //itt megvannak az adott id-hez tartozó attribútum:érték párok
			
			Iterator it = objectSon.keys();
			String className = objectSon.getString("className");
			
			if(className.equals("String")){
				StringPrimitiv inst = new StringPrimitiv("String", classes, map);
				inst.setAttribute((String)objectSon.get("value"));
				inst.id = idValue;
				map.add(inst);
			}else if(className.equals("Number")){
				NumberPrimitiv inst = new NumberPrimitiv("Number", classes, map);
				inst.setAttribute((Number)objectSon.get("value"));
				inst.id = idValue;
				map.add(inst);
			}else if(className.equals("Boolean")){
				BooleanPrimitiv inst = new BooleanPrimitiv("Boolean", classes, map);
				inst.setAttribute((Boolean)objectSon.get("value"));
				inst.id = idValue;
				map.add(inst);
			}else{
				Instance inst = new Instance(className, classes, map);
				while(it.hasNext()){
					String attr = (String)it.next();
					if(!attr.equals("value") && !attr.equals("className")){
						inst.setAttribute(attr.substring(1, attr.length()), (Integer)objectSon.get(attr));
						//System.out.println("new: " + inst.getAttribute(attr) + "but seted: " + (Integer)oo.get(attr));
					}
				}
				inst.id = idValue;	
				map.add(inst);
			}
		}
		
		return map;
	}
	
	public List<ClassDefinition> getClassDefinitions(JSONObject databaseAnswer){
		classes = new ArrayList<ClassDefinition>();
		JSONArray clases = databaseAnswer.getJSONArray("classes");
		
		for(int i=0;i<clases.length();i++){
			JSONObject object = new JSONObject();
			
			JSONObject objectSon = clases.getJSONObject(i);
			ClassDefinition cdf = new ClassDefinition(objectSon.keys().next());
			
			JSONObject atrributes = new JSONObject();
			atrributes = objectSon.getJSONObject(objectSon.keys().next());//ebbe már csak az attribútumok vannak
			
			Iterator attnames = atrributes.keys();
			while(attnames.hasNext()){
				String attnam = (String)attnames.next();//itt megvannak az attribútumnevek
				
				cdf.getAttributes().put(attnam, (String)atrributes.get(attnam)); //atrributes.get(attnam)   itt az attribútum értékei vannak
			}
			
			classes.add(cdf);
		}
		
		return classes;
	}
	
	public List<Integer> getResults(JSONObject databaseAnswer){
		results = new ArrayList<Integer>();
		JSONArray result = databaseAnswer.getJSONArray("results");
		
		for(int i=0;i<result.length();i++){
			results.add(result.getInt(i));
		}
		
		return results;
	}
}