package database;

import java.io.FileWriter;
import java.io.IOException;
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
import datastructure.TileMap;

public class LoadedDatabase {

	public int id = 0;
	
	public TileMap map; 
	public List<ClassDefinition> classes;
	
	public ClassDefinition stone;
	public ClassDefinition mine;
	
	public InstanceMaker maker;
	
	private ClassDefinitionProvider classDefinitionProvider;
	
	private int[] zlayer;
	
	public LoadedDatabase() {
		zlayer = new int[100]; /*100 szint lehet. ebben a tömbben tárolom az adott szinten aktuális
		indexet, amit kilehet osztani.*/
		for(int i=0;i<zlayer.length;i++){
			zlayer[i] = 0;
		}
		
		InMemoryDatabase indb = new InMemoryDatabase("db");
		
		maker = new InstanceMaker();
		
		map = new TileMap();
		classes = new ArrayList<ClassDefinition>();
		
		classDefinitionProvider = new ClassDefinitionProvider(classes, indb.getMaps());
		
		
		stone = new ClassDefinition("Stone", indb.getMaps(), classes);
		stone.getAttributes().put("strong", "Number");
		stone.setAttributeDefaultValue("strong", "11");
		
		stone.getAttributes().put("location", "Point");
		
		
		mine = new ClassDefinition("Mine", indb.getMaps(), classes);
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
		p.zlayer = 2;
		p.zindex = zlayer[p.zlayer];
		zlayer[p.zlayer] += 1;

		p.setAttribute("y", y.id);
		p.id = maker.id;
		maker.id += 1;
		
		
		map.add(p);
		
		Instance rect = new Instance("Rectangle", classes, map);
		rect.setAttribute("location", p.id);
		rect.setAttribute("size", p.id);
		rect.id = maker.id;
		rect.zlayer = 2;
		rect.zindex = zlayer[rect.zlayer];
		zlayer[rect.zlayer] += 1;
		
		maker.id += 1;
		map.add(rect);
		
		
		Instance ston = new Instance("Stone", classes, map);
		ston.setAttribute("strong", x.id);
		ston.setAttribute("location", p.id);
		ston.id = maker.id;
		maker.id += 1;
		ston.zlayer = 2;
		ston.zindex = zlayer[ston.zlayer];
		zlayer[ston.zlayer] += 1;
		
		map.add(ston);
		
		
		Instance miyne = new Instance("Mine", classes, map);
		miyne.setAttribute("kor", x.id);
		miyne.setAttribute("stone", ston.id);
		miyne.setAttribute("name", szov.id);
		miyne.id = maker.id;
		maker.id += 1;
		miyne.zlayer = 0;
		miyne.zindex = zlayer[miyne.zlayer];
		zlayer[miyne.zlayer] += 1;
		
		map.add(miyne);
		
						
	}
	
	public JSONObject resultJSON(){
		JSONObject response = new JSONObject();
		
		JSONArray instancearray = new JSONArray();
		
		for(Instance inst : map.getMap()){
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
		
		
		JSONObject dbb = new JSONObject();
		dbb.put("azeroth", instancearray);

		
		JSONObject dbbb = new JSONObject();
		dbbb.put("og", instancearray);
		
		JSONObject cls = new JSONObject();
		cls.put("classes", classesarray);
		
		
		JSONArray maps = new JSONArray();
		maps.put(cls);
		maps.put(dbb);
		maps.put(dbbb);
		
		
		JSONObject dbname = new JSONObject();
		
		dbname.put("firstdatabase", maps);
		
	//	System.out.println(response);
		
		
	
		
		return response;
	}


	public TileMap getMap() {
		return map;
	}

	public void setMap(TileMap map) {
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
		for(Instance x : this.map.getMap()){
			sb.append(x.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}