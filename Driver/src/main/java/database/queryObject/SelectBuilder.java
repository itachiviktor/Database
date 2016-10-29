package database.queryObject;

import database.InMemoryDatabase;
import database.queryObject.wherebuilder.WhereBuilder;

public class SelectBuilder {
	private InMemoryDatabase db;
	private WhereBuilder whereBuilder;
	private Select select;
	private From from;
	private OrderBy by;
	
	public SelectBuilder(InMemoryDatabase db) {
		this.db = db;
		this.whereBuilder = new WhereBuilder();
	}
	
	public void setResultObject(String result){
		this.select = new Select(this.db, result);
	}
	
	public void setFrom(String from){
		this.from = new From(from);
		this.select.setFrom(this.from);
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
	
	public void setOrderByAttribute(String attribute){
		by = new OrderBy(attribute);
		this.select.setOrderby(this.by);
	}
	
	public void setOrderBySort(String orderSort){
		if(orderSort.equalsIgnoreCase("ASC")){
			by.setSort(OrderBySort.ASC);
		}else if(orderSort.equalsIgnoreCase("DESC")){
			by.setSort(OrderBySort.DESC);
		}
		
		this.select.setOrderby(this.by);
	}
	
	public void setLimit(int number){
		this.select.setLimit(number);
	}
	
	
	public IQueryObject build(){
		Where where = this.whereBuilder.build();
		this.select.setWhere(where);
		
		return this.select;
	}
}
