package database.queryObject.drop;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;

public class DropBuilder {
	private Drop drop;
	
	public DropBuilder(InMemoryDatabase db) {
		this.drop = new Drop(db);
	}
	
	public void setType(String type){
		this.drop.setType(type);
	}
	
	public void setName(String name){
		this.drop.setName(name);
	}
	
	public IQueryObject build(){
		return this.drop;
	}
}