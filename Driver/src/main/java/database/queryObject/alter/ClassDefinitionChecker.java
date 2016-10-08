package database.queryObject.alter;

import java.util.List;

import database.InMemoryDatabase;
import datastructure.Instance;
import datastructure.TileMap;

/**
 * This class examines that the database has an existing instance from this class.
 * @author kiss
 *
 */
public class ClassDefinitionChecker {
	private InMemoryDatabase db;

	public ClassDefinitionChecker(InMemoryDatabase db) {
		super();
		this.db = db;
	}
	
	public boolean hasAnExistingInstanceInDB(String className){
		List<TileMap> tileMaps = db.getMaps();
		boolean existingInstance = false;
		
		outer:for(int i=0;i<tileMaps.size();i++){
			List<Instance> map = tileMaps.get(i).getMap();
			
			for(int j=0;j<map.size();j++){
				if(map.get(j).className.equals(className)){
					existingInstance = true;
					break outer;
				}
			}
		}
		
		return existingInstance;
	}
}
