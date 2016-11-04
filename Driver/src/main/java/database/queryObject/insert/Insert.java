package database.queryObject.insert;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.Where;
import datastructure.Instance;
import datastructure.InstanceMaker;

public class Insert implements IQueryObject{
	private String into; /*azeroth*/
	private Values values;
	private Integer layer;/*opcionális*/
	
	private String className;/*Insert Mine*/
	
	
	private InMemoryDatabase db;
	
	public InstanceMaker maker;
	private String mapName;
	
	public TreeBuilder builder;
	
	public Insert(InMemoryDatabase db, String mapName) {
		this.db = db;
		this.mapName = mapName;
		
		this.maker = db.getMapByName(mapName).getMaker();
		this.builder = new TreeBuilder(db, maker);
	}

	public List<Instance> execute() {
		/*Itt , hogy melyik mapre insertelünk, azt a Connection objektumtól kell elkérni.*/
		Instance instance = null;
		
		/*Szért kell, hogy a fentartott memory Insatnce pool törlődjön.*/
		db.clearMemoryMap();
		
		values = builder.root.getValue();
		if(layer != null){
			values.execute(layer);
		}else{
			values.execute(0);
		}
		return null;
		//return values.execute(param, instance, db.getMaps().get("azeroth"), this);/*a létrehozott példány id-je*/
	}
	
	public void makeRoot(String className, String insertedMapName){
		this.builder.makeRoot(className, insertedMapName);
	}
	
	public void makeChildren(String attributeName, String primitiveValue){
		this.builder.makeChildren(attributeName, primitiveValue);
	}
	
	public void makeChildren(String attributeName){
		this.builder.makeChildren(attributeName);
		
	}
	
	public TreeNode getActualTreeNode(){
		return this.builder.actualNode;
	}
	
	public void moveToParent(){
		this.builder.moveToParent();
		
	}
	
	/**
	 * This method gives the id of the new instance.*/
	public int getTheNewInstanceId(){
		return 0;
	}

	public String getInto() {
		return into;
	}

	public void setInto(String into) {
		this.into = into;
	}

	public Values getValues() {
		return values;
	}

	public void setValues(Values values) {
		this.values = values;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}


	public InMemoryDatabase getDb() {
		return db;
	}

	public void setDb(InMemoryDatabase db) {
		this.db = db;
	}

	public Where getWhere() {
		// TODO Auto-generated method stub
		return null;
	}
}