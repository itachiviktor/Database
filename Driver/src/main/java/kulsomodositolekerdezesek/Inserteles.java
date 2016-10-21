package kulsomodositolekerdezesek;

import database.InMemoryDatabase;
import database.queryObject.create.AttributeDescriptor;
import database.queryObject.create.Class;
import database.queryObject.create.Create;
import database.queryObject.insert.TreeBuilder;
import database.queryObject.insert.Values;
import datastructure.InstanceMaker;

public class Inserteles {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		
		InstanceMaker maker = db.getMapByName("azeroth").getMaker();
		
		TreeBuilder builder = new TreeBuilder(db, maker);
		
		
		
		builder.makeRoot("Entity", "azeroth");
		builder.makeChildren("x", "600");
		builder.makeChildren("y", "500");
		builder.makeChildren("width", "30");
		builder.makeChildren("height", "40");
		
		Values value = builder.root.getValue();
		value.execute(0);
		
		db.persist();
		
		
		System.out.println("lefutott");

	}

}
