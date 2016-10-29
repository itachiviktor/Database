package database.queryObject.create;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import datastructure.Instance;
import datastructure.TileMap;

public class Map implements Executable{
	private String name;
	
	private InMemoryDatabase memory;
	
	public Map(String name, InMemoryDatabase memory) {
		this.name = name;
		this.memory = memory;
	}
	
	public void execute(){
		TileMap map = new TileMap();
		map.setMapName(name);
		
		memory.getMaps().add(map);
		
		memory.persist();
	}

	public void insertAttribute() {
		// TODO Auto-generated method stub
		
	}

	public void addAttributeParameter(String type) {
		// TODO Auto-generated method stub
		
	}
}
