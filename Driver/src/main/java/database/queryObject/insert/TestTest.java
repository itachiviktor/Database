package database.queryObject.insert;

import database.InMemoryDatabase;

public class TestTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		Insert insert = new Insert(db, "og");
		
		insert.makeRoot("Rectangle", "og");
		
		insert.makeChildren("location");
		insert.makeChildren("x", "100");
		insert.makeChildren("y", "200");
		insert.moveToParent();
		insert.makeChildren("size");
		insert.makeChildren("width", "200");
		insert.makeChildren("height", "100");


		insert.execute();
		
		
		db.persist();

	}

}
