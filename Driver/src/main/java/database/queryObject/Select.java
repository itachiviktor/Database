package database.queryObject;

import java.util.List;

import database.Database;
import datastructure.Instance;

public class Select {
	From from;
	Where where;
	List<Instance> result;
	Database db;
	
	public Select(Database db) {
		this.db = db;
		where = new Where();
		from = new From("asd");
	}
	
	public List<Instance> execute(){
		return where.execute(from.execute(db));
	}
}
