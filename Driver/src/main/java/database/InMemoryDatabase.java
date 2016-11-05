package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.OrderBy;
import database.queryObject.OrderBySort;
import database.queryObject.Select;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import datastructure.BooleanPrimitiv;
import datastructure.ClassDefinition;
import datastructure.Instance;
import datastructure.NumberPrimitiv;
import datastructure.StringPrimitiv;
import datastructure.TileMap;
import parser.Parser;

public class InMemoryDatabase {
	
	//private Map<String, List<Instance>> maps;
	
	private List<TileMap> maps;
	
	private List<ClassDefinition> classes;
	
	private String databaseName;
	
	private TileMap memoryMap;/*Ebbe csak ideiglenes objektumokat pakolunk a számolás végett(Ebbe rectangleek
	vagy pointok vannak.)*/
	private int memoryId = 0;/*Itt a memoryMap id kövi értéke.*/
	
	public InMemoryDatabase(String databaseName) {
		/*Ez a konstruktor, amikor a projektben lévő adatbázis JSON-öket kezeljük, ez persze a ritkább*/
		//maps = new HashMap<String, List<Instance>>();
		maps = new ArrayList<TileMap>();
		this.databaseName = databaseName;
		this.memoryMap = new TileMap();
		
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
	
	public InMemoryDatabase(File fileLocation) {
		maps = new ArrayList<TileMap>();
		this.databaseName = fileLocation.getName().split("\\.")[0];
		
		this.memoryMap = new TileMap();
		
		FileInputStream in = null;
		try{
			in = new FileInputStream(fileLocation.getAbsolutePath());
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
		
		JSONArray dbelements = database.getJSONArray(databaseName);
		
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
	
	public JSONObject resultJSON(String query){
		/*Itt a query maga a lekérdezés string. Ezután átadjuk a parserenk, az hívógat.*/
		
		/*Itt végrehajtjuk a lekérdezésobjektumot, amit létrehozott a parser.*/
		
		Select sel = new Select(this, "mine");
		From fr = new From("og");
		Operand op3 = new Operand("mine",true);
		Operand op4 = new Operand(false, this,"50","50");
		Operators oper2 = Operators.COLLIDE;
		WhereLetter let1 = new WhereLetter(op3, op4, oper2);
		
		Where wh = new Where();
		wh.setRoot(let1);
		OrderBy by = new OrderBy("mine.id", OrderBySort.DESC);
		
		sel.setFrom(fr);
		sel.setWhere(wh);
		
		/*a fenti részt a parser végzi, nekünk csak itt ezt az alsót kellene.*/
		List<Instance> ini = sel.execute();
		
		
		JSONObject response = new JSONObject();
		
		JSONArray instancearray = new JSONArray();
		
		/*getmapbynamehez a from értéke kellene*/
		for(Instance inst : getMapByName("og").getMap()){
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
		
		for(int i=0;i<ini.size();i++){
			results.put(ini.get(i).id);
		}
		
		
		response.put("classes", classesarray);
		response.put("instances", instancearray);
		response.put("results", results);
		
		

		/*Csak a lekérdezendő mapről kell információ*/
		JSONObject dbbb = new JSONObject();
		dbbb.put("og", instancearray);
		
		JSONObject cls = new JSONObject();
		cls.put("classes", classesarray);
		
		
		JSONArray maps = new JSONArray();
		maps.put(cls);
		maps.put(dbbb);
		
		
		JSONObject dbname = new JSONObject();
		
		dbname.put(databaseName, maps);
		
		
		System.out.println(dbname);
		
		return response;
	}
	
	public int addMemoryPoint(Select x, Select y){
		Instance a = new NumberPrimitiv("Number", classes, memoryMap);
		a.id = this.memoryId;
		a.setValue((Integer)x.execute().get(0).getValue());
		this.memoryId++;
		this.memoryMap.add(a);
		
		
		Instance b = new NumberPrimitiv("Number", classes, memoryMap);
		b.id = this.memoryId;
		b.setValue((Integer)y.execute().get(0).getValue());
		this.memoryId++;
		this.memoryMap.add(b);
		
		Instance p = new Instance("Point", classes, memoryMap);
		p.id = this.memoryId;
		this.memoryId++;
		p.setAttribute("x", a.id);
		p.setAttribute("y", b.id);
		this.memoryMap.add(p);
		
		
		return this.memoryId - 1;/*visszaadjuk az id-jét a beszúrt elemnek*/
	}
	
	public int addMemoryPoint(int x, Select y){
		Instance a = new NumberPrimitiv("Number", classes, memoryMap);
		a.id = this.memoryId;
		a.setValue(x);
		this.memoryId++;
		this.memoryMap.add(a);
		
		
		Instance b = new NumberPrimitiv("Number", classes, memoryMap);
		b.id = this.memoryId;
		b.setValue((Integer)y.execute().get(0).getValue());
		this.memoryId++;
		this.memoryMap.add(b);
		
		Instance p = new Instance("Point", classes, memoryMap);
		p.id = this.memoryId;
		this.memoryId++;
		p.setAttribute("x", a.id);
		p.setAttribute("y", b.id);
		this.memoryMap.add(p);
		
		
		return this.memoryId - 1;/*visszaadjuk az id-jét a beszúrt elemnek*/
	}
	
	public int addMemoryPoint(Select x, int y){
		Instance a = new NumberPrimitiv("Number", classes, memoryMap);
		a.id = this.memoryId;
		a.setValue((Integer)x.execute().get(0).getValue());
		this.memoryId++;
		this.memoryMap.add(a);
		
		
		Instance b = new NumberPrimitiv("Number", classes, memoryMap);
		b.id = this.memoryId;
		b.setValue(y);
		this.memoryId++;
		this.memoryMap.add(b);
		
		Instance p = new Instance("Point", classes, memoryMap);
		p.id = this.memoryId;
		this.memoryId++;
		p.setAttribute("x", a.id);
		p.setAttribute("y", b.id);
		this.memoryMap.add(p);
		
		
		return this.memoryId - 1;/*visszaadjuk az id-jét a beszúrt elemnek*/
	}
	
	
	public int addMemoryPoint(int x, int y){
		Instance a = new NumberPrimitiv("Number", classes, memoryMap);
		a.id = this.memoryId;
		a.setValue(x);
		this.memoryId++;
		this.memoryMap.add(a);
		
		
		Instance b = new NumberPrimitiv("Number", classes, memoryMap);
		b.id = this.memoryId;
		b.setValue(y);
		this.memoryId++;
		this.memoryMap.add(b);
		
		Instance p = new Instance("Point", classes, memoryMap);
		p.id = this.memoryId;
		this.memoryId++;
		p.setAttribute("x", a.id);
		p.setAttribute("y", b.id);
		this.memoryMap.add(p);
		
		
		return this.memoryId - 1;/*visszaadjuk az id-jét a beszúrt elemnek*/
	}
	
	public int addMemoryRectangle(int x, int y, int width, int height){
		Instance a = new NumberPrimitiv("Number", classes, memoryMap);
		a.id = this.memoryId;
		a.setValue(x);
		this.memoryId++;
		this.memoryMap.add(a);
		
		
		Instance b = new NumberPrimitiv("Number", classes, memoryMap);
		b.id = this.memoryId;
		b.setValue(y);
		this.memoryId++;
		this.memoryMap.add(b);
		
		Instance w = new NumberPrimitiv("Number", classes, memoryMap);
		w.id = this.memoryId;
		w.setValue(width);
		this.memoryId++;
		this.memoryMap.add(w);
		
		Instance h = new NumberPrimitiv("Number", classes, memoryMap);
		h.id = this.memoryId;
		h.setValue(height);
		this.memoryId++;
		this.memoryMap.add(h);
	
		
		Instance p = new Instance("Point", classes, memoryMap);
		p.id = this.memoryId;
		this.memoryId++;
		p.setAttribute("x", a.id);
		p.setAttribute("y", b.id);
		this.memoryMap.add(p);
		
		Instance s = new Instance("Size", classes, memoryMap);
		s.id = this.memoryId;
		this.memoryId++;
		s.setAttribute("width", w.id);
		s.setAttribute("height", h.id);
		this.memoryMap.add(s);
		
		Instance rec = new Instance("Rectangle", classes, memoryMap);
		rec.id = this.memoryId;
		this.memoryId++;
		rec.setAttribute("location", p.id);
		rec.setAttribute("size", s.id);
		this.memoryMap.add(rec);
		
		
		return this.memoryId - 1;
	}
	
	/**
	 * This method clear the memroy instance map. 
	 * */
	public void clearMemoryMap(){
		this.memoryMap.clear();
	}

	
	public TileMap getMemoryMap() {
		return memoryMap;
	}

	public void setMemoryMap(TileMap memoryMap) {
		this.memoryMap = memoryMap;
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
	
	/**
	 * This method writes the memory content to the JSON file.
	 * */
	public void persist(){
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new FileWriter(databaseName + ".json"));
			
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
			
			dbname.put(databaseName, persistentmaps);
			
			out.write(dbname.toString());
			
		}catch (IOException e) {
			
		}finally{
			try{
				if(out != null){
					out.close();
				}
			}catch (IOException e) {
				
			}
		}
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
		for(Instance x : getMapByName("og").getMap()){
			sb.append(x.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	public void checkQueryObject(IQueryObject object){
		/**/
	}
	
	/**
	 * This method calls by the driver
	 * 
	 * */
	public List<Instance> executeQuery(String query){
		Parser parser = new Parser();
		IQueryObject object = parser.parse(this, query);
		
		return object.execute();
	}
	
}