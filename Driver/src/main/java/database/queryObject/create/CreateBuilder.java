package database.queryObject.create;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;

public class CreateBuilder {
	private Create create;
	private InMemoryDatabase db;
	private Executable createType;
	private CreateEnum createTypeEnum;
	
	public CreateBuilder(InMemoryDatabase db) {
		this.db = db;
		this.create = new Create();
	}
	
	public void setCreateType(String createType){
		if(createType.equalsIgnoreCase("map")){
			this.createTypeEnum = CreateEnum.MAP;
		}else if(createType.equalsIgnoreCase("database")){
			this.createTypeEnum = CreateEnum.DATABASE;
		}else if(createType.equalsIgnoreCase("class")){
			this.createTypeEnum = CreateEnum.CLASS;
		}
	}
	
	/**
	 * This method set the mapname or dbname or classname
	 * */
	public void setTheCommonValue(String value){
		if(this.createTypeEnum == CreateEnum.MAP){
			this.createType = new Map(value, this.db);
		}else if(this.createTypeEnum == CreateEnum.DATABASE){
			this.createType = new Database(value);
		}else if(this.createTypeEnum == CreateEnum.CLASS){
			this.createType = new Class(value, this.db);
		}
		this.create.setExec(this.createType);
	}
	
	
	public void addAttributeParameter(String value){
		this.createType.addAttributeParameter(value);
	}
	
	public void insertAttribute(){
		this.createType.insertAttribute();
	}
	
	public IQueryObject build(){
		return this.create;
	}
	
}




/*
 				Itt adatbázis létrehozás
				CreateBuilder builder = new CreateBuilder(Window.this.db);
				builder.createCreate();
				builder.setCreateType("database");
				builder.setTheCommonValue("DatabaseName");
				
				builder.build().execute(); 
				
				
				
				
				
				
				itt mapet hozunk létre.
				CreateBuilder builder = new CreateBuilder(Window.this.db);
				builder.createCreate();
				builder.setCreateType("map");
				builder.setTheCommonValue("og");
				
				builder.build().execute();
				
				
				
				
				
				Ezzel új osztályt definiálunk.
				CreateBuilder builder = new CreateBuilder(Window.this.db);
				builder.createCreate();
				builder.setCreateType("class");
				builder.setTheCommonValue("Harcos");
				builder.addAttributeParameter("Number");
				builder.addAttributeParameter("kor");
				builder.insertAttribute();
				builder.addAttributeParameter("String");
				builder.addAttributeParameter("attack");
				builder.addAttributeParameter("10");
				builder.insertAttribute();
				builder.addAttributeParameter("String");
				builder.addAttributeParameter("nev");
				builder.insertAttribute();
				
				builder.build().execute();
				
				
 * */
