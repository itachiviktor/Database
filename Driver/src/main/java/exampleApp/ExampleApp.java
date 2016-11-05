package exampleApp;

import java.util.List;

import database.InMemoryDatabase;
import datastructure.Instance;

public class ExampleApp {
	public static void main(String[] args) {
		InMemoryDatabase database = new InMemoryDatabase("Game");
		List<Instance> map = database.executeQuery("SELECT entity FROM og WHERE entity.x > 1000");
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).className.equals("Entity")){
				System.out.println(map.get(i).getAttribute("x").getValue());
			}
		}
		database.executeQuery("ALTER CLASS Entity RENAMEATTRIBUTE x TO newx ");
		database.executeQuery("DELETE obj FROM og WHERE obj IS Entity");
		
		database.persist();
		
	}
}
