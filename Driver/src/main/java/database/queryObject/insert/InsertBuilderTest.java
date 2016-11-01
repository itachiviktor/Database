package database.queryObject.insert;

import database.InMemoryDatabase;

public class InsertBuilderTest {

	public static void main(String[] args) {
		InMemoryDatabase db = new InMemoryDatabase("Game");
		
		InsertBuilder builder = new InsertBuilder(db);
		builder.addClassName("Almaa");
		builder.addAttribute("size");
		builder.addAttribute("x");
		builder.addAttribute("y");
		builder.removeRoundBracketAttribute();
		builder.addAttribute("location");
		builder.addAttribute("x");
		builder.addAttribute("y");
		builder.removeRoundBracketAttribute();
		builder.removeRoundBracketAttribute();
		
		builder.addMapName("og");
		
		builder.addRoundBracketValue();
		builder.addRoundBracketValue();
		builder.addValue("10");
		builder.addValue("20");
		builder.addRoundBracketValue();
		builder.addValue("30");
		builder.addValue("40");
		
		builder.build().execute();
		
		
		
		db.persist();
		
		System.out.println("Lefutott");
	}
}