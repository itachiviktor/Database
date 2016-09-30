package database.queryObject.create;

import database.InMemoryDatabase;

public class CreateMapTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("seconddb");
		
		Create create = new Create();
		Map map = new Map("sw", db);
		create.setExec(map);
		create.execute();
		System.out.println("createmap done.");

	}

}
