package database.queryObject;

import database.InMemoryDatabase;
import datastructure.Instance;
import datastructure.NumberPrimitiv;
import datastructure.TileMap;

public class CreateInstanceWithoutInserzingToMapTest {
	public static void main(String[] args) {
		
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		
		int number = db.addMemoryRectangle(800, 800, 100, 100);
		
		System.out.println(db.getMapByName("og").get(269).operate(db.getMemoryMap().get(number), Operators.COLLIDE));
		
	}
}
