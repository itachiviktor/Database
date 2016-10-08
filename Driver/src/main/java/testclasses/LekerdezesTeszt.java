package testclasses;

import database.InMemoryDatabase;

public class LekerdezesTeszt {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		
		
		
		System.out.println(db.getMapByName("azeroth").get(7));
		
		
		
		
		
	}
}