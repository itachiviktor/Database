package database.queryObject.selectBuild;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.IQueryObject;
import database.queryObject.Operand;
import database.queryObject.Operators;
import database.queryObject.Select;
import database.queryObject.Where;
import database.queryObject.WhereLetter;
import database.queryObject.insert.InsertBuilder;
import datastructure.Instance;
import parser.Parser;

public class Test {
	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		SelectBuilder builder = new SelectBuilder(db);
		builder.addResource("mine");
		builder.setFrom("og");
		builder.addOp("not");
		builder.addOp("(");
		builder.addOp("mine.id");
		builder.addOp("<");
		builder.addOp("100");
		builder.addOp("AND");
		builder.addOp("mine.id");
		builder.addOp("=");
		builder.addOp("150");
		builder.addOp(")");
	
		
		/*SelectBuilder builder = new SelectBuilder(db);
		builder.addResource("mine");
		builder.setFrom("og");
		builder.addOp("mine");
		builder.addOp("COLLIDE");
		builder.addOp("[");
		builder.addResource("mine.x");
		builder.setFrom("og");
		builder.addOp("mine.id");
		builder.addOp("=");
		builder.addOp("6");
		builder.addOp("}");
		builder.addResource("mine.x");
		builder.setFrom("og");
		builder.addOp("mine.id");
		builder.addOp("=");
		builder.addOp("6");
		builder.addOp("}");
		builder.addOp("]");*/
	
	
		
		
		
		
		
	
		
		
		
		/*Parser parser = new Parser();
		IQueryObject ob = parser.parse(db, "INSERT Rectangle(size(width,height),location(x,y)) INTO og VALUES((100,100),(100,100))");

		ob.execute();*/

		
		List<Instance> inst = builder.build().execute();
		
		//db.persist();
		for(int i=0;i<inst.size();i++){
			System.out.println(inst.get(i));
		}
	}
}
