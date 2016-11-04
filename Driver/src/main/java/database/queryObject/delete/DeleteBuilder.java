package database.queryObject.delete;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.Where;
import database.queryObject.selectBuild.SelectBuilder;
import datastructure.Instance;

public class DeleteBuilder {
	private Delete delete;
	private InMemoryDatabase db;
	private SelectBuilder selectBuilder;
	
	public DeleteBuilder(InMemoryDatabase db) {
		this.selectBuilder = new SelectBuilder(db);
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
		
		if(this.selectBuilder.getRoot() == null){
			this.delete.where = null;
		}else{
			IQueryObject ob = this.selectBuilder.build();
			List<Instance> inst = ob.execute();
			
			this.delete.where = ob.getWhere();
		}
		
		return this.delete;
	}
	
	public void addRoundedBracket(){
		this.selectBuilder.addRoundedBracket();
	}
	
	public void removeRoundedBracket(){
		this.selectBuilder.removeRoundedBracket();
	}
	
	public void addOp(String op){
		this.selectBuilder.addOp(op);
	}
	
	public void startWhereCondition(){
		this.selectBuilder.addResource(this.delete.selectObject);
		this.selectBuilder.setFrom(this.delete.from.map);
	}
	
	public void addResource(String source){
		this.selectBuilder.addResource(source);
	}	
	
	public void setSelectFrom(String from){
		this.selectBuilder.setFrom(from);
	}
	
	public void setOrderByAttribute(String attribute){
		this.selectBuilder.setOrderByAttribute(attribute);
	}
	
	public void setOrderBySort(String orderSort){
		this.selectBuilder.setOrderBySort(orderSort);
	}
	
	public void setLimit(int number){
		this.selectBuilder.setLimit(number);
	}
}