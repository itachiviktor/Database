package database.queryObject.create;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.insert.TreeBuilder;
import database.queryObject.insert.TreeNode;
import database.queryObject.insert.Values;
import datastructure.InstanceMaker;

/**
 * In this class I create a db and a map, and give this map values.And create Classdefinitions.
 * */
public class CreateDatabseTest {
	public static void main(String[] args) {
		Create creat = new Create();
		Database database = new Database("thirddb");
		creat.setExec(database);
		creat.execute();
		
		InMemoryDatabase db = new InMemoryDatabase("thirddb");
		
		Create createMap = new Create();
		Map map = new Map("Undercity", db);
		createMap.setExec(map);
		createMap.execute();
		
		InstanceMaker maker = db.getMapByName("Undercity").getMaker();
		
		
		
		TreeBuilder builder = new TreeBuilder(db, maker);
		
		/*for(int i=0;i<classes.size();i++){
			System.out.println(classes.get(i));
		}*/
		
		Create create = new Create();
		
		Class cl = new Class("Bomb", db);
		AttributeDescriptor color = new AttributeDescriptor();
		color.attrName = "color";
		color.attrType = "String";
		color.defValue = "yellow";
		
		cl.getAttributes().add(color);
		
		create.setExec(cl);
		create.execute();
		
		
		
		TreeBuilder masik = new TreeBuilder(db, maker);
		masik.makeRoot("Bomb", "Undercity");
		masik.makeChildren("color", "Red"); /*Ha nemadjuk meg az attribútumot, akkor a default lesz ott*/
		
		
		/*builder.makeRoot("Mine");
		builder.makeChildren("name", "ValamiName");
		builder.makeChildren("kor", "1111");
		
		builder.makeChildren("stone");
		builder.makeChildren("strong", "11");
		builder.makeChildren("location");
		builder.makeChildren("x", "123");
		builder.makeChildren("y", "321");*/
		
		//System.out.println(builder.toString());
		
		
		
		//List<TreeNode> result = builder.root.getSortedTreeList();/*Ebben a listában megvan sorrenben, hogy hogyan
		//hajsta végre.*/
		//result.add(0, builder.root);
		/*innentől van meg.*/
		
		
		
		/*for(int i=0;i<result.size();i++){
			System.out.println(result.get(i).ownDetails());
		}*/
		
		/*Values value = builder.root.getValue();
		value.execute();*/
		
		Values v = masik.root.getValue();
		v.execute(0);
		
		
		db.persist();
		
		System.out.println("done");
		
	}
}
