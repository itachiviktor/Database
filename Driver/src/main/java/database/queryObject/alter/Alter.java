package database.queryObject.alter;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import datastructure.ClassDefinition;
import datastructure.Instance;

public class Alter implements IQueryObject{
	private IAlterType alterType;
	private String className;
	
	private InMemoryDatabase db;
	
	public Alter(InMemoryDatabase db, String className) {
		this.db = db;
		this.className = className;
	}
	
	public List<Instance> execute(){
		alterType.execute(this.className);
		return null;
	}


	public IAlterType getAlterType() {
		return alterType;
	}

	public void setAlterType(IAlterType alterType) {
		this.alterType = alterType;
	}
	
	
}
