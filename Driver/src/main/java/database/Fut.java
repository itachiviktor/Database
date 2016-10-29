package database;

import java.io.File;
import java.util.List;

import database.queryObject.create.AttributeDescriptor;
import database.queryObject.create.Class;
import database.queryObject.create.Create;
import database.queryObject.create.Database;
import database.queryObject.create.Map;
import database.queryObject.insert.TreeBuilder;
import database.queryObject.insert.TreeNode;
import database.queryObject.insert.Values;
import datastructure.Instance;
import datastructure.InstanceMaker;
import datastructure.TileMap;

public class Fut {

	public static void main(String[] args) {
		
		InMemoryDatabase db = new InMemoryDatabase("db");
		//TileMap map = db.getMapByName("azeroth");
		
		//InstanceMaker maker = db.getMapByName("azeroth").getMaker();
		
		//TreeBuilder builder = new TreeBuilder(db, maker);
		
		/*for(int i=0;i<classes.size();i++){
			System.out.println(classes.get(i));
		}*/
		
		
		
		//TreeBuilder masik = new TreeBuilder(db, maker);
		//masik.makeRoot("Bomb");
		//masik.makeChildren("color", "Red"); /*Ha nemadjuk meg az attribútumot, akkor a default lesz ott*/
		
		
		//builder.makeRoot("Mine");
		//builder.makeChildren("name", "ValamiName");
		//builder.makeChildren("kor", "1111");
		
		//builder.makeChildren("stone");
		//builder.makeChildren("strong", "11");
		//builder.makeChildren("location");
		//builder.makeChildren("x", "123");
		//builder.makeChildren("y", "321");
		
		//System.out.println(builder.toString());
		
		
		
		/*List<TreeNode> result = builder.root.getSortedTreeList();/*Ebben a listában megvan sorrenben, hogy hogyan
		hajsta végre.*/
		//result.add(0, builder.root);
		/*innentől van meg.*/
		
		
		
		/*for(int i=0;i<result.size();i++){
			System.out.println(result.get(i).ownDetails());
		}*/
		
	/*	Values value = builder.root.getValue();
		value.execute();
		
		Values v = masik.root.getValue();
		v.execute();
		*/
		
	/*	for(int i=0;i<db.getMapByName("azeroth").size();i++){
			System.out.println(db.getMapByName("azeroth").get(i));
		}*/
		
		for(int i=0;i<db.getClasses().size();i++){
			System.out.println(db.getClasses().get(i));
		}
		
		db.persist();
		System.out.println("lefutott");
	}
}