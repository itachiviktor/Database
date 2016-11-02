package database.queryObject.delete;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.Where;
import database.queryObject.wherebuilder.WhereBuilder;

public class DeleteBuilder {
	private Delete delete;
	private InMemoryDatabase db;
	private Where where;
	private WhereBuilder whereBuilder;
	
	public DeleteBuilder(InMemoryDatabase db) {
		this.whereBuilder = new WhereBuilder(db);
		this.db = db;
	}
	
	public void createDelete(String object){
		/*object az Mine*/
		this.delete = new Delete(this.db, object);
	}
	
	public void setFrom(String mapName){
		this.delete.setFrom(new From(mapName));
	}
	
	
	public IQueryObject build(){
		if(this.whereBuilder.getNodes().size() == 0){
			this.delete.where = null;
		}else{
			Where where = this.whereBuilder.build();
			this.delete.where = where;
		}
		return this.delete;
	}
	
	public void addOperandPiece(String piece){
		this.whereBuilder.addOperandPiece(piece);
	}
	
	public void addRoundBracket(){
		this.whereBuilder.addRoundBracket();
	}
	
	public void removeRoundBracket(){
		this.whereBuilder.removeRoundBracket();
	}
	
	public void removeAngledBracket(){
		this.whereBuilder.removeAngledBracket();
	}
	
	public void addPointParameter(String value){
		this.whereBuilder.addPointParameter(value);
	}
	
	public void addAngledBracket(){
		this.whereBuilder.addAngledBracket();
	}
		
}