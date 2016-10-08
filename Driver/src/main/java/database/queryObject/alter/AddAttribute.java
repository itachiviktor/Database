package database.queryObject.alter;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;

public class AddAttribute implements IAlterType{

	private String attributeName;
	private String attributeClassName;
	
	private InMemoryDatabase db;
	
	private ClassDefinitionChecker checker;
	
	public AddAttribute(InMemoryDatabase db) {
		this.db = db;
		this.checker = new ClassDefinitionChecker(db);
	}
	
	
	public AddAttribute(String attributeName, String attributeClassName) {
		super();
		this.attributeName = attributeName;
		this.attributeClassName = attributeClassName;
	}



	public void execute(String className){
		/*először megkell vizsgálni, hogy a módosítandó class-ból van-e példány létrehozva, mert ha igen,
		 akkor nem lehet módosítani.*/
		if(!checker.hasAnExistingInstanceInDB(className)){
			ClassDefinition classDef = null;	
			
			for(int i=0;i<db.getClasses().size();i++){
				if(db.getClasses().get(i).className.equals(className)){
					classDef = db.getClasses().get(i);
					break;
				}
			}
			
			if(classDef != null){
				classDef.attributes.put(this.attributeName, this.attributeClassName);
			}
		}else{
			System.out.println("ezt nem tudod módosítani, van létező példány belőle");
		}
		
	}



	public String getAttributeName() {
		return attributeName;
	}



	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}



	public String getAttributeClassName() {
		return attributeClassName;
	}



	public void setAttributeClassName(String attributeClassName) {
		this.attributeClassName = attributeClassName;
	}
	
	

}
