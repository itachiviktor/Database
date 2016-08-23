package driver;

import java.util.List;

import datastructure.NotValidAttribute;

public class Result {
	private List<Result> attributes;
	private Integer id;
	private String className;
	private Number numberValue;
	private String stringValue;
	private Boolean booleanValue;
	
	
	
	public Result(Integer id, String className) {
		this.id = id;
		this.className = className;
	}
	
	public void addAttribute(){
		
	}
	
	public Number getInt(String attributeName) throws NotValidAttribute{
		if(attributeName.equals("alma")){
			throw new NotValidAttribute("Boolean","Integer");
		}else{
			return 1;
		}	
	}
	
	public Float getFloat(){
		return 1f;
	}
	
	
	
	public boolean getBoolean(){
		return false;
	}
	
	public Result get(){
		return null;
	}
}