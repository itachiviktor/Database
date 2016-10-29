package database.queryObject.create;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import datastructure.ClassDefinition;
import datastructure.ClassDefinitionProvider;
import datastructure.Instance;
import datastructure.NumberPrimitiv;
import datastructure.TileMap;

public class Database implements Executable{
	private String name;
	
	private List<ClassDefinition> classes;/*ez azért kell, hogy az alap class definíciókat
	tároljam valahol, és adatbázis létrehozáskor ezek már benne legyenek.*/
	
	private List<TileMap> maps;
	
	public Database(String name) {
		this.name = name;
		maps = new ArrayList<TileMap>();
		
		classes = new ArrayList<ClassDefinition>();
		ClassDefinitionProvider classProvider = new ClassDefinitionProvider(classes, null);
		
	}
	
	public void execute(){
		try {
			FileWriter file = new FileWriter(this.name + ".json");
			
			File theDir = new File(this.name);
			theDir.mkdir();
			
			JSONArray classesarray = new JSONArray();
			
			for(ClassDefinition dzs : classes){
				JSONObject ob = new JSONObject();
				ob.put(dzs.className, dzs.getJSONrepresentation());
				classesarray.put(ob);
			}
			
			JSONObject cls = new JSONObject();
			cls.put("classes", classesarray);
			
			JSONArray persistentmaps = new JSONArray();
			persistentmaps.put(cls);
			

			for(int i=0;i<maps.size();i++){
				JSONArray instancearray = new JSONArray();
				for(Instance inst : maps.get(i).getMap()){
					JSONObject ob = new JSONObject();
					ob.put(String.valueOf(inst.id),inst.getJSONRepresentation());
					instancearray.put(ob);
				}
				JSONObject obj = new JSONObject();
				obj.put(maps.get(i).getMapName(), instancearray);
				persistentmaps.put(obj);
				
			}
			
			JSONObject dbname = new JSONObject();
			
			dbname.put(this.name, persistentmaps);
			
			file.write(dbname.toString());
			
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertAttribute() {
		// TODO Auto-generated method stub
		
	}

	public void addAttributeParameter(String type) {
		// TODO Auto-generated method stub
		
	}
}
