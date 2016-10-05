package database.queryObject.delete;

import java.util.List;

import database.InMemoryDatabase;
import database.LoadedDatabase;
import database.queryObject.From;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import database.queryObject.insert.TreeBuilder;
import database.queryObject.insert.TreeNode;
import database.queryObject.insert.Values;
import datastructure.InstanceMaker;
import datastructure.TileMap;

public class DeleteTest {

	public static void main(String[] args) {
		
		InMemoryDatabase db = new InMemoryDatabase("db");
		TileMap map = db.getMapByName("azeroth");
		
		
		
		
		
		
		InstanceMaker maker = db.getMapByName("azeroth").getMaker();
		
		TreeBuilder builder = new TreeBuilder(db, maker);
		//masik.makeChildren("color", "Red"); /*Ha nemadjuk meg az attribútumot, akkor a default lesz ott*/
		
		
		builder.makeRoot("Mine", "azeroth");
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
		
		Values value = builder.root.getValue();
		value.execute();
		
		
		
		
		
		
		
		
		
		
		
		
		for(int i=0;i<db.getMapByName("azeroth").size();i++){
			System.out.println(db.getMapByName("azeroth").get(i));
		}
		
		Delete delete = new Delete(db, "mine");
		From from = new From("azeroth");
		
		Operand op3 = new Operand("mine.id",true);
		Operand op4 = new Operand("7",false);
		Operators oper2 = Operators.EQ;
		WhereLetter let1 = new WhereLetter(op3, op4, oper2);
		
		Where wh = new Where();
		wh.setRoot(let1);
		
		delete.setFrom(from);
		delete.setWhere(wh);
		
		delete.execute();
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		for(int i=0;i<db.getMapByName("azeroth").size();i++){
			System.out.println(db.getMapByName("azeroth").get(i));
		}
	}

}