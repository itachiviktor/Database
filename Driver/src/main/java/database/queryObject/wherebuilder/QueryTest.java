package database.queryObject.wherebuilder;

import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.From;
import database.queryObject.Select;
import database.queryObject.SelectBuilder;
import database.queryObject.Where;
import datastructure.Instance;

public class QueryTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		/*for(int i=0;i<db.getMapByName("og").size();i++){
			System.out.println(db.getMapByName("og").get(i));
		}*/
		
		//WhereBuilder builder = new WhereBuilder();
		
		SelectBuilder builder = new SelectBuilder(db);
		builder.setResultObject("mine");
		builder.setFrom("og");
		
		builder.addRoundBracket();
		builder.addOperandPiece("mine.id");
		builder.addOperandPiece(">=");
		builder.addOperandPiece("100");
		builder.addOperandPiece("AND");
		builder.addOperandPiece("mine.id");
		builder.addOperandPiece("<=");
		builder.addOperandPiece("150");
		builder.removeRoundBracket();
		builder.addOperandPiece("AND");
		builder.addOperandPiece("mine.id");
		builder.addOperandPiece(">");
		builder.addOperandPiece("10");
		
		builder.setOrderByAttribute("mine.id");
		builder.setOrderBySort("desc");
		
		builder.setLimit(2);
		
		
		
		List<Instance> ini = builder.build().execute();
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Lista" + ini.size());
		
		for(int i=0;i<ini.size();i++){
			System.out.println(ini.get(i));
		}
		
		
		

	}

}
