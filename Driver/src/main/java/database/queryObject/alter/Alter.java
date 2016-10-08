package database.queryObject.alter;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;

public class Alter {
	private IAlterType alterType;
	private String className;
	
	private InMemoryDatabase db;
	
	public Alter(InMemoryDatabase db, String className) {
		this.db = db;
		this.className = className;
	}
	
	public void execute(){
		alterType.execute(this.className);
	}


	public IAlterType getAlterType() {
		return alterType;
	}

	public void setAlterType(IAlterType alterType) {
		this.alterType = alterType;
	}
	
	
}
