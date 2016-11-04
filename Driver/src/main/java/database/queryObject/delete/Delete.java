package database.queryObject.delete;

import java.util.List;
import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.Where;
import datastructure.Instance;

public class Delete implements IQueryObject{
	public From from;
	public Where where;
	public InMemoryDatabase db;
	
	public String[] selectAttributes;/*selectattributes[0] az mindig classname.*/
	public String selectObject;
	
	public Delete(InMemoryDatabase db, String selectObject) {
		this.db = db;
		this.selectObject = selectObject;
		selectAttributes = selectObject.split("\\.");/*mine.stone.location.x ezt itt táolja darabonként*/
	}
	
	public List<Instance> execute(){
		if(where == null){
			db.getMapByName(from.map).getMap().clear();
		}else{
			List<Instance> deletableObjects = where.execute(from.execute(db));
			List<Instance> database = db.getMapByName(from.map).getMap();
			for(int i=0;i<database.size();i++){
				for(int j=0;j<deletableObjects.size();j++){
					if(database.get(i) == deletableObjects.get(j)){
						
						database.remove(i);
					}
				}
			}
		}
		
		return null;
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
	
}
