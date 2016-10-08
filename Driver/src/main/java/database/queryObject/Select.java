package database.queryObject;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import database.LoadedDatabase;
import datastructure.Instance;
import datastructure.TileMap;

public class Select {
	public From from;
	public Where where;
	public OrderBy orderby;
	public List<Instance> result;
	public InMemoryDatabase db;
	public int limit = -1;
	
	String selectObject;
	String[] selectAttributes;/*selectattributes[0] az mindig classname.*/
	
	public Select(InMemoryDatabase db, String selectObject) {
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
		if(limit > -1){
			/*Ha nincs limit a lekérdezésben, akkor az alapértelmezett értéke -1.*/
			List<Instance> executed = orderby.execute(where.execute(from.execute(db)));
			List<Instance> result = new ArrayList<Instance>();
			/*Itt az a lényeg, hogy csak annyi elemet rakunk bele a lekérdezés eredményébe,
			 amennyit a limit megenged.*/
			for(int i=0;i<executed.size();i++){
				if(i < limit){
					result.add(executed.get(i));
				}
			}
			return result;
		}
		if(orderby != null){
			return orderby.execute(where.execute(from.execute(db)));
		}else{
			return where.execute(from.execute(db));
		}
		
		
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
