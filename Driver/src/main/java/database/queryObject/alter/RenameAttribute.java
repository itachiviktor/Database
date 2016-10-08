package database.queryObject.alter;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;

public class RenameAttribute implements IAlterType{
	
	private String attributeName;
	private String attributeNameToThis;/*erre fogjuk átnevezni az előző attribútumnevet.*/
	
	private InMemoryDatabase db;
	
	private ClassDefinitionChecker checker;
	
	public RenameAttribute(InMemoryDatabase db) {
		this.db = db;
		this.checker = new ClassDefinitionChecker(db);
	}

	public void execute(String className) {
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
				String modifierClassType = classDef.attributes.get(this.attributeName);
				classDef.attributes.remove(this.attributeName);/*Itt töröljük a régi elemt, mert új névvel fogjuk beszúrni.*/
				
				classDef.attributes.put(this.attributeNameToThis, modifierClassType);
				
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

	public String getAttributeNameToThis() {
		return attributeNameToThis;
	}

	public void setAttributeNameToThis(String attributeNameToThis) {
		this.attributeNameToThis = attributeNameToThis;
	}
	
	

}
