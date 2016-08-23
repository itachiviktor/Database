package bridge;



import java.util.List;

import database.Database;
import datastructure.ClassDefinition;
import datastructure.Instance;
import datastructure.NotValidAttribute;
import driver.Driver;
import driver.JSONDeserializer;
import driver.Result;

public class Select {

	public static void main(String[] args) {
		Database db = new Database();
		Driver driver = new Driver(db);
		
		List<Instance> res = driver.getResultSetList();
		for(int i=0;i<res.size();i++){
			if(res.get(i).className.equals("Rectangle")){
				System.out.println(res.get(i).getAttribute("location").getAttribute("x").getValue());
			}else{
				System.out.println(res.get(i));
			}
			
		}
		
		for(int i=0;i<db.map.size();i++){
			System.out.println(db.map.get(i).toString());
		}
		
		
		/*for(int i=0;i<driver.map.size();i++){
			System.out.println(driver.map.get(i).toString());
		}*/
		
		//Result rs = new Result();
		/*try {
			rs.getInt("alma");
		} catch (NotValidAttribute e) {
			e.printStackTrace();
		}*/
		
		/*
		System.out.println(db.mapInstancesString());
		System.out.println(driver.mapInstancesString());
		*/
		
		/*System.out.println(db.classDefinitionsString());
		System.out.println(driver.classDefinitionsString());*/
	}
}