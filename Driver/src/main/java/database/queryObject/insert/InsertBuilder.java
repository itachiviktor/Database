package database.queryObject.insert;

import java.util.ArrayList;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;

public class InsertBuilder {
	private Insert insert;
	private InMemoryDatabase db;
	
	private String mapName;
	private String className;
	private List<InsertNode> attributes;
	private List<String> values;
	
	public InsertBuilder(InMemoryDatabase db) {
		this.db = db;
		this.values = new ArrayList<String>();
		this.attributes = new ArrayList<InsertNode>();
		this.attributes.add(new InsertNode("obj"));
	}
	
	/**
	 * Into parameter
	 * */
	public void addMapName(String mapName){
		this.mapName = mapName;
	}
	
	public void addClassName(String className){
		this.className = className;
	}
	
	/**
	 * After classname attribute
	 * */
	public void addAttribute(String attribute){
		this.attributes.add(new InsertNode(attribute));
	}
	
	public void removeRoundBracketAttribute(){
		this.attributes.get(this.attributes.size() - 1).riseStackValue();
	}
	
	public void addRoundBracketValue(){
		this.values.add("obj");
	}
	
	public void addValue(String value){
		this.values.add(value);
	}
	
	
	public IQueryObject build(){
		this.insert = new Insert(this.db, mapName);
		
		
		
		insert.makeRoot(this.className, this.mapName);
		for(int i=1;i<this.attributes.size();i++){
			
			if(this.values.get(i).equalsIgnoreCase("obj")){
				insert.makeChildren(this.attributes.get(i).getValue());
			}else{
				insert.makeChildren(this.attributes.get(i).getValue(), this.values.get(i));
			}
			
			this.attributes.get(i).callMoveToParent(insert);			
		}
		
		return this.insert;
	}
}

















/*

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


 * */
