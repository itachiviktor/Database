package database;

import java.util.List;

import datastructure.Instance;
import datastructure.TileMap;

public class Fut {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		TileMap map = db.getMapByName("azeroth");
		
		
		for(int i=0;i<map.size();i++){
			System.out.println(map.get(i));
		}
	}
}