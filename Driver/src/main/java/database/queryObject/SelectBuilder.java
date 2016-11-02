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
	}
	
	public void setResultObject(String result){
		if(this.whereBuilder == null){
			this.select = new Select(this.db, result);
		}else{
			this.whereBuilder.setResultObject(result);
		}
		
	}
	
	public void setFrom(String from){
		if(this.whereBuilder == null){
			this.from = new From(from);
			this.select.setFrom(this.from);
			this.whereBuilder = new WhereBuilder(this.db);
		}else{
			this.whereBuilder.setFrom(from);
		}
		
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
	
	public void removeAngledBracket(){
		this.whereBuilder.removeAngledBracket();
	}
	
	public void addPointParameter(String value){
		this.whereBuilder.addPointParameter(value);
	}
	
	public void addPointParameter(Select select){
		this.whereBuilder.addPointParameter(select);
	}
	
	public void addAngledBracket(){
		this.whereBuilder.addAngledBracket();
	}
	
	
	public IQueryObject build(){
		if(this.whereBuilder.getNodes().size() == 0){
			this.select.setWhere(null);
		}else{
			Where where = this.whereBuilder.build();
			this.select.setWhere(where);
		}
		
		return this.select;
	}
	
	public void buildAlSelectAndPutAsOperand(){
		this.whereBuilder.buildAlSelectAndPutAsOperand();
	}
	
	public void createAnAlSelect(){
		this.whereBuilder.createAnAlSelect();
	}
}




















/*SelectBuilder builder = new SelectBuilder(db);
builder.setResultObject("mine");
builder.setFrom("og");
builder.addOperandPiece("mine.id");
builder.addOperandPiece(">=");
builder.createAnAlSelect();
builder.setResultObject("mine.x");
builder.setFrom("og");
builder.addOperandPiece("mine.id");
builder.addOperandPiece("=");
builder.createAnAlSelect();
builder.setResultObject("mine.y");
builder.setFrom("og");
builder.addOperandPiece("mine.id");
builder.addOperandPiece("=");
builder.addOperandPiece("162");

builder.buildAlSelectAndPutAsOperand();


/*builder.setOrderByAttribute("mine.id");
builder.setOrderBySort("desc");

builder.setLimit(2);*/
