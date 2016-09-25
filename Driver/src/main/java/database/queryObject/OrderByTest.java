package database.queryObject;

import java.util.ArrayList;
import java.util.List;

public class OrderByTest {
	public static void main(String[] args) {
		List<String> strings = new ArrayList<String>();
		
		strings.add("szilva");
		strings.add("alma");
		strings.add("körte");
	
		

		
		for(int i=0;i<strings.size();i++){
			System.out.println(strings.get(i));
		}
	}
}
