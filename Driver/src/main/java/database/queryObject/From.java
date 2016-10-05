package database.queryObject;

import java.util.List;

import database.InMemoryDatabase;
import database.LoadedDatabase;
import datastructure.Instance;
import datastructure.TileMap;

public class From {
	public String map;
	public Select select;

	public From(String map) {
		super();
		this.map = map;
	}
	
	public List<Instance> execute(InMemoryDatabase db){
		if(select != null){
			return select.execute();
		}
		return db.getMapByName(map).getMap();
	}
}
