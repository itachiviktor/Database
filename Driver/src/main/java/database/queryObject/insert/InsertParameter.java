package database.queryObject.insert;

import java.util.ArrayList;
import java.util.List;

public class InsertParameter{
	private String className;
	public List<String> attributes;
	public List<InsertParameter> parameters;
	
	public InsertParameter(String className) {
		attributes = new ArrayList<String>();
		parameters = new ArrayList<InsertParameter>();
		this.className = className;
	}
	
	/**
	 * This method answrs that the attribute has this class or not.*/
	public boolean contains(String attribute){
		for(int i=0;i<attributes.size();i++){
			if(attributes.get(i).equals(attribute)){
				return true;
			}
		}
		
		for(int i=0;i<parameters.size();i++){
			if(parameters.get(i).className.equals(attribute)){
				return true;
			}
		}
		
		return false;
	}
	
	public void addAttribute(String value){
		attributes.add(value);
	}
	
	public void addParameter(InsertParameter param){
		parameters.add(param);
	}
	
	/*Ha a két tömb null, akkor azt jelenti, hogy így adták meg, insert Mine into azeroth.*/
	
	public String getClassName() {
		return className;
	}
	
}
