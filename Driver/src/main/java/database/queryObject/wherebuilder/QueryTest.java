package database.queryObject.wherebuilder;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.Select;
import database.queryObject.SelectBuilder;
import database.queryObject.Where;
import datastructure.Instance;
import parser.Parser;

public class QueryTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		/*for(int i=0;i<db.getMapByName("og").size();i++){
			System.out.println(db.getMapByName("og").get(i));
		}*/
		
		//WhereBuilder builder = new WhereBuilder();
		
		/*SelectBuilder builder = new SelectBuilder(db);
		builder.setResultObject("mine");
		builder.setFrom("og");
		
		

		builder.addOperandPiece("mine");
		builder.addOperandPiece("COLLIDE");
		builder.addAngledBracket();
		builder.createAnAlSelect();
		builder.setResultObject("mine.x");
		builder.setFrom("og");
		builder.addOperandPiece("mine.id");
		builder.addOperandPiece("=");
		builder.addOperandPiece("6");
		builder.buildAlSelectAndPutAsOperand();
	
		builder.addPointParameter("0");
		builder.removeAngledBracket();*/
		
		

		/*builder.setOrderByAttribute("mine.id");
		builder.setOrderBySort("desc");
		
		builder.setLimit(2);*/
		
		/*List<Instance> ini = builder.build().execute();
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Lista" + ini.size());
		
		for(int i=0;i<ini.size();i++){
			System.out.println(ini.get(i));
		}*/
		
		
		Parser parser = new Parser();
		// List<Instance> inst = parser.parse(db, "INSERT Rectangle(size(width,height),location(x,y)) INTO og VALUES((10,20),(30,40)))").execute();
		List<Instance> inst = parser.parse(db, "SELECT mine FROM og WHERE mine.id = 10 OR mine.id > 100").execute();
		for(int i=0;i<inst.size();i++){
			System.out.println(inst.get(i));
		}
	}
}