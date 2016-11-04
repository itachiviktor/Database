package database.queryObject.update;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.Where;
import datastructure.Instance;

public class Update implements IQueryObject{
	public Where where;
	private Set set;
	private Move move;
	
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
		
		if(this.set != null){
			return set.execute(where.execute(this.instances));
		}else if(this.move != null){
			return move.execute(where.execute(this.instances), this.instances);
		}else{
			return null;
		}
		
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Where getWhere() {
		// TODO Auto-generated method stub
		return null;
	}
}
