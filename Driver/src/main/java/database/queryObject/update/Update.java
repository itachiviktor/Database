package database.queryObject.update;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.Where;
import datastructure.Instance;

public class Update {
	public Where where;
	public Set set;
	
	public String mapName;
	public InMemoryDatabase db;
	public List<Instance> instances;
	
	public Update(String mapName, InMemoryDatabase db) {
		this.db = db;
		this.mapName = mapName;
		this.instances = db.getMapByName(mapName).getMap();
	}
	
	public List<Instance> execute(){
		/*Itt a visszatérési érték nem void, hanem a módosított elemek listája kell legyen majd.*/
		return set.execute(where.execute(this.instances));
	}
}
