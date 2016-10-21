package database.queryObject.insert;

import database.InMemoryDatabase;

public class InsertTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		
		Insert insert = new Insert(db, "azeroth");
		
		insert.makeRoot("Mine", "azeroth");
		insert.makeChildren("name", "NagyBany");
		insert.makeChildren("kor", "23");
		
		insert.makeChildren("stone");
		insert.makeChildren("strong", "60");
		insert.makeChildren("location");
		insert.makeChildren("x", "0");
		insert.makeChildren("y", "0");
		
		
		System.out.println(insert.execute());
		db.persist();
		
		for(int i=0;i<db.getMapByName("azeroth").size();i++){
			System.out.println(db.getMapByName("azeroth").get(i));
		}

	}

}
