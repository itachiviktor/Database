package database.queryObject;

import datastructure.Instance;

public class Prefix {
	/*Ez nagyon úgy tűnik, hogy intercenak kellene, hogy legyen...*/
	public Integer numbervalue;
	public String stringValue;
	public Boolean booleanValue;
	
	
	public String symbol;
	public String method;
	public Select select;
	
	public Prefix child;
	
	public Instance represented;
	public boolean isRoot;
	
	public Prefix() {
		
	}
	
	public <T> T kiertekel(){
		if(child == null){
			return (T) "alma";
		}
		return child.kiertekel();
	}
}
