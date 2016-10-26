package database.queryObject.insert;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;

public class InsertBuilder {
	private Insert insert;
	private InMemoryDatabase db;
	
	private String mapName;
	
	public InsertBuilder(InMemoryDatabase db) {
		this.db = db;
	}
	
	public void createInsert(String mapName){
		this.insert = new Insert(this.db, mapName);
		this.mapName = mapName;
	}
	
	public void makeRoot(String className){
		this.insert.makeRoot(className, this.mapName);
	}
	
	public void makeChildren(String attributeName){
		this.insert.makeChildren(attributeName);
	}
	
	public void makeChildren(String attributeName, String primitiveValue){
		this.insert.makeChildren(attributeName, primitiveValue);
	}
	
	public void moveToParent(){
		this.insert.moveToParent();
	}
	
	public IQueryObject build(){
		return this.insert;
	}
}
