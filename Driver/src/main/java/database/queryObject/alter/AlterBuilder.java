package database.queryObject.alter;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;

public class AlterBuilder {
	private Alter alter;
	private InMemoryDatabase db;
	private IAlterType alt;
	
	public AlterBuilder(InMemoryDatabase db) {
		this.db = db;
	}
	
	public void createAlter(String className){
		this.alter = new Alter(this.db, className);
	}
	
	public void setAlterType(String alterType){
		if(alterType.equalsIgnoreCase("addattribute")){
			this.alt = new AddAttribute(this.db);
		}else if(alterType.equalsIgnoreCase("renameattribute")){
			this.alt = new RenameAttribute(this.db);
		}else if(alterType.equalsIgnoreCase("deleteattribute")){
			this.alt = new DeleteAttribute(this.db);
		}else if(alterType.equalsIgnoreCase("renameclass")){
			this.alt = new RenameClass(this.db);
		}
	}
	
	public void setStringAttributeName(String attributeName){
		this.alt.setStringAttributeName(attributeName);
		this.alter.setAlterType(this.alt);
	}
	
	public void setStringOptionalValue(String value){
		this.alt.setStringOptionalValue(value);
	}
	
	public IQueryObject build(){
		return this.alter;
	}
}












/*
 
  
  				Itt új attribútumot adunk hozzá.
  				AlterBuilder builder = new AlterBuilder(Window.this.db);
				builder.createAlter("Körte");
				builder.setAlterType("addattribute");
				builder.setStringAttributeName("nevem");
				builder.setStringOptionalValue("String");
				
				
				builder.build().execute();
				
				
				
				
				itt pedig a size attribútum nevét változtatjuk meg modosítottattributumnevre
				builder.createAlter("Körte");
				builder.setAlterType("renameattribute");
				builder.setStringAttributeName("size");
				builder.setStringOptionalValue("modositott attributumnev");
  
  
 * */
