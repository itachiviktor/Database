package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import datastructure.BooleanPrimitiv;
import datastructure.ClassDefinition;
import datastructure.Instance;
import datastructure.NumberPrimitiv;
import datastructure.StringPrimitiv;
import datastructure.TileMap;

public class InMemoryDatabase {
	
	//private Map<String, List<Instance>> maps;
	
	private List<TileMap> maps;
	
	private List<ClassDefinition> classes;
	
	public InMemoryDatabase(String databaseName) {
		//maps = new HashMap<String, List<Instance>>();
		maps = new ArrayList<TileMap>();
		
		FileInputStream in = null;
		try{
			in = new FileInputStream(databaseName + ".json");
			JSONTokener tokener = new JSONTokener(in);
			JSONObject root = new JSONObject(tokener);
			
			getInstances(root);
			
		}catch (IOException e) {
			
		}finally{
			try{
				if(in != null){
					in.close();
				}
			}catch (IOException e) {
				
			}
		}
	}
	
	public TileMap getMapByName(String mapName){
		for(int i=0;i<maps.size();i++){
			if(mapName.equals(maps.get(i).getMapName())){
				return maps.get(i);
			}
		}
		
		return null;
	}
	
	private void getInstances(JSONObject database){
		
		JSONArray dbelements = database.getJSONArray("firstdatabase");
		
		for(int j=0;j<dbelements.length();j++){
			JSONObject mapp = dbelements.getJSONObject(j);
			
			JSONArray instances = mapp.getJSONArray(mapp.keys().next());
			
			
			if(j > 0){
				//List<Instance> map = new ArrayList<Instance>();
				TileMap map = new TileMap();
				int idValue = 0;
				
				for(int i=0;i<instances.length();i++){
					JSONObject object = instances.getJSONObject(i);
								
					idValue = Integer.parseInt(object.keys().next()); /*Ebbe az aktuális elem kulcsa van*/
					
					JSONObject objectSon = instances.getJSONObject(i).getJSONObject(object.keys().next()); //itt megvannak az adott id-hez tartozó attribútum:érték párok
					
					Iterator it = objectSon.keys();
					String className = objectSon.getString("className");
					int zindex = objectSon.getInt("zindex");
					int zlayer = objectSon.getInt("zlayer");
					
					if(className.equals("String")){
						StringPrimitiv inst = new StringPrimitiv("String", classes, map);
						inst.setAttribute((String)objectSon.get("value"));
						inst.id = idValue;
						inst.zindex = zindex;
						inst.zlayer = zlayer;
						map.add(inst);
					}else if(className.equals("Number")){
						NumberPrimitiv inst = new NumberPrimitiv("Number", classes, map);
						inst.setAttribute((Number)objectSon.get("value"));
						inst.id = idValue;
						inst.zindex = zindex;
						inst.zlayer = zlayer;
						map.add(inst);
					}else if(className.equals("Boolean")){
						BooleanPrimitiv inst = new BooleanPrimitiv("Boolean", classes, map);
						inst.setAttribute((Boolean)objectSon.get("value"));
						inst.id = idValue;
						inst.zindex = zindex;
						inst.zlayer = zlayer;
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
						inst.zindex = zindex;
						inst.zlayer = zlayer;
						map.add(inst);
					}
				}
				
				map.setMapName(mapp.keys().next());
				map.getMaker().id = map.size();/*Itt állítjuk be a mapekre, hogy mi a következő id , amit kioszthat.*/
				maps.add(map);
				
				//maps.put(mapp.keys().next(), map);
			}else{
				classes = new ArrayList<ClassDefinition>();
				
				
				for(int i=0;i<instances.length();i++){
					JSONObject object = new JSONObject();
					
					JSONObject objectSon = instances.getJSONObject(i);
					ClassDefinition cdf = new ClassDefinition(objectSon.keys().next(), maps, classes);
					
					JSONObject atrributes = new JSONObject();
					atrributes = objectSon.getJSONObject(objectSon.keys().next());//ebbe már csak az attribútumok vannak
					
					Iterator attnames = atrributes.keys();
					while(attnames.hasNext()){
						String attnam = (String)attnames.next();//itt megvannak az attribútumnevek
						
						
						String attr = (String)atrributes.get(attnam);
						String[] def = attr.split(" ");
						
						if(def.length > 1){
							cdf.defaultValues.put(attnam, def[2]);
							cdf.getAttributes().put(attnam, def[0]);
						}else{
							cdf.getAttributes().put(attnam, (String)atrributes.get(attnam));//atrributes.get(attnam)   itt az attribútum értékei vannak
						}
					}
					
					classes.add(cdf);
				}
			}
			
		}
		
	}

	

	public List<ClassDefinition> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassDefinition> classes) {
		this.classes = classes;
	}

	public List<TileMap> getMaps() {
		return maps;
	}

	public void setMaps(List<TileMap> maps) {
		this.maps = maps;
	}
	
	
	
	
}