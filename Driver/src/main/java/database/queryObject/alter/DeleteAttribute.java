package database.queryObject.alter;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;

public class DeleteAttribute implements IAlterType{
	private String deletableAttributeName;
	
	private InMemoryDatabase db;
	
	private ClassDefinitionChecker checker;
	
	public DeleteAttribute(InMemoryDatabase db) {
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
				classDef.attributes.remove(this.deletableAttributeName);
			}
		}else{
			System.out.println("ezt nem tudod módosítani, van létező példány belőle");
		}		
	}

	public String getDeletableAttributeName() {
		return deletableAttributeName;
	}


	public void setStringAttributeName(String attributeName) {
		this.deletableAttributeName = attributeName;
		
	}

	public void setStringOptionalValue(String value) {
		/*erre itt niincs szükség*/
		
	}
	
}
