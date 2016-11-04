package database.queryObject.selectBuild;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.OrderBy;
import database.queryObject.OrderBySort;

public class SelectBuilder {
	private SelectNode actualSelect;
	private SelectNode root;
	private InMemoryDatabase db;
	private int rootNumber = 0;
	
	public SelectBuilder(InMemoryDatabase db) {
		this.db = db;
	}
	
	public void addResource(String resource){
		SelectNode parent = actualSelect;
		actualSelect = new SelectNode(db, this);
		actualSelect.setParent(parent);
		
		if(parent != null){
			parent.addSelect(actualSelect);
		}
	
		if(rootNumber == 0){
			root = actualSelect;
		}
		rootNumber++;
		
		
		actualSelect.setSource(resource);
	}
	
	public void setFrom(String from){
		actualSelect.setFrom(from);
	}
	
	public void addRoundedBracket(){
		actualSelect.addOp("(");
	}
	
	public void removeRoundedBracket(){
		actualSelect.addOp(")");
	}
	
	public void addOp(String op){
		if(op.equals("}")){
			actualSelect = actualSelect.getParent();
		}else{
			actualSelect.addOp(op);
		}
	}
	
	public IQueryObject build(){
		return root.build();
	}

	public SelectNode getActualSelect() {
		return actualSelect;
	}

	public void setActualSelect(SelectNode actualSelect) {
		this.actualSelect = actualSelect;
	}

	public SelectNode getRoot() {
		return root;
	}
	
	public void setOrderByAttribute(String attribute){
		this.actualSelect.setOrderByAttribute(attribute);
	}
	
	public void setOrderBySort(String orderSort){
		this.actualSelect.setOrderBySort(orderSort);
	}
	
	public void setLimit(int number){
		this.actualSelect.setLimit(number);
	}
		
}