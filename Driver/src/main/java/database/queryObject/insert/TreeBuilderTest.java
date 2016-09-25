package database.queryObject.insert;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.create.AttributeDescriptor;
import database.queryObject.create.Class;
import database.queryObject.create.Create;
import database.queryObject.create.Database;
import database.queryObject.create.Map;
import datastructure.ClassDefinition;
import datastructure.InstanceMaker;

public class TreeBuilderTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		
		List<ClassDefinition> classes = db.getClasses();
		
		InstanceMaker maker = db.getMapByName("azeroth").getMaker();
		
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
		masik.makeRoot("Bomb");
		//masik.makeChildren("color", "Red"); /*Ha nemadjuk meg az attribútumot, akkor a default lesz ott*/
		
		
		builder.makeRoot("Mine");
		builder.makeChildren("name", "ValamiName");
		builder.makeChildren("kor", "1111");
		
		builder.makeChildren("stone");
		builder.makeChildren("strong", "11");
		builder.makeChildren("location");
		builder.makeChildren("x", "123");
		builder.makeChildren("y", "321");
		
		//System.out.println(builder.toString());
		
		
		
		List<TreeNode> result = builder.root.getSortedTreeList();/*Ebben a listában megvan sorrenben, hogy hogyan
		hajsta végre.*/
		result.add(0, builder.root);
		/*innentől van meg.*/
		
		
		
		/*for(int i=0;i<result.size();i++){
			System.out.println(result.get(i).ownDetails());
		}*/
		
		Values value = builder.root.getValue();
		value.execute();
		
		Values v = masik.root.getValue();
		v.execute();
		
		
		for(int i=0;i<db.getMapByName("azeroth").size();i++){
			System.out.println(db.getMapByName("azeroth").get(i));
		}
		
		
		/*itt már ténylegesen lekérdezve van az attribútum*/
		//System.out.println(db.getMapByName("azeroth").get(16).getAttribute("stone").getAttribute("location").getAttribute("y").getValue());
		

	}

}