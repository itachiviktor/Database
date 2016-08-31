package database.queryObject;

import database.LoadedDatabase;
import datastructure.TileMap;

public class From {
	public String map;
	public Select select;

	public From(String map) {
		super();
		this.map = map;
	}
	
	public TileMap execute(LoadedDatabase db){
		if(select != null){
			return select.execute();
		}
		return db.getMap();
	}
}
