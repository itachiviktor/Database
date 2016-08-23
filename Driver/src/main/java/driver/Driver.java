package driver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import database.Database;
import datastructure.ClassDefinition;
import datastructure.Instance;

public class Driver {
	public List<Instance> map; 
	public List<ClassDefinition> classes;
	public List<Integer> results;
	
	public List<Instance> resultSetList;
	
	private Database db;
	private JSONDeserializer deserializer;
	
	public Driver(Database db) {
		this.db = db;
		deserializer = new JSONDeserializer();
		
		JSONObject res = db.resultJSON();
		
		classes = deserializer.getClassDefinitions(res);
		map = deserializer.getInstances(res);
		results = deserializer.getResults(res);
	}
	
	/**
	 * This method give the ResultSet.
	 * */
	public List<Instance> getResultSetList(){
		resultSetList = new ArrayList<Instance>();
		for(int i=0;i<map.size();i++){
			for(int j=0;j<results.size();j++){
				if(i == results.get(j)){
					resultSetList.add(map.get(i));
				}
			}
		}
		return resultSetList;
	}
	
	

	public List<Instance> getMap() {
		return map;
	}

	public void setMap(List<Instance> map) {
		this.map = map;
	}

	public List<ClassDefinition> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassDefinition> classes) {
		this.classes = classes;
	}
	
	public String classDefinitionsString(){
		StringBuilder sb = new StringBuilder();
		for(ClassDefinition x : this.classes){
			sb.append(x.toString());
		}
		return sb.toString();
	}
	
	public String mapInstancesString(){
		StringBuilder sb = new StringBuilder();
		for(Instance x : this.map){
			sb.append(x.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
}