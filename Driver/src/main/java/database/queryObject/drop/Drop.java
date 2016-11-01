package database.queryObject.drop;

import java.io.File;
import java.util.List;

import database.InMemoryDatabase;
import database.queryObject.IQueryObject;
import datastructure.Instance;

public class Drop implements IQueryObject{
	private InMemoryDatabase db;
	private String type;
	private String name;
	
	public Drop(InMemoryDatabase db) {
		this.db = db;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<Instance> execute() {
		if(this.type.equalsIgnoreCase("class")){
			boolean existingClass = false;
			for(int i=0;i<this.db.getClasses().size();i++){
				if(this.db.getClasses().get(i).className.equals(this.name)){
					existingClass = true;
					break;
				}
			}
			/*Ha a fentiekből az derült ki, hogy van ilyen class, akkor végigkell vizsgálni az összes mapet,
			 hogy van-e belőle valamelyikből példány, mert ha van, akkor nem lehet a class törlést végrehajtani.*/
			if(existingClass){
				boolean existingInstanceOfIt = false;
				
				outer:for(int i=0;i<this.db.getMaps().size();i++){
					for(int j=0;j<this.db.getMaps().get(i).size();j++){
						if(this.db.getMaps().get(i).get(j).className.equals(this.name)){
							existingInstanceOfIt = true;
							break outer;
						}
					}
				}
				
				if(!existingInstanceOfIt){
					int deleteableIndex = -1;
					for(int i=0;i<this.db.getClasses().size();i++){
						if(this.db.getClasses().get(i).className.equals(this.name)){
							deleteableIndex = i;
							break;
						}
					}
					
					if(deleteableIndex >= 0){
						this.db.getClasses().remove(deleteableIndex);
					}
				}
			}
		}else if(this.type.equalsIgnoreCase("database")){
			File file = new File(this.name + ".json");
			file.setWritable(true);
			file.delete();
		}else if(this.type.equalsIgnoreCase("map")){
			boolean existingMap = false;
			int deletableIndex = -1;
			for(int i=0;i<this.db.getMaps().size();i++){
				if(this.db.getMaps().get(i).getMapName().equals(this.name)){
					existingMap = true;
					deletableIndex = i;
					break;
				}
			}
			
			if(existingMap){
				this.db.getMaps().remove(deletableIndex);
			}
		}
		
		return null;
	}	
}