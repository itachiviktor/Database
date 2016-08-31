package database.queryObject;

import java.util.List;

import database.LoadedDatabase;
import datastructure.Instance;
import datastructure.TileMap;

public class Select {
	From from;
	Where where;
	List<Instance> result;
	LoadedDatabase db;
	
	String selectObject;
	String[] selectAttributes;/*selectattributes[0] az mindig classname.*/
	
	public Select(LoadedDatabase db, String selectObject) {
		this.db = db;
		where = new Where();
		from = new From("asd");
		
		this.selectObject = selectObject;
		selectAttributes = this.selectObject.split("\\.");/*mine.stone.location.x ezt itt táolja darabonként*/
		
	}
	
	public TileMap execute(){
		return where.execute(from.execute(db));
	}
}
