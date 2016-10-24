package driver;

import java.util.List;

import database.InMemoryDatabase;
import database.LoadedDatabase;
import datastructure.Instance;

public class DriverTest {

	public static void main(String[] args) {
		Driver driver = new Driver(new InMemoryDatabase("Game"));
		List<Instance> res = driver.getResultSetList();
		for(int i=0;i<res.size();i++){
			System.out.println(res.get(i));
		}

	}

}
