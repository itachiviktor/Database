package database.queryObject.selectBuild;

import java.util.List;
import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import database.queryObject.delete.Delete;
import database.queryObject.delete.DeleteBuilder;
import datastructure.Instance;
import parser.Parser;

public class Test {
	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		/*Parser parser = new Parser();
		IQueryObject ob = parser.parse(db, "INSERT Rectangle(size(width,height),location(x,y)) INTO og VALUES((100,100),(100,100))");

		ob.execute();*/

		Parser parser = new Parser();
		//IQueryObject ob = parser.parse(db, "SELECT mine FROM og WHERE mine.distanceFrom[ {SELECT mine.x FROM og WHERE mine.id = {SELECT mine.x FROM og WHERE mine.id = 218}},10] > 10");
		//IQueryObject ob = parser.parse(db, "CREATE CLASS Harcos(Number kor, String type, Number power DEFAULT 10, Point location)");
		
		//IQueryObject ob = parser.parse(db, "DROP DATABASE Pentek");
		
		/*DeleteBuilder builder = new DeleteBuilder(db);
		builder.createDelete("mine");
		builder.setFrom("og");
		builder.startWhereCondition();
		builder.addOp("mine.id");
		builder.addOp("<");
		builder.addOp("{");
		builder.addResource("mine.width");
		builder.setSelectFrom("og");
		builder.addOp("mine.id");
		builder.addOp("=");
		builder.addOp("180");
		builder.addOp("}");
		
		IQueryObject ob = builder.build();
		
		List<Instance> inst = ob.execute();*/
		
		IQueryObject ob = parser.parse(db, "DELETE mine FROM og WHERE mine.id > 10");
		List<Instance> inst = ob.execute();
	
		db.persist();
		//db.persist();
		/*for(int i=0;i<inst.size();i++){
			System.out.println(inst.get(i));
		}*/
	}
}
