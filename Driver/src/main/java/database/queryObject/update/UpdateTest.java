package database.queryObject.update;

import java.awt.Point;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import database.queryObject.create.AttributeDescriptor;
import database.queryObject.create.Class;
import database.queryObject.create.Create;
import database.queryObject.insert.TreeBuilder;
import database.queryObject.insert.TreeNode;
import database.queryObject.insert.Values;
import datastructure.Instance;
import datastructure.InstanceMaker;
import datastructure.TileMap;

public class UpdateTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("db");
		TileMap map = db.getMapByName("azeroth");

		
		InstanceMaker maker = db.getMapByName("azeroth").getMaker();
		
		TreeBuilder builder = new TreeBuilder(db, maker);
		
		/*for(int i=0;i<classes.size();i++){
			System.out.println(classes.get(i));
		}*/
		
		
		TreeBuilder masik = new TreeBuilder(db, maker);
		masik.makeRoot("Bomb", "azeroth");
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
		
		
		
		/*for(int i=0;i<result.size();i++){
			System.out.println(result.get(i).ownDetails());
		}*/
		
		Values value = builder.root.getValue();
		value.execute(0);
		
		Values v = masik.root.getValue();
		v.execute(0);
		
		
		
		
		
		
		
		/*System.out.println(map.get(7).getAttribute("stone").getAttribute("location").getAttribute("x").getValue());
		map.get(7).getAttribute("stone").getAttribute("location").getAttribute("x").setValue(20);
		System.out.println(map.get(7).getAttribute("stone").getAttribute("location").getAttribute("x").getValue());
		*/
		
		Update update = new Update("azeroth", db);
		
		/*Set set = new Set();
		set.setAttribute("location.x");
		set.setValue(30);
		set.setAttribute("location.y");
		set.setValue(333);*/
		
		Move move = new Move();
		move.setNewLocationPoint(new Point(100, 100));
		
		/*updatenél nincs mine.id csak id , majd a mine. -ot manuálisan kell odaraknom a buildelőben.*/
		Operand op3 = new Operand("mine.id",true);
		Operand op4 = new Operand("20",false);
		Operators oper2 = Operators.EQ;
		WhereLetter let1 = new WhereLetter(op3, op4, oper2);
		
		Where wh = new Where();
		wh.setRoot(let1);
		
		update.db = db;
		update.setMove(move);
		update.where = wh;
		
		List<Instance> res = update.execute();
		
		System.out.println();
		System.out.println();
		for(int i=0;i<map.size();i++){
			System.out.println(map.get(i));
		}
	}
}
