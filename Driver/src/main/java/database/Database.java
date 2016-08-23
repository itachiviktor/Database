package database;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import datastructure.BooleanPrimitiv;
import datastructure.ClassDefinition;
import datastructure.ClassDefinitionProvider;
import datastructure.Instance;
import datastructure.InstanceMaker;
import datastructure.NumberPrimitiv;
import datastructure.StringPrimitiv;

public class Database {

	public int id = 0;
	
	public List<Instance> map; 
	public List<ClassDefinition> classes;
	
	public ClassDefinition stone;
	public ClassDefinition mine;
	
	public InstanceMaker maker;
	
	private ClassDefinitionProvider classDefinitionProvider;
	
	public Database() {
		maker = new InstanceMaker();
		
		map = new ArrayList<Instance>();
		classes = new ArrayList<ClassDefinition>();
		
		classDefinitionProvider = new ClassDefinitionProvider(classes);
		
		
		stone = new ClassDefinition("Stone");
		stone.getAttributes().put("strong", "Number");
		stone.getAttributes().put("location", "Point");
		
		
		mine = new ClassDefinition("Mine");
		mine.getAttributes().put("kor", "Number");
		mine.getAttributes().put("stone", "Stone");
		mine.getAttributes().put("name", "String");
		
		
		classes.add(stone);
		classes.add(mine);
		
		
		StringPrimitiv szov = maker.makeString("körteee", classes, map);
	
		
		NumberPrimitiv x = maker.makeNumber(12, classes, map);

		
		NumberPrimitiv y = maker.makeNumber(321, classes, map);
		
		BooleanPrimitiv bo = maker.makeBoolean(true, classes, map);
		
		Instance p = new Instance("Point", classes, map);
		p.setAttribute("x", x.id);
		

		p.setAttribute("y", y.id);
		p.id = maker.id;
		maker.id += 1;
		
		
		map.add(p);
		
		Instance rect = new Instance("Rectangle", classes, map);
		rect.setAttribute("location", p.id);
		rect.setAttribute("size", p.id);
		rect.id = maker.id;
		
		maker.id += 1;
		map.add(rect);
		
		
		Instance ston = new Instance("Stone", classes, map);
		ston.setAttribute("strong", x.id);
		ston.setAttribute("location", p.id);
		ston.id = maker.id;
		maker.id += 1;
		map.add(ston);
		
		
		Instance miyne = new Instance("Mine", classes, map);
		miyne.setAttribute("kor", x.id);
		miyne.setAttribute("stone", ston.id);
		miyne.setAttribute("name", szov.id);
		miyne.id = maker.id;
		maker.id += 1;
		map.add(miyne);
		
						
	}
	
	public JSONObject resultJSON(){
		JSONObject response = new JSONObject();
		
		JSONArray instancearray = new JSONArray();
		
		for(Instance inst : map){
			JSONObject ob = new JSONObject();
			ob.put(String.valueOf(inst.id),inst.getJSONRepresentation());
			instancearray.put(ob);
		}
		

		JSONArray classesarray = new JSONArray();
		
		for(ClassDefinition dzs : classes){
			JSONObject ob = new JSONObject();
			ob.put(dzs.className, dzs.getJSONrepresentation());
			classesarray.put(ob);
		}
		
		JSONArray results = new JSONArray();
		
		results.put(1);
		results.put(5);
		
		response.put("classes", classesarray);
		response.put("instances", instancearray);
		response.put("results", results);
		
		
		return response;
	}

	public List<Instance> getMap() {
		return map;
	}

	public void setMap(List<Instance> map) {
		this.map = map;
	}

	public List<ClassDefinition> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassDefinition> classes) {
		this.classes = classes;
	}
	
	public String classDefinitionsString(){
		StringBuilder sb = new StringBuilder();
		for(ClassDefinition x : this.classes){
			sb.append(x.toString());
		}
		return sb.toString();
	}
	
	public String mapInstancesString(){
		StringBuilder sb = new StringBuilder();
		for(Instance x : this.map){
			sb.append(x.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}