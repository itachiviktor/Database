package database.queryObject.delete;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.Where;

public class DeleteBuilder {
	private Delete delete;
	private InMemoryDatabase db;
	private Where where;
	
	public DeleteBuilder(InMemoryDatabase db) {
		this.db = db;
	}
	
	public void createDelete(String object){
		/*object az Mine*/
		this.delete = new Delete(this.db, object);
	}
	
	public void setFrom(String mapName){
		this.delete.setFrom(new From(mapName));
	}
	
	public void setWhere(Where where){
		this.delete.setWhere(where);
	}
	
	public IQueryObject build(){
		return this.delete;
	}
		
}