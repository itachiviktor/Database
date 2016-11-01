package database.queryObject.drop;

import database.InMemoryDatabase;
import database.queryObject.create.CreateBuilder;

public class DropTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		DropBuilder builder = new DropBuilder(db);
		builder.setType("database");
		builder.setName("Game");
		builder.build().execute();
		System.out.println("kész...");
	}
}