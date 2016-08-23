package database.queryObject;

import java.util.List;

import database.Database;
import datastructure.Instance;

public class From {
	public String map;
	public Select select;

	public From(String map) {
		super();
		this.map = map;
	}
	
	public List<Instance> execute(Database db){
		if(select != null){
			return select.execute();
		}
		return db.getMap();
	}
}
