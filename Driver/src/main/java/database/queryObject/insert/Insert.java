package database.queryObject.insert;

import database.InMemoryDatabase;
import database.queryObject.create.Executable;
import datastructure.Instance;
import datastructure.InstanceMaker;

public class Insert{
	private String into; /*azeroth*/
	private Values values;
	private Integer layer;/*opcionális*/
	
	private String className;/*Insert Mine*/
	
	
	private InMemoryDatabase db;
	
	public InstanceMaker maker;
	
	public Insert(InMemoryDatabase db, InstanceMaker maker) {
		this.db = db;
		this.maker = maker;
		
	}

	public int execute() {
		/*Itt , hogy melyik mapre insertelünk, azt a Connection objektumtól kell elkérni.*/
		Instance instance = null;
	
		return 1;
		//return values.execute(param, instance, db.getMaps().get("azeroth"), this);/*a létrehozott példány id-je*/
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
	
	
	
}
