package database.queryObject.insert;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.create.Executable;
import datastructure.BooleanPrimitiv;
import datastructure.ClassDefinition;
import datastructure.Instance;
import datastructure.InstanceMaker;
import datastructure.NumberPrimitiv;
import datastructure.StringPrimitiv;
import datastructure.TileMap;

public class Values{
	List<Integer> values;
	/*ezek a pointereket tartalmazzák a létrehozott objektumokra*/
	
	String stringPrimitive;
	Number numberPrimitive;
	Boolean booleanPrimitive;
	
	List<Values> children;
	List<String> childAttributeNames;/*Ez a kettő párban van, tehát a fenti lekérdezés, az itt azonos idnexen 
	lévő attribútumot adja.A childAttributeNames-t s Treeből töltjük fel.*/
	
	String mapName;
	
	String className;
	
	public ClassDefinition ownClass;
	
	public InMemoryDatabase db;
	
	public TileMap instances;
	public List<ClassDefinition> classes;
	
	public InstanceMaker maker;
	
	public Values(String mapName, InMemoryDatabase db, InstanceMaker maker) {
		values = new ArrayList<Integer>();
		
		this.children = new ArrayList<Values>();
		this.childAttributeNames = new ArrayList<String>();
		
		this.db = db;
		
		this.classes = db.getClasses();
		
		
		
		this.instances = db.getMapByName(mapName);
		//this.instances = db.getMaps().get(mapName);
		this.maker = maker;

		this.mapName = mapName;
	}
	
	public int execute(){
		/*Itt először feltöltjük a default értékeket.*/
		
		
		
		if(stringPrimitive != null){
			StringPrimitiv prim = maker.makeString(this.stringPrimitive, classes, instances); 
			return prim.id;
		}else if(numberPrimitive != null){
			NumberPrimitiv prim = maker.makeNumber(this.numberPrimitive, classes, instances);
			return prim.id;
		}else if(booleanPrimitive != null){
			BooleanPrimitiv prim = maker.makeBoolean(this.booleanPrimitive, classes, instances);
			return prim.id;
		}else{
			Instance inst = new Instance(className, classes, instances);
			for(int i=0;i<children.size();i++){
				
				values.add(children.get(i).execute());
				/*Itt még össze kell rakni egy Instancet és hozzáadni a listához, majd az idjével visszatérni.*/
				
				inst.setAttribute(this.childAttributeNames.get(i), values.get(i));
				//System.out.println("attributeName: " + this.childAttributeNames.get(i) + " values: " + values.get(i));
			}
			
			/*FIGYELEM, még be kell állítani azokat az attribútumokat is melyek a defaultra játszanak.*/
			

			
			for(String x : ownClass.defaultValuesInMap.keySet()){
				boolean isDefault = true;
				//x az egy attributename
				
				for(int i=0;i<childAttributeNames.size();i++){
					if(x.equals(childAttributeNames.get(i))){
						isDefault = false;
						break;
					}
				}
				
				if(isDefault){
					inst.setAttribute(x, ownClass.defaultValuesInMap.get(x).id);
				}	
			}
			
			
			
			inst.id = maker.id;
			maker.id += 1;
			inst.zlayer = 88;/*Ez a LAYERtől függ*/
			inst.zindex = maker.zlayer[inst.zlayer];
			maker.zlayer[inst.zlayer] += 1;
			
			instances.add(inst);
			return inst.id;
			
		}
	}
}