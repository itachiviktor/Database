package database.queryObject.alter;

import database.InMemoryDatabase;
import datastructure.ClassDefinition;

public class RenameClass implements IAlterType{
	private String renameClassToThisName;
	
	private InMemoryDatabase db;
	
	private ClassDefinitionChecker checker;
	
	public RenameClass(InMemoryDatabase db) {
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
				classDef.className = this.renameClassToThisName;
			}
		}else{
			System.out.println("ezt nem tudod módosítani, van létező példány belőle");
		}
		
	}

	public String getRenameClassToThisName() {
		return renameClassToThisName;
	}


	public void setStringAttributeName(String attributeName) {
		this.renameClassToThisName = attributeName;
	}

	public void setStringOptionalValue(String value) {
		/*erre itt nincs szükség*/
		
	}
		
}