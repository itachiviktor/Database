package database.queryObject.selectBuild;

import java.util.HashMap;
import java.util.Map;

import database.queryObject.Select;

public class ParameterPuffer {
	private Map<Integer, String> strings;
	private Map<Integer, Select> selects;
	private int counter = 0;
	
	public ParameterPuffer() {
		this.strings = new HashMap<Integer, String>();
		this.selects = new HashMap<Integer, Select>();
	}
	
	public void addString(String string){
		strings.put(counter, string);
		counter++;
	}
	
	public void addSelect(Select select){
		selects.put(counter, select);
		counter++;
	}
	
	public void cleanBuffer(){
		this.strings.clear();
		this.selects.clear();
		this.counter = 0;
	}

	public Map<Integer, String> getStrings() {
		return strings;
	}

	public Map<Integer, Select> getSelects() {
		return selects;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
}
