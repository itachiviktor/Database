package database.queryObject;

import java.util.List;

import database.Database;
import datastructure.Instance;

public class QueryObjectTest {
	public static void main(String[] args) {
		Database db = new Database();
		
		
		Select belso = new Select(db);
		belso.where.op = ">";
		belso.where.jobb = "3";
		
		Select select = new Select(db);
		select.where.op = "<";
		select.where.jobb = "5";
		select.from.select = belso;
		
		List<Instance> l = select.execute();
		
		for(int i=0;i<l.size();i++){
			System.out.println(l.get(i));
		}
	}
}
