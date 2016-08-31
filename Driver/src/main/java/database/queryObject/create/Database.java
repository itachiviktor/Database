package database.queryObject.create;

import java.io.FileWriter;
import java.io.IOException;

public class Database implements Executable{
	private String name;
	
	
	public Database(String name) {
		this.name = name;
	}
	
	public void execute(){
		try {
			FileWriter file = new FileWriter(this.name + ".json");
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
