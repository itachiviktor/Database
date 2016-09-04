package database.queryObject;

import java.util.List;

import database.LoadedDatabase;
import datastructure.Instance;
import datastructure.TileMap;

public class Select {
	From from;
	Where where;
	OrderBy orderby;
	List<Instance> result;
	LoadedDatabase db;
	
	String selectObject;
	String[] selectAttributes;/*selectattributes[0] az mindig classname.*/
	
	public Select(LoadedDatabase db, String selectObject) {
		this.db = db;
		
		this.selectObject = selectObject;
		selectAttributes = this.selectObject.split("\\.");/*mine.stone.location.x ezt itt táolja darabonként*/
		
	}
	
	
	public From getFrom() {
		return from;
	}


	public void setFrom(From from) {
		this.from = from;
	}


	public Where getWhere() {
		return where;
	}


	public void setWhere(Where where) {
		this.where = where;
	}



	public OrderBy getOrderby() {
		return orderby;
	}



	public void setOrderby(OrderBy orderby) {
		this.orderby = orderby;
	}



	public List<Instance> getResult() {
		return result;
	}



	public void setResult(List<Instance> result) {
		this.result = result;
	}



	public List<Instance> execute(){
		return where.execute(from.execute(db));
	}
}
